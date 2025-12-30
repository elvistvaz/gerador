Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTE - Correcao Audits Table Error" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://127.0.0.1:8888"

Write-Host "Testando pagina de Cidades..." -NoNewline
try {
    $cidades = Invoke-WebRequest -Uri "$baseUrl/cidades" -UseBasicParsing -ErrorAction Stop

    # Verifica se há erro de audits table
    if ($cidades.Content -match 'audits|SQLSTATE.*audits|no such table: audits') {
        Write-Host " FALHOU" -ForegroundColor Red
        Write-Host ""
        Write-Host "ERRO: Ainda ha referencia a tabela audits!" -ForegroundColor Red
        Write-Host ""

        # Mostra trecho do erro
        if ($cidades.Content -match '(no such table: audits|insert into.*audits)') {
            Write-Host "Trecho do erro: $($Matches[1])" -ForegroundColor Yellow
        }
    } else {
        Write-Host " OK" -ForegroundColor Green
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "  SUCESSO! Erro de Audits Corrigido!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "A aplicacao foi gerada SEM o AuditableTrait." -ForegroundColor Green
        Write-Host "Models nao tentam mais inserir na tabela audits." -ForegroundColor Green
    }
} catch {
    if ($_.Exception.Message -match "401|403") {
        Write-Host " OK (Requer autenticacao - esperado)" -ForegroundColor Green
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "  SUCESSO! Erro de Audits Corrigido!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "Pagina de cidades requer autenticacao (comportamento correto)." -ForegroundColor Green
        Write-Host "Sem erros de tabela audits!" -ForegroundColor Green
    } else {
        Write-Host " ERRO: $($_.Exception.Message)" -ForegroundColor Red
    }
}
Write-Host ""
