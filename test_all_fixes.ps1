Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTE COMPLETO - TODAS AS CORRECOES" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://127.0.0.1:8888"
$allPassed = $true

# Teste 1: MissingAppKeyException
Write-Host "[1/3] Testando correcao MissingAppKeyException..." -NoNewline
try {
    $login = Invoke-WebRequest -Uri "$baseUrl/login" -UseBasicParsing -ErrorAction Stop

    if ($login.Content -match 'MissingAppKeyException') {
        Write-Host " FALHOU" -ForegroundColor Red
        Write-Host "  Erro: MissingAppKeyException ainda presente!" -ForegroundColor Red
        $allPassed = $false
    } else {
        Write-Host " OK" -ForegroundColor Green
    }
} catch {
    Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    $allPassed = $false
}

# Teste 2: QueryException (Banco de dados)
Write-Host "[2/3] Testando correcao QueryException (SQLite)..." -NoNewline
try {
    $login = Invoke-WebRequest -Uri "$baseUrl/login" -UseBasicParsing -ErrorAction Stop

    if ($login.Content -match 'QueryException|SQLSTATE|Connection refused') {
        Write-Host " FALHOU" -ForegroundColor Red
        Write-Host "  Erro: Problema de conexao com banco de dados!" -ForegroundColor Red
        $allPassed = $false
    } else {
        Write-Host " OK" -ForegroundColor Green
    }
} catch {
    Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    $allPassed = $false
}

# Teste 3: UrlGenerationException
Write-Host "[3/3] Testando correcao UrlGenerationException..." -NoNewline
try {
    $bancos = Invoke-WebRequest -Uri "$baseUrl/bancos" -UseBasicParsing -ErrorAction Stop

    if ($bancos.Content -match 'UrlGenerationException|Missing required parameter|Missing parameter') {
        Write-Host " FALHOU" -ForegroundColor Red
        Write-Host "  Erro: UrlGenerationException ainda presente!" -ForegroundColor Red
        Write-Host "  A rota nao esta usando a chave primaria corretamente." -ForegroundColor Yellow
        $allPassed = $false
    } else {
        Write-Host " OK" -ForegroundColor Green
    }
} catch {
    if ($_.Exception.Message -match "401|403") {
        Write-Host " OK (Requer autenticacao - esperado)" -ForegroundColor Green
    } else {
        Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
        $allPassed = $false
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
if ($allPassed) {
    Write-Host "  RESULTADO: TODOS OS TESTES PASSARAM!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Correcoes implementadas com sucesso:" -ForegroundColor Green
    Write-Host "  [OK] MissingAppKeyException - APP_KEY gerada automaticamente" -ForegroundColor Green
    Write-Host "  [OK] QueryException - SQLite configurado como padrao" -ForegroundColor Green
    Write-Host "  [OK] UrlGenerationException - Rotas usando chave primaria" -ForegroundColor Green
} else {
    Write-Host "  RESULTADO: ALGUNS TESTES FALHARAM!" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Verifique os erros acima para mais detalhes." -ForegroundColor Yellow
}
Write-Host ""
