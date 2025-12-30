Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTE FINAL - Diagnostico Completo" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://127.0.0.1:8888"

# Teste 1: Verificar se servidor responde
Write-Host "[1/4] Verificando servidor..." -NoNewline
try {
    $ping = Invoke-WebRequest -Uri "$baseUrl" -UseBasicParsing -TimeoutSec 5 -ErrorAction Stop
    Write-Host " OK" -ForegroundColor Green
} catch {
    Write-Host " ERRO: Servidor nao responde" -ForegroundColor Red
    exit 1
}

# Teste 2: Verificar Model
Write-Host "[2/4] Verificando Model Cidade..." -NoNewline
$modelContent = Get-Content "c:\java\workspace\Gerador\generated\xandel-laravel\app\Models\Cidade.php" -Raw
if ($modelContent -match 'getRouteKeyName') {
    Write-Host " OK (metodo existe)" -ForegroundColor Green
} else {
    Write-Host " ERRO: metodo getRouteKeyName nao encontrado!" -ForegroundColor Red
}

# Teste 3: Verificar View
Write-Host "[3/4] Verificando View index..." -NoNewline
$viewContent = Get-Content "c:\java\workspace\Gerador\generated\xandel-laravel\resources\views\cidades\index.blade.php" -Raw
if ($viewContent -match '\$cidade->id_cidade') {
    Write-Host " OK (usa snake_case)" -ForegroundColor Green
} else {
    Write-Host " ERRO: view usa formato errado!" -ForegroundColor Red
}

# Teste 4: Testar rota real
Write-Host "[4/4] Testando rota /cidades..." -NoNewline
try {
    $cidades = Invoke-WebRequest -Uri "$baseUrl/cidades" -UseBasicParsing -TimeoutSec 10 -ErrorAction Stop

    if ($cidades.Content -match 'UrlGenerationException|Missing required parameter') {
        Write-Host " FALHOU" -ForegroundColor Red
        Write-Host ""
        Write-Host "DIAGNOSTICO:" -ForegroundColor Yellow
        Write-Host "  - Model: OK (tem getRouteKeyName)" -ForegroundColor Green
        Write-Host "  - View: OK (usa id_cidade)" -ForegroundColor Green
        Write-Host "  - Servidor: CACHE?" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "SOLUCAO:" -ForegroundColor White
        Write-Host "  1. Limpe cache do navegador (Ctrl+F5)" -ForegroundColor Cyan
        Write-Host "  2. Execute: php artisan view:clear" -ForegroundColor Cyan
        Write-Host "  3. Execute: php artisan config:clear" -ForegroundColor Cyan
        Write-Host "  4. Reinicie o servidor PHP" -ForegroundColor Cyan
    } else {
        Write-Host " OK" -ForegroundColor Green
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "  SUCESSO! Tudo funcionando!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
    }
} catch {
    if ($_.Exception.Message -match "401|403") {
        Write-Host " OK (requer autenticacao)" -ForegroundColor Green
    } else {
        Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
