try {
    Write-Host "Testing on port 8888..."
    $response = Invoke-WebRequest -Uri 'http://127.0.0.1:8888/' -UseBasicParsing -MaximumRedirection 0 -ErrorAction SilentlyContinue -ErrorVariable redirectError

    if ($redirectError) {
        Write-Host "Got redirect (expected)"
        Write-Host "Testing login page..."
        $login = Invoke-WebRequest -Uri 'http://127.0.0.1:8888/login' -UseBasicParsing -ErrorAction Stop
        Write-Host "SUCCESS: Login page loaded (Status: $($login.StatusCode))"

        if ($login.Content.Length -lt 500) {
            Write-Host "Warning: Page seems too small"
        }
    }
} catch {
    Write-Host "ERROR: $($_.Exception.Message)"
}
