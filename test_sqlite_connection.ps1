Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TESTE - Conexao SQLite" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

try {
    Write-Host "Testando pagina de login..."
    $login = Invoke-WebRequest -Uri 'http://127.0.0.1:8888/login' -UseBasicParsing -ErrorAction Stop

    # Verifica se há erro de banco de dados no conteúdo
    if ($login.Content -match 'QueryException|SQLSTATE|Connection refused|No such file or directory') {
        Write-Host ""
        Write-Host "ERRO: Problema de conexao com banco de dados encontrado!" -ForegroundColor Red
        Write-Host ""

        # Mostra trecho do erro
        if ($login.Content -match 'SQLSTATE\[([^\]]+)\]') {
            Write-Host "Codigo SQLSTATE: $($Matches[1])" -ForegroundColor Yellow
        }
    } else {
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "  SUCESSO! Aplicacao carrega sem erros de banco!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "Configuracao SQLite funcionando corretamente!" -ForegroundColor Green
        Write-Host "Pagina de login: OK (Status: $($login.StatusCode), Tamanho: $($login.Content.Length) bytes)" -ForegroundColor Green
        Write-Host ""
    }
} catch {
    Write-Host "ERROR: $($_.Exception.Message)" -ForegroundColor Red
}
