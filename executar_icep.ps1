Write-Host "================================================================"
Write-Host "  EXECUTAR ICEP - Laravel Server"
Write-Host "================================================================"
Write-Host ""

# Verifica se o projeto existe
$projectDir = Join-Path $PSScriptRoot "generated\icep-laravel"
if (-not (Test-Path $projectDir)) {
    Write-Host "  ERRO: Projeto nao encontrado!"
    Write-Host ""
    Write-Host "  Execute primeiro: gerar_icep.bat"
    Write-Host ""
    Read-Host "Pressione Enter para continuar"
    exit 1
}

Write-Host "[1/3] Parando servidor anterior (se estiver rodando)..."
$processes = Get-NetTCPConnection -LocalPort 8000 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess -Unique
foreach ($processId in $processes) {
    Write-Host "  - Matando processo $processId na porta 8000..."
    Stop-Process -Id $processId -Force -ErrorAction SilentlyContinue
}
Write-Host "  OK - Porta 8000 liberada"
Write-Host ""

Write-Host "[2/3] Limpando caches Laravel..."
Push-Location $projectDir
try {
    & "C:\php82\php.exe" artisan config:clear 2>&1 | Out-Null
    & "C:\php82\php.exe" artisan cache:clear 2>&1 | Out-Null
    & "C:\php82\php.exe" artisan route:clear 2>&1 | Out-Null
    & "C:\php82\php.exe" artisan view:clear 2>&1 | Out-Null
    Write-Host "  OK - Caches limpos"
    Write-Host ""

    Write-Host "[3/3] Iniciando servidor Laravel..."
    Write-Host ""
    Write-Host "================================================================"
    Write-Host "  SERVIDOR INICIADO!"
    Write-Host "================================================================"
    Write-Host ""
    Write-Host "  URL: http://localhost:8000"
    Write-Host ""
    Write-Host "  Usuario padrao:"
    Write-Host "    Email: admin@admin.com"
    Write-Host "    Senha: admin"
    Write-Host ""
    Write-Host "  Pressione Ctrl+C para parar o servidor"
    Write-Host "================================================================"
    Write-Host ""

    & "C:\php82\php.exe" artisan serve --port=8000 --host=127.0.0.1
} finally {
    Pop-Location
}
