# Script para extrair CREATE TABLE e REFERENCES do arquivo SQL

$inputFile = "c:\java\workspace\Gerador\banco xandel.txt"
$outputFile = "c:\java\workspace\Gerador\tables_extracted.txt"

# Ler conteúdo completo
$content = Get-Content $inputFile -Raw -Encoding UTF8

# Encontrar todos os CREATE TABLE
$createTablePattern = 'CREATE TABLE\s+(\[?[\w]+\]?\.)?(\[?[\w]+\]?)\s*\(([\s\S]*?)\);'
$matches = [regex]::Matches($content, $createTablePattern)

$tables = @()

foreach ($match in $matches) {
    $tableName = $match.Groups[2].Value -replace '\[|\]', ''
    $tableDefinition = $match.Groups[3].Value

    # Extrair colunas
    $columnLines = $tableDefinition -split ',' | ForEach-Object { $_.Trim() }

    $columns = @()
    $foreignKeys = @()

    foreach ($line in $columnLines) {
        # Verificar se é CONSTRAINT
        if ($line -match '^\s*CONSTRAINT') {
            # Verificar se é FOREIGN KEY
            if ($line -match 'FOREIGN KEY\s*\((\[?[\w]+\]?)\)\s*REFERENCES\s+(\[?[\w]+\]?\.)?(\[?[\w]+\]?)\s*\((\[?[\w]+\]?)\)') {
                $fkColumn = $Matches[1] -replace '\[|\]', ''
                $refTable = $Matches[3] -replace '\[|\]', ''
                $refColumn = $Matches[4] -replace '\[|\]', ''

                $foreignKeys += @{
                    Column = $fkColumn
                    ReferencesTable = $refTable
                    ReferencesColumn = $refColumn
                }
            }
        }
        # Verificar se a coluna tem REFERENCES inline
        elseif ($line -match '^(\[?[\w]+\]?)\s+([\w\(\),\s]+?)(?:\s+REFERENCES\s+(\[?[\w]+\]?\.)?(\[?[\w]+\]?)\s*\((\[?[\w]+\]?)\))') {
            $colName = $Matches[1] -replace '\[|\]', ''
            $colType = $Matches[2].Trim()
            $refTable = $Matches[4] -replace '\[|\]', ''
            $refColumn = $Matches[5] -replace '\[|\]', ''

            $columns += @{
                Name = $colName
                Type = $colType
            }

            $foreignKeys += @{
                Column = $colName
                ReferencesTable = $refTable
                ReferencesColumn = $refColumn
            }
        }
        # Coluna normal
        elseif ($line -match '^(\[?[\w]+\]?)\s+([\w\(\),\s]+)') {
            $colName = $Matches[1] -replace '\[|\]', ''
            $colType = $Matches[2].Trim()

            # Remover constraints inline (PRIMARY KEY, NOT NULL, etc)
            $colType = $colType -replace '\s+(PRIMARY KEY|NOT NULL|NULL|IDENTITY.*|DEFAULT.*)', ''
            $colType = $colType.Trim()

            if ($colName -ne '' -and $colType -ne '') {
                $columns += @{
                    Name = $colName
                    Type = $colType
                }
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
$output += "=" * 80
$output += "TABELAS EXTRAÍDAS DO BANCO DE DADOS"
$output += "=" * 80
$output += ""
$output += "Total de tabelas encontradas: $($tables.Count)"
$output += ""

foreach ($table in $tables) {
    $output += "-" * 80
    $output += "TABELA: $($table.Name)"
    $output += "-" * 80

    $output += ""
    $output += "COLUNAS:"
    foreach ($col in $table.Columns) {
        $output += "  - $($col.Name): $($col.Type)"
    }

    if ($table.ForeignKeys.Count -gt 0) {
        $output += ""
        $output += "FOREIGN KEYS:"
        foreach ($fk in $table.ForeignKeys) {
            $output += "  - $($fk.Column) -> $($fk.ReferencesTable)($($fk.ReferencesColumn))"
        }
    }

    $output += ""
}

$output += "=" * 80

# Salvar em arquivo
$output | Out-File -FilePath $outputFile -Encoding UTF8

# Exibir na tela
$output | ForEach-Object { Write-Host $_ }

Write-Host ""
Write-Host "Arquivo salvo em: $outputFile" -ForegroundColor Green
