try {
    $response = Invoke-WebRequest -Uri "http://localhost:8000" -UseBasicParsing
    Write-Host "Status: $($response.StatusCode)"
    Write-Host ""

    if ($response.Content -match '<title>([^<]+)</title>') {
        Write-Host "Page Title: $($matches[1])"
    }

    if ($response.Content -match 'MissingAppKeyException') {
        Write-Host "ERROR: APP_KEY still missing!"
    } elseif ($response.Content -match 'login|Login') {
        Write-Host "SUCCESS: Login page loaded correctly!"
    } else {
        Write-Host "Page loaded, checking content..."
    }

    Write-Host ""
    Write-Host "First 300 characters:"
    Write-Host $response.Content.Substring(0, [Math]::Min(300, $response.Content.Length))
} catch {
    Write-Host "Error accessing server: $_"
}
