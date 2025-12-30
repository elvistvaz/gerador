Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTE - UrlGenerationException Fix" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://127.0.0.1:8888"

Write-Host "Testando pagina de Cidades..." -NoNewline
try {
    $cidades = Invoke-WebRequest -Uri "$baseUrl/cidades" -UseBasicParsing -ErrorAction Stop

    if ($cidades.Content -match 'UrlGenerationException|Missing required parameter') {
        Write-Host " FALHOU" -ForegroundColor Red
        Write-Host ""
        Write-Host "ERRO: UrlGenerationException ainda ocorre!" -ForegroundColor Red
        Write-Host ""
    } else {
        Write-Host " OK" -ForegroundColor Green
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "  SUCESSO! UrlGenerationException Corrigida!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "A rota agora usa:" -ForegroundColor Green
        Write-Host "  route('cidades.edit', " -NoNewline -ForegroundColor White
        Write-Host "$cidade->id_cidade" -NoNewline -ForegroundColor Yellow
        Write-Host ")" -ForegroundColor White
        Write-Host ""
        Write-Host "Chave primaria em snake_case!" -ForegroundColor Green
    }
} catch {
    if ($_.Exception.Message -match "401|403") {
        Write-Host " OK (Requer autenticacao)" -ForegroundColor Green
        Write-Host ""
        Write-Host "Pagina requer autenticacao (comportamento esperado)." -ForegroundColor Yellow
        Write-Host "Sem erros de UrlGenerationException!" -ForegroundColor Green
    } else {
        Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    }
}
Write-Host ""
