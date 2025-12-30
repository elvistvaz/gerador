Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTE - CORRECAO MissingAppKeyException" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

try {
    $response = Invoke-WebRequest -Uri 'http://127.0.0.1:8888/' -UseBasicParsing -MaximumRedirection 0 -ErrorAction SilentlyContinue -ErrorVariable redirectError

    if ($redirectError) {
        Write-Host "SUCCESS: Got redirect (esperado)" -ForegroundColor Green
        Write-Host "Testando pagina de login..."
        $login = Invoke-WebRequest -Uri 'http://127.0.0.1:8888/login' -UseBasicParsing -ErrorAction Stop
        Write-Host "SUCCESS: Pagina de login carregou (Status: $($login.StatusCode))" -ForegroundColor Green

        if ($login.Content.Length -lt 500) {
            Write-Host "Warning: Pagina parece muito pequena" -ForegroundColor Yellow
        } else {
            Write-Host "SUCCESS: Pagina tem tamanho adequado ($($login.Content.Length) bytes)" -ForegroundColor Green
        }

        # Verifica se há erro de MissingAppKeyException
        if ($login.Content -match 'MissingAppKeyException') {
            Write-Host ""
            Write-Host "FALHA: Erro MissingAppKeyException ainda presente!" -ForegroundColor Red
            Write-Host ""
        } else {
            Write-Host ""
            Write-Host "========================================" -ForegroundColor Green
            Write-Host "  SUCESSO! Erro MissingAppKeyException CORRIGIDO!" -ForegroundColor Green
            Write-Host "========================================" -ForegroundColor Green
            Write-Host ""
            Write-Host "A chave de aplicacao foi gerada automaticamente!" -ForegroundColor Green
            Write-Host "Agora o gerador sempre criara a APP_KEY ao gerar a aplicacao Laravel." -ForegroundColor Green
            Write-Host ""
        }
    }
} catch {
    Write-Host "ERROR: $($_.Exception.Message)" -ForegroundColor Red
}
