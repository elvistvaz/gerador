try {
    Write-Host "Testing root page..."
    $response = Invoke-WebRequest -Uri 'http://127.0.0.1:8000/' -UseBasicParsing -ErrorAction Stop -MaximumRedirection 0 -ErrorVariable redirectError
    Write-Host "Status: $($response.StatusCode)"
} catch {
    if ($_.Exception.Response.StatusCode -eq 'Redirect' -or $_.Exception.Response.StatusCode -eq 'Found') {
        Write-Host "Redirect detected (expected)"
        Write-Host "Location: $($_.Exception.Response.Headers['Location'])"
    } else {
        Write-Host "Error: $($_.Exception.Message)"
    }
}

Write-Host "`nTesting login page..."
try {
    $response = Invoke-WebRequest -Uri 'http://127.0.0.1:8000/login' -UseBasicParsing -ErrorAction Stop
    Write-Host "Status: $($response.StatusCode)"

    if ($response.Content -match 'Internal Server Error|Exception|Missing parameter') {
        Write-Host "ERROR: Found error in page"
    } else {
        Write-Host "SUCCESS: Login page loaded successfully"
    }
} catch {
    Write-Host "Error: $($_.Exception.Message)"
}
