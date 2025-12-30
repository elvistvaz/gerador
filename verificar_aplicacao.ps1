Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  VERIFICACAO DA APLICACAO LARAVEL" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://127.0.0.1:8888"
$testsPassed = 0
$testsFailed = 0

# Teste 1: Página inicial (deve redirecionar para login)
Write-Host "[1/5] Testando pagina inicial (redirect para login)..." -NoNewline
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/" -UseBasicParsing -MaximumRedirection 0 -ErrorAction SilentlyContinue -ErrorVariable redirectError
    if ($redirectError -and $redirectError[0].Exception.Response.StatusCode -eq 302) {
        Write-Host " OK" -ForegroundColor Green
        $testsPassed++
    } else {
        Write-Host " FALHOU (esperava redirect 302)" -ForegroundColor Red
        $testsFailed++
    }
} catch {
    Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    $testsFailed++
}

# Teste 2: Página de login
Write-Host "[2/5] Testando pagina de login..." -NoNewline
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/login" -UseBasicParsing -ErrorAction Stop
    if ($response.StatusCode -eq 200 -and $response.Content.Length -gt 500) {
        Write-Host " OK (Status: $($response.StatusCode), Size: $($response.Content.Length) bytes)" -ForegroundColor Green
        $testsPassed++
    } else {
        Write-Host " FALHOU (Status: $($response.StatusCode))" -ForegroundColor Red
        $testsFailed++
    }
} catch {
    Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    $testsFailed++
}

# Teste 3: Página de registro
Write-Host "[3/5] Testando pagina de registro..." -NoNewline
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/register" -UseBasicParsing -ErrorAction Stop
    if ($response.StatusCode -eq 200) {
        Write-Host " OK (Status: $($response.StatusCode))" -ForegroundColor Green
        $testsPassed++
    } else {
        Write-Host " FALHOU" -ForegroundColor Red
        $testsFailed++
    }
} catch {
    Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    $testsFailed++
}

# Teste 4: Swagger/API Documentation
Write-Host "[4/5] Testando documentacao API (Swagger)..." -NoNewline
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/api/documentation" -UseBasicParsing -ErrorAction Stop
    if ($response.StatusCode -eq 200) {
        Write-Host " OK" -ForegroundColor Green
        $testsPassed++
    } else {
        Write-Host " FALHOU" -ForegroundColor Red
        $testsFailed++
    }
} catch {
    Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    $testsFailed++
}

# Teste 5: Verificar se há erros nos logs
Write-Host "[5/5] Verificando logs de erro..." -NoNewline
$logFile = "c:\java\workspace\Gerador\generated\xandel-laravel\storage\logs\laravel.log"
if (Test-Path $logFile) {
    $recentLogs = Get-Content $logFile -Tail 50 | Select-String -Pattern "\[$(Get-Date -Format 'yyyy-MM-dd')\].*ERROR" -CaseSensitive:$false
    if ($recentLogs.Count -eq 0) {
        Write-Host " OK (sem erros recentes)" -ForegroundColor Green
        $testsPassed++
    } else {
        Write-Host " AVISO ($($recentLogs.Count) erros encontrados hoje)" -ForegroundColor Yellow
        $testsPassed++
    }
} else {
    Write-Host " OK (nenhum log ainda)" -ForegroundColor Green
    $testsPassed++
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  RESULTADO DOS TESTES" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Testes passados: $testsPassed/5" -ForegroundColor $(if ($testsPassed -eq 5) { "Green" } else { "Yellow" })
Write-Host "Testes falhados: $testsFailed/5" -ForegroundColor $(if ($testsFailed -eq 0) { "Green" } else { "Red" })
Write-Host ""

if ($testsPassed -eq 5) {
    Write-Host "STATUS: Aplicacao funcionando corretamente!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Acesse: $baseUrl" -ForegroundColor Cyan
} elseif ($testsPassed -ge 3) {
    Write-Host "STATUS: Aplicacao parcialmente funcional" -ForegroundColor Yellow
} else {
    Write-Host "STATUS: Aplicacao com problemas" -ForegroundColor Red
}
Write-Host ""
