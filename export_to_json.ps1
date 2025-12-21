# Script para exportar tabelas em formato JSON

$inputFile = "c:\java\workspace\Gerador\banco xandel.txt"
$outputFileJson = "c:\java\workspace\Gerador\tables_structure.json"

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
        if ($cell.source -is [array]) {
            $sqlContent += ($cell.source -join "") + "`n"
        } else {
            $sqlContent += $cell.source + "`n"
        }
    }
}

$sqlContent = $sqlContent -replace '\\r\\n', "`n"
$sqlContent = $sqlContent -replace '\\n', "`n"

# Regex para encontrar CREATE TABLE
$createTablePattern = '(?ms)CREATE TABLE \[dbo\]\.\[(\w+)\]\((.*?)\)\s+ON \[PRIMARY\]'
$matches = [regex]::Matches($sqlContent, $createTablePattern)

$tables = @()

foreach ($match in $matches) {
    $tableName = $match.Groups[1].Value
    $tableBody = $match.Groups[2].Value

    $columns = @()
    $foreignKeys = @()

    # Processar corpo da tabela
    $tableBody = $tableBody -replace '\r\n', ' '
    $tableBody = $tableBody -replace '\n', ' '
    $tableBody = $tableBody -replace '\s+', ' '

    # Dividir por vírgula preservando parênteses
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

    if ($currentItem.Trim() -ne '') {
        $items += $currentItem.Trim()
    }

    # Processar cada item
    foreach ($item in $items) {
        $item = $item.Trim()
        if ($item -eq '') { continue }

        # FOREIGN KEY
        if ($item -match 'CONSTRAINT.*FOREIGN KEY\s*\(\[(\w+)\]\)\s*REFERENCES\s+\[dbo\]\.\[(\w+)\]\s*\(\[(\w+)\]\)') {
            $foreignKeys += @{
                column = $Matches[1]
                referencesTable = $Matches[2]
                referencesColumn = $Matches[3]
            }
            continue
        }

        # PRIMARY KEY ou outros constraints
        if ($item -match '^\s*CONSTRAINT.*PRIMARY KEY') {
            continue
        }

        # Coluna
        if ($item -match '^\[(\w+)\]\s+\[?(\w+(?:\([^\)]+\))?)\]?(.*)') {
            $colName = $Matches[1]
            $colType = $Matches[2]
            $modifiers = $Matches[3].Trim()

            $isIdentity = $false
            $isNullable = $true
            $isPrimaryKey = $false

            if ($modifiers -match '(IDENTITY\([^\)]+\))') {
                $colType += " " + $Matches[1]
                $isIdentity = $true
            }

            if ($modifiers -match '\bNOT NULL\b') {
                $isNullable = $false
            }

            if ($modifiers -match '\bPRIMARY KEY\b') {
                $isPrimaryKey = $true
                $isNullable = $false
            }

            $columns += @{
                name = $colName
                type = $colType
                nullable = $isNullable
                identity = $isIdentity
                primaryKey = $isPrimaryKey
            }
        }
    }

    # Procurar ALTER TABLE para FKs adicionais
    $alterPattern = "(?ms)ALTER TABLE \[dbo\]\.\[$tableName\].*?ADD\s+CONSTRAINT.*?FOREIGN KEY\s*\(\[(\w+)\]\)\s*REFERENCES\s+\[dbo\]\.\[(\w+)\]\s*\(\[(\w+)\]\)"
    $alterMatches = [regex]::Matches($sqlContent, $alterPattern)

    foreach ($alterMatch in $alterMatches) {
        $fkColumn = $alterMatch.Groups[1].Value
        $refTable = $alterMatch.Groups[2].Value
        $refColumn = $alterMatch.Groups[3].Value

        $exists = $false
        foreach ($fk in $foreignKeys) {
            if ($fk.column -eq $fkColumn -and $fk.referencesTable -eq $refTable) {
                $exists = $true
                break
            }
        }

        if (-not $exists) {
            $foreignKeys += @{
                column = $fkColumn
                referencesTable = $refTable
                referencesColumn = $refColumn
            }
        }
    }

    $tables += @{
        name = $tableName
        columns = $columns
        foreignKeys = $foreignKeys
    }
}

# Criar estrutura final
$result = @{
    database = "Sociedade"
    extractedAt = (Get-Date).ToString("yyyy-MM-dd HH:mm:ss")
    totalTables = $tables.Count
    totalColumns = ($tables | ForEach-Object { $_.columns.Count } | Measure-Object -Sum).Sum
    totalForeignKeys = ($tables | ForEach-Object { $_.foreignKeys.Count } | Measure-Object -Sum).Sum
    tables = $tables | Sort-Object name
}

# Salvar JSON
$result | ConvertTo-Json -Depth 10 | Out-File -FilePath $outputFileJson -Encoding UTF8

Write-Host "Arquivo JSON salvo em: $outputFileJson" -ForegroundColor Green
Write-Host "Total de tabelas: $($result.totalTables)" -ForegroundColor Cyan
Write-Host "Total de colunas: $($result.totalColumns)" -ForegroundColor Cyan
Write-Host "Total de FKs: $($result.totalForeignKeys)" -ForegroundColor Cyan
