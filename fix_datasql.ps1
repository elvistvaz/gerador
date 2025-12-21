$filePath = 'c:\java\workspace\Gerador\generated\icep\back\src\main\resources\data.sql'
$content = [System.IO.File]::ReadAllText($filePath)
$content = $content -replace [char]0xFEFF, ''
$utf8 = New-Object System.Text.UTF8Encoding($false)
[System.IO.File]::WriteAllText($filePath, $content, $utf8)
Write-Host 'Done'
