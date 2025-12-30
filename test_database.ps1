try {
    Write-Host "Testando pagina de login com banco de dados..." -ForegroundColor Cyan
    $response = Invoke-WebRequest -Uri 'http://127.0.0.1:8888/login' -UseBasicParsing -ErrorAction Stop

    if ($response.Content -match 'QueryException|DatabaseException|SQLSTATE') {
        Write-Host "ERRO: Ainda ha erro de banco de dados" -ForegroundColor Red
        Write-Host "Detalhes: $($response.Content.Substring(0, [Math]::Min(500, $response.Content.Length)))"
    } else {
        Write-Host "OK: Pagina de login carregando sem erro de banco!" -ForegroundColor Green
        Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
        Write-Host "Tamanho: $($response.Content.Length) bytes" -ForegroundColor Green
    }
} catch {
    Write-Host "ERRO: $($_.Exception.Message)" -ForegroundColor Red
}
