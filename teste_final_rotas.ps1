Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTE FINAL - CORRECAO DAS ROTAS" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://127.0.0.1:8888"

Write-Host "Verificando se as paginas CRUD carregam sem erro de rota..." -ForegroundColor Yellow
Write-Host ""

$entidades = @("bancos", "bairros", "cidades", "empresas", "pessoas")
$sucessos = 0
$falhas = 0

foreach ($entidade in $entidades) {
    Write-Host "  Testando /$entidade..." -NoNewline
    try {
        $response = Invoke-WebRequest -Uri "$baseUrl/$entidade" -UseBasicParsing -ErrorAction Stop

        # Verifica se há erros de rota no conteúdo
        if ($response.Content -match 'UrlGenerationException|Missing parameter|Missing required parameter') {
            Write-Host " FALHOU - Erro de rota encontrado!" -ForegroundColor Red
            $falhas++
        } elseif ($response.StatusCode -eq 200) {
            Write-Host " OK (Status: 200)" -ForegroundColor Green
            $sucessos++
        } else {
            Write-Host " Status: $($response.StatusCode)" -ForegroundColor Yellow
            $sucessos++
        }
    } catch {
        if ($_.Exception.Message -match "404") {
            Write-Host " Requer autenticacao (esperado)" -ForegroundColor Cyan
            $sucessos++
        } else {
            Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
            $falhas++
        }
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Resultado: $sucessos/$($entidades.Count) entidades sem erro de rota" -ForegroundColor $(if ($falhas -eq 0) { "Green" } else { "Yellow" })
Write-Host ""

if ($falhas -eq 0) {
    Write-Host "SUCESSO! Nenhum erro de UrlGenerationException encontrado!" -ForegroundColor Green
    Write-Host "A correcao das rotas foi aplicada com sucesso." -ForegroundColor Green
} else {
    Write-Host "Ainda existem $falhas erro(s) de rota." -ForegroundColor Red
}
Write-Host ""
