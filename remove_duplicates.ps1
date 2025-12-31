$migrationsPath = "c:\java\workspace\Gerador\generated\xandel-laravel\database\migrations"
$files = Get-ChildItem -Path $migrationsPath -Filter "*.php" | Where-Object { $_.Name -notlike "0001_01_01*" }

$grouped = $files | ForEach-Object {
    $pattern = $_.Name -replace '^\d{4}_\d{2}_\d{2}_\d{6}_', ''
    [PSCustomObject]@{
        Pattern = $pattern
        File = $_
        Timestamp = [datetime]::ParseExact($_.Name.Substring(0, 17), "yyyy_MM_dd_HHmmss", $null)
    }
} | Group-Object Pattern

$duplicates = $grouped | Where-Object { $_.Count -gt 1 }

foreach ($group in $duplicates) {
    Write-Host "Found $($group.Count) files for $($group.Name)"
    $sorted = $group.Group | Sort-Object Timestamp
    $keep = $sorted[0]
    Write-Host "  Keeping: $($keep.File.Name)"

    for ($i = 1; $i -lt $sorted.Count; $i++) {
        $toDelete = $sorted[$i]
        Write-Host "  Deleting: $($toDelete.File.Name)"
        Remove-Item $toDelete.File.FullName -Force
    }
}

Write-Host "`nDone! Removed duplicates."
