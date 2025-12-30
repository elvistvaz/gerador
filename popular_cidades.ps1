Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Populando Cidades de Teste" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$cidades = @(
    @{nome='Salvador'; uf='BA'; ddd='71'; iss='2.5'},
    @{nome='São Paulo'; uf='SP'; ddd='11'; iss='5.0'},
    @{nome='Rio de Janeiro'; uf='RJ'; ddd='21'; iss='3.5'},
    @{nome='Brasília'; uf='DF'; ddd='61'; iss='2.0'},
    @{nome='Belo Horizonte'; uf='MG'; ddd='31'; iss='2.8'}
)

foreach ($cidade in $cidades) {
    Write-Host "Criando: $($cidade.nome)..." -NoNewline

    $cmd = "cd /d c:\java\workspace\Gerador\generated\xandel-laravel && C:\php82\php.exe artisan tinker --execute=`"`$c = new App\Models\Cidade(); `$c->nome_cidade = '$($cidade.nome)'; `$c->uf = '$($cidade.uf)'; `$c->ddd = '$($cidade.ddd)'; `$c->iss = $($cidade.iss); `$c->save();`""

    cmd /c $cmd | Out-Null

    Write-Host " OK" -ForegroundColor Green
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  5 Cidades criadas com sucesso!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Acesse: http://127.0.0.1:8888/cidades" -ForegroundColor Cyan
Write-Host ""
