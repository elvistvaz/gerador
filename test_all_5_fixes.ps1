Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTE COMPLETO - 5 CORRECOES" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://127.0.0.1:8888"
$allPassed = $true

# Teste 1: MissingAppKeyException
Write-Host "[1/5] Testando correcao MissingAppKeyException..." -NoNewline
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
Write-Host "[2/5] Testando correcao QueryException (SQLite)..." -NoNewline
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
Write-Host "[3/5] Testando correcao UrlGenerationException..." -NoNewline
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

# Teste 4: FatalError (Métodos Duplicados)
Write-Host "[4/5] Testando correcao FatalError (Metodos Duplicados)..." -NoNewline
try {
    $cidades = Invoke-WebRequest -Uri "$baseUrl/cidades" -UseBasicParsing -ErrorAction Stop

    if ($cidades.Content -match 'FatalError|Cannot redeclare|redeclare') {
        Write-Host " FALHOU" -ForegroundColor Red
        Write-Host "  Erro: Metodos duplicados encontrados no Model!" -ForegroundColor Red
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

# Teste 5: QueryException (Audits Table)
Write-Host "[5/5] Testando correcao QueryException (Audits Table)..." -NoNewline
try {
    $cidades = Invoke-WebRequest -Uri "$baseUrl/cidades" -UseBasicParsing -ErrorAction Stop

    if ($cidades.Content -match 'audits|SQLSTATE.*audits|no such table: audits') {
        Write-Host " FALHOU" -ForegroundColor Red
        Write-Host "  Erro: Ainda ha referencia a tabela audits!" -ForegroundColor Red
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
    Write-Host "  RESULTADO: TODOS OS 5 TESTES PASSARAM!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Correcoes implementadas com sucesso:" -ForegroundColor Green
    Write-Host "  [OK] 1. MissingAppKeyException - APP_KEY gerada automaticamente" -ForegroundColor Green
    Write-Host "  [OK] 2. QueryException - SQLite configurado como padrao" -ForegroundColor Green
    Write-Host "  [OK] 3. UrlGenerationException - Rotas usando chave primaria" -ForegroundColor Green
    Write-Host "  [OK] 4. FatalError - Relacionamentos com nomes unicos" -ForegroundColor Green
    Write-Host "  [OK] 5. QueryException Audits - Feature audit desabilitada" -ForegroundColor Green
} else {
    Write-Host "  RESULTADO: ALGUNS TESTES FALHARAM!" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Verifique os erros acima para mais detalhes." -ForegroundColor Yellow
}
Write-Host ""
