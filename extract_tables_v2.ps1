# Script para extrair CREATE TABLE e REFERENCES de um notebook Jupyter SQL

$inputFile = "c:\java\workspace\Gerador\banco xandel.txt"
$outputFile = "c:\java\workspace\Gerador\tables_extracted.txt"

# Ler conteúdo completo
$content = Get-Content $inputFile -Raw -Encoding UTF8

# Converter de JSON
try {
    $notebook = $content | ConvertFrom-Json
} catch {
    Write-Host "Erro ao converter JSON: $_" -ForegroundColor Red
    exit
}

# Extrair todo o SQL das células
$sqlContent = ""
foreach ($cell in $notebook.cells) {
    if ($cell.cell_type -eq "code" -and $cell.source) {
        $sqlContent += ($cell.source -join "`n") + "`n"
    }
}

Write-Host "Extraindo tabelas do SQL..." -ForegroundColor Cyan

# Regex para encontrar CREATE TABLE com suporte multilinha
$createTablePattern = '(?ms)CREATE TABLE\s+\[?dbo\]?\.\[?(\w+)\]?\s*\((.*?)\)\s*(?:ON\s+\[PRIMARY\])?'

$matches = [regex]::Matches($sqlContent, $createTablePattern)

Write-Host "Total de CREATE TABLE encontrados: $($matches.Count)" -ForegroundColor Yellow

$tables = @()

foreach ($match in $matches) {
    $tableName = $match.Groups[1].Value
    $tableBody = $match.Groups[2].Value

    Write-Host "Processando tabela: $tableName" -ForegroundColor Green

    # Dividir as linhas do corpo da tabela
    $lines = $tableBody -split '\r?\n' | Where-Object { $_.Trim() -ne '' }

    $columns = @()
    $foreignKeys = @()

    foreach ($line in $lines) {
        $line = $line.Trim()

        # Ignorar linhas de comentário e GO
        if ($line -match '^--' -or $line -eq 'GO' -or $line -eq '') {
            continue
        }

        # Verificar CONSTRAINT com FOREIGN KEY
        if ($line -match 'CONSTRAINT.*FOREIGN KEY\s*\(\[?(\w+)\]?\)\s*REFERENCES\s+\[?dbo\]?\.\[?(\w+)\]?\s*\(\[?(\w+)\]?\)') {
            $fkColumn = $Matches[1]
            $refTable = $Matches[2]
            $refColumn = $Matches[3]

            $foreignKeys += @{
                Column = $fkColumn
                ReferencesTable = $refTable
                ReferencesColumn = $refColumn
            }
            continue
        }

        # Verificar se é CONSTRAINT (PRIMARY KEY, INDEX, etc) - ignorar
        if ($line -match '^\s*CONSTRAINT|^\s*PRIMARY KEY|^\s*FOREIGN KEY') {
            continue
        }

        # Extrair definição de coluna
        # Formato: [Nome] [Tipo] [IDENTITY] [NOT NULL] [NULL] etc
        if ($line -match '^\[?(\w+)\]?\s+([\w\(\),\s]+?)(?:\s+IDENTITY|\s+NOT NULL|\s+NULL|\s+CONSTRAINT|\s*,|\s*$)') {
            $colName = $Matches[1]
            $colType = $Matches[2].Trim()

            # Limpar vírgula final se houver
            $colType = $colType -replace ',\s*$', ''

            if ($colName -ne '' -and $colType -ne '' -and $colName -notmatch '^(CONSTRAINT|PRIMARY|FOREIGN|WITH|ON)') {
                $columns += @{
                    Name = $colName
                    Type = $colType
                }
            }
        }
    }

    # Procurar por ALTER TABLE ... ADD CONSTRAINT ... FOREIGN KEY para esta tabela
    $alterPattern = "(?ms)ALTER TABLE\s+\[?dbo\]?\.\[?$tableName\]?.*?ADD\s+CONSTRAINT.*?FOREIGN KEY\s*\(\[?(\w+)\]?\)\s*REFERENCES\s+\[?dbo\]?\.\[?(\w+)\]?\s*\(\[?(\w+)\]?\)"
    $alterMatches = [regex]::Matches($sqlContent, $alterPattern)

    foreach ($alterMatch in $alterMatches) {
        $fkColumn = $alterMatch.Groups[1].Value
        $refTable = $alterMatch.Groups[2].Value
        $refColumn = $alterMatch.Groups[3].Value

        # Verificar se já existe
        $exists = $false
        foreach ($fk in $foreignKeys) {
            if ($fk.Column -eq $fkColumn -and $fk.ReferencesTable -eq $refTable) {
                $exists = $true
                break
            }
        }

        if (-not $exists) {
            $foreignKeys += @{
                Column = $fkColumn
                ReferencesTable = $refTable
                ReferencesColumn = $refColumn
            }
        }
    }

    $tables += @{
        Name = $tableName
        Columns = $columns
        ForeignKeys = $foreignKeys
    }
}

# Gerar saída formatada
$output = @()
$output += "=" * 100
$output += "TABELAS EXTRAÍDAS DO BANCO DE DADOS SQL SERVER - SOCIEDADE"
$output += "=" * 100
$output += ""
$output += "Total de tabelas encontradas: $($tables.Count)"
$output += "Data de extração: $(Get-Date -Format 'dd/MM/yyyy HH:mm:ss')"
$output += ""

foreach ($table in $tables | Sort-Object Name) {
    $output += ""
    $output += "=" * 100
    $output += "TABELA: $($table.Name)"
    $output += "=" * 100

    if ($table.Columns.Count -gt 0) {
        $output += ""
        $output += "COLUNAS ($($table.Columns.Count)):"
        $output += "-" * 100

        $maxNameLen = ($table.Columns | ForEach-Object { $_.Name.Length } | Measure-Object -Maximum).Maximum
        if ($maxNameLen -lt 20) { $maxNameLen = 20 }

        foreach ($col in $table.Columns) {
            $output += "  $($col.Name.PadRight($maxNameLen)) : $($col.Type)"
        }
    }

    if ($table.ForeignKeys.Count -gt 0) {
        $output += ""
        $output += "FOREIGN KEYS ($($table.ForeignKeys.Count)):"
        $output += "-" * 100
        foreach ($fk in $table.ForeignKeys) {
            $output += "  $($fk.Column) --> $($fk.ReferencesTable)($($fk.ReferencesColumn))"
        }
    }
}

$output += ""
$output += "=" * 100
$output += "FIM DA EXTRAÇÃO"
$output += "=" * 100

# Salvar em arquivo
$output | Out-File -FilePath $outputFile -Encoding UTF8

# Exibir resumo na tela
Write-Host ""
Write-Host "=" * 80 -ForegroundColor Cyan
Write-Host "RESUMO DA EXTRAÇÃO" -ForegroundColor Yellow
Write-Host "=" * 80 -ForegroundColor Cyan
Write-Host "Total de tabelas: $($tables.Count)" -ForegroundColor Green
Write-Host "Total de colunas: $(($tables | ForEach-Object { $_.Columns.Count } | Measure-Object -Sum).Sum)" -ForegroundColor Green
Write-Host "Total de FKs: $(($tables | ForEach-Object { $_.ForeignKeys.Count } | Measure-Object -Sum).Sum)" -ForegroundColor Green
Write-Host ""
Write-Host "Arquivo salvo em: $outputFile" -ForegroundColor Green
Write-Host "=" * 80 -ForegroundColor Cyan

# Mostrar primeiras 50 linhas
Write-Host ""
Write-Host "Primeiras linhas do resultado:" -ForegroundColor Yellow
$output | Select-Object -First 50 | ForEach-Object { Write-Host $_ }
