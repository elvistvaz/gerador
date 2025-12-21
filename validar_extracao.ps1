# Script de validação da extração

$jsonFile = "c:\java\workspace\Gerador\tables_structure.json"

Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host "VALIDAÇÃO DA EXTRAÇÃO DO BANCO DE DADOS" -ForegroundColor Yellow
Write-Host "=" * 100 -ForegroundColor Cyan

# Ler JSON
$data = Get-Content $jsonFile -Raw | ConvertFrom-Json

Write-Host ""
Write-Host "Banco de Dados: $($data.database)" -ForegroundColor Green
Write-Host "Data de Extração: $($data.extractedAt)" -ForegroundColor Green
Write-Host ""
Write-Host "Estatísticas Gerais:" -ForegroundColor Yellow
Write-Host "  Total de Tabelas: $($data.totalTables)" -ForegroundColor Cyan
Write-Host "  Total de Colunas: $($data.totalColumns)" -ForegroundColor Cyan
Write-Host "  Total de Foreign Keys: $($data.totalForeignKeys)" -ForegroundColor Cyan

Write-Host ""
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host "TABELAS PRINCIPAIS COM MAIS COLUNAS" -ForegroundColor Yellow
Write-Host "=" * 100 -ForegroundColor Cyan

$topTables = $data.tables | Sort-Object { $_.columns.Count } -Descending | Select-Object -First 10

foreach ($table in $topTables) {
    Write-Host ""
    Write-Host "Tabela: $($table.name)" -ForegroundColor Green
    Write-Host "  Colunas: $($table.columns.Count)" -ForegroundColor Cyan
    Write-Host "  Foreign Keys: $($table.foreignKeys.Count)" -ForegroundColor Cyan

    if ($table.foreignKeys.Count -gt 0) {
        Write-Host "  FKs:" -ForegroundColor Yellow
        foreach ($fk in $table.foreignKeys) {
            Write-Host "    - $($fk.column) -> $($fk.referencesTable)($($fk.referencesColumn))" -ForegroundColor White
        }
    }
}

Write-Host ""
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host "TABELAS COM FOREIGN KEYS" -ForegroundColor Yellow
Write-Host "=" * 100 -ForegroundColor Cyan

$tablesWithFK = $data.tables | Where-Object { $_.foreignKeys.Count -gt 0 } | Sort-Object name

Write-Host ""
Write-Host "Total de tabelas com FK: $($tablesWithFK.Count)" -ForegroundColor Green
Write-Host ""

foreach ($table in $tablesWithFK) {
    Write-Host "$($table.name) ($($table.foreignKeys.Count) FKs)" -ForegroundColor Cyan
    foreach ($fk in $table.foreignKeys) {
        Write-Host "  └─ $($fk.column) -> $($fk.referencesTable).$($fk.referencesColumn)" -ForegroundColor White
    }
}

Write-Host ""
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host "MAPEAMENTO DE RELACIONAMENTOS" -ForegroundColor Yellow
Write-Host "=" * 100 -ForegroundColor Cyan

# Criar mapa de referências
$refMap = @{}

foreach ($table in $data.tables) {
    foreach ($fk in $table.foreignKeys) {
        $refTable = $fk.referencesTable

        if (-not $refMap.ContainsKey($refTable)) {
            $refMap[$refTable] = @()
        }

        $refMap[$refTable] += @{
            FromTable = $table.name
            FromColumn = $fk.column
            ToColumn = $fk.referencesColumn
        }
    }
}

Write-Host ""
Write-Host "Tabelas mais referenciadas:" -ForegroundColor Yellow
Write-Host ""

$refMap.GetEnumerator() | Sort-Object { $_.Value.Count } -Descending | ForEach-Object {
    Write-Host "$($_.Key) é referenciada $($_.Value.Count) vez(es)" -ForegroundColor Green
    foreach ($ref in $_.Value) {
        Write-Host "  └─ $($ref.FromTable).$($ref.FromColumn) -> $($ref.ToColumn)" -ForegroundColor White
    }
    Write-Host ""
}

Write-Host ""
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host "COLUNAS COM IDENTITY" -ForegroundColor Yellow
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host ""

foreach ($table in $data.tables | Sort-Object name) {
    $identityCols = $table.columns | Where-Object { $_.identity -eq $true }

    if ($identityCols.Count -gt 0) {
        Write-Host "$($table.name):" -ForegroundColor Green
        foreach ($col in $identityCols) {
            Write-Host "  └─ $($col.name) ($($col.type))" -ForegroundColor Cyan
        }
    }
}

Write-Host ""
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host "VALIDAÇÃO CONCLUÍDA" -ForegroundColor Green
Write-Host "=" * 100 -ForegroundColor Cyan
Write-Host ""
Write-Host "Arquivos disponiveis:" -ForegroundColor Yellow
Write-Host "  1. tables_extracted.txt - Listagem formatada legivel" -ForegroundColor Cyan
Write-Host "  2. tables_structure.json - Estrutura em JSON para programacao" -ForegroundColor Cyan
Write-Host "  3. RESUMO_BANCO_DADOS.md - Documentacao Markdown" -ForegroundColor Cyan
Write-Host ""
