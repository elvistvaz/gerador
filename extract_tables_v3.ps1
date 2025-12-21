# Script aprimorado para extrair CREATE TABLE e REFERENCES de notebook Jupyter SQL

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

# Extrair todo o SQL das células, concatenando arrays
$sqlContent = ""
foreach ($cell in $notebook.cells) {
    if ($cell.cell_type -eq "code" -and $cell.source) {
        # source pode ser um array de strings
        if ($cell.source -is [array]) {
            $sqlContent += ($cell.source -join "") + "`n"
        } else {
            $sqlContent += $cell.source + "`n"
        }
    }
}

# Remover \r\n e substituir por quebra de linha real
$sqlContent = $sqlContent -replace '\\r\\n', "`n"
$sqlContent = $sqlContent -replace '\\n', "`n"

Write-Host "Extraindo tabelas do SQL..." -ForegroundColor Cyan
Write-Host "Tamanho do SQL: $($sqlContent.Length) caracteres" -ForegroundColor Yellow

# Regex para encontrar CREATE TABLE completo
# Captura desde CREATE TABLE até o próximo GO
$createTablePattern = '(?ms)CREATE TABLE \[dbo\]\.\[(\w+)\]\((.*?)\)\s+ON \[PRIMARY\]'

$matches = [regex]::Matches($sqlContent, $createTablePattern)

Write-Host "Total de CREATE TABLE encontrados: $($matches.Count)" -ForegroundColor Yellow

$tables = @()

foreach ($match in $matches) {
    $tableName = $match.Groups[1].Value
    $tableBody = $match.Groups[2].Value

    Write-Host "Processando tabela: $tableName" -ForegroundColor Green

    $columns = @()
    $foreignKeys = @()

    # Remover quebras de linha extras e normalizar
    $tableBody = $tableBody -replace '\r\n', ' '
    $tableBody = $tableBody -replace '\n', ' '
    $tableBody = $tableBody -replace '\s+', ' '

    # Dividir por vírgula, mas preservar vírgulas dentro de parênteses
    $depth = 0
    $currentItem = ""
    $items = @()

    for ($i = 0; $i -lt $tableBody.Length; $i++) {
        $char = $tableBody[$i]

        if ($char -eq '(') {
            $depth++
            $currentItem += $char
        }
        elseif ($char -eq ')') {
            $depth--
            $currentItem += $char
        }
        elseif ($char -eq ',' -and $depth -eq 0) {
            if ($currentItem.Trim() -ne '') {
                $items += $currentItem.Trim()
            }
            $currentItem = ""
        }
        else {
            $currentItem += $char
        }
    }

    # Adicionar último item
    if ($currentItem.Trim() -ne '') {
        $items += $currentItem.Trim()
    }

    # Processar cada item
    foreach ($item in $items) {
        $item = $item.Trim()

        # Pular linhas vazias
        if ($item -eq '') { continue }

        # Verificar CONSTRAINT com FOREIGN KEY
        if ($item -match 'CONSTRAINT.*FOREIGN KEY\s*\(\[(\w+)\]\)\s*REFERENCES\s+\[dbo\]\.\[(\w+)\]\s*\(\[(\w+)\]\)') {
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

        # Verificar se é CONSTRAINT PRIMARY KEY ou outros constraints
        if ($item -match '^\s*CONSTRAINT.*PRIMARY KEY') {
            continue
        }

        # Extrair definição de coluna
        # Formato: [Nome] [Tipo] [modifiers...]
        if ($item -match '^\[(\w+)\]\s+\[?(\w+(?:\([^\)]+\))?)\]?(.*)') {
            $colName = $Matches[1]
            $colType = $Matches[2]
            $modifiers = $Matches[3].Trim()

            # Incluir NOT NULL, NULL, IDENTITY se houver
            if ($modifiers -match '(IDENTITY\([^\)]+\))') {
                $colType += " " + $Matches[1]
            }
            if ($modifiers -match '\bNOT NULL\b') {
                $colType += " NOT NULL"
            }
            elseif ($modifiers -match '\bNULL\b' -and $modifiers -notmatch 'NOT NULL') {
                $colType += " NULL"
            }

            $columns += @{
                Name = $colName
                Type = $colType
            }
        }
    }

    # Procurar por ALTER TABLE ... ADD CONSTRAINT ... FOREIGN KEY para esta tabela
    $alterPattern = "(?ms)ALTER TABLE \[dbo\]\.\[$tableName\].*?ADD\s+CONSTRAINT.*?FOREIGN KEY\s*\(\[(\w+)\]\)\s*REFERENCES\s+\[dbo\]\.\[(\w+)\]\s*\(\[(\w+)\]\)"
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
$output += "=" * 120
$output += "TABELAS EXTRAÃDAS DO BANCO DE DADOS SQL SERVER - SOCIEDADE"
$output += "=" * 120
$output += ""
$output += "Banco de Dados: Sociedade"
$output += "Total de tabelas encontradas: $($tables.Count)"
$output += "Total de colunas: $(($tables | ForEach-Object { $_.Columns.Count } | Measure-Object -Sum).Sum)"
$output += "Total de Foreign Keys: $(($tables | ForEach-Object { $_.ForeignKeys.Count } | Measure-Object -Sum).Sum)"
$output += "Data de extração: $(Get-Date -Format 'dd/MM/yyyy HH:mm:ss')"
$output += ""

foreach ($table in $tables | Sort-Object Name) {
    $output += ""
    $output += "=" * 120
    $output += "TABELA: $($table.Name)"
    $output += "=" * 120

    if ($table.Columns.Count -gt 0) {
        $output += ""
        $output += "COLUNAS ($($table.Columns.Count)):"
        $output += "-" * 120

        $maxNameLen = ($table.Columns | ForEach-Object { $_.Name.Length } | Measure-Object -Maximum).Maximum
        if ($maxNameLen -lt 25) { $maxNameLen = 25 }

        foreach ($col in $table.Columns) {
            $output += "  $($col.Name.PadRight($maxNameLen)) : $($col.Type)"
        }
    } else {
        $output += ""
        $output += "COLUNAS: Nenhuma coluna extraída (verificar formato)"
    }

    if ($table.ForeignKeys.Count -gt 0) {
        $output += ""
        $output += "FOREIGN KEYS ($($table.ForeignKeys.Count)):"
        $output += "-" * 120
        foreach ($fk in $table.ForeignKeys) {
            $output += "  $($fk.Column.PadRight(30)) --> $($fk.ReferencesTable)($($fk.ReferencesColumn))"
        }
    }
}

$output += ""
$output += "=" * 120
$output += "FIM DA EXTRAÇÃO"
$output += "=" * 120

# Salvar em arquivo
$output | Out-File -FilePath $outputFile -Encoding UTF8

# Exibir resumo na tela
Write-Host ""
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host "RESUMO DA EXTRAÇÃO" -ForegroundColor Yellow
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host "Total de tabelas: $($tables.Count)" -ForegroundColor Green
Write-Host "Total de colunas: $(($tables | ForEach-Object { $_.Columns.Count } | Measure-Object -Sum).Sum)" -ForegroundColor Green
Write-Host "Total de FKs: $(($tables | ForEach-Object { $_.ForeignKeys.Count } | Measure-Object -Sum).Sum)" -ForegroundColor Green
Write-Host ""
Write-Host "Listagem de tabelas:" -ForegroundColor Yellow
$tables | Sort-Object Name | ForEach-Object {
    Write-Host "  - $($_.Name) ($($_.Columns.Count) colunas, $($_.ForeignKeys.Count) FKs)" -ForegroundColor Cyan
}
Write-Host ""
Write-Host "Arquivo salvo em: $outputFile" -ForegroundColor Green
Write-Host "=" * 100 -ForegroundColor Cyan
