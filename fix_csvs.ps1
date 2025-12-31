$csvDir = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial"

Get-ChildItem -Path $csvDir -Filter "*.csv" | ForEach-Object {
    $file = $_.FullName
    $content = Get-Content $file -Encoding UTF8

    if ($content.Length -lt 2) { return }

    $header = $content[0]
    $numColumns = ($header -split ';').Count

    $hasEmpty = $false
    for ($i = 1; $i -lt $content.Length; $i++) {
        $fields = $content[$i] -split ';'
        if ($fields.Count -lt $numColumns -or ($fields -match '^\s*$')) {
            $hasEmpty = $true
            break
        }
    }

    if ($hasEmpty) {
        Write-Host "Arquivo com dados vazios: $($_.Name)" -ForegroundColor Yellow
    }
}
