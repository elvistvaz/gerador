package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.MetaModel;

/**
 * Template para geração de arquivos de configuração do projeto Laravel.
 */
public class LaravelProjectTemplate {

    private ProjectConfig projectConfig;

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public String generateComposerJson(MetaModel metaModel) {
        String projectName = metaModel.getMetadata().getName().toLowerCase();
        String description = metaModel.getMetadata().getDescription();

        return """
{
    "name": "laravel/%s",
    "type": "project",
    "description": "%s",
    "keywords": ["laravel", "framework"],
    "license": "MIT",
    "require": {
        "php": "^8.2",
        "darkaonline/l5-swagger": "^9.0",
        "guzzlehttp/guzzle": "^7.9",
        "laravel/breeze": "^2.3",
        "laravel/framework": "^12.0",
        "laravel/sanctum": "^4.0",
        "laravel/tinker": "^2.10",
        "owen-it/laravel-auditing": "^14.0",
        "tymon/jwt-auth": "^2.2"
    },
    "require-dev": {
        "fakerphp/faker": "^1.23",
        "laravel/pint": "^1.13",
        "laravel/sail": "^1.41",
        "mockery/mockery": "^1.6",
        "nunomaduro/collision": "^8.8",
        "phpunit/phpunit": "^12.1"
    },
    "autoload": {
        "psr-4": {
            "App\\\\": "app/",
            "Database\\\\Factories\\\\": "database/factories/",
            "Database\\\\Seeders\\\\": "database/seeders/"
        }
    },
    "autoload-dev": {
        "psr-4": {
            "Tests\\\\": "tests/"
        }
    },
    "scripts": {
        "post-autoload-dump": [
            "Illuminate\\\\Foundation\\\\ComposerScripts::postAutoloadDump",
            "@php artisan package:discover --ansi"
        ],
        "post-update-cmd": [
            "@php artisan vendor:publish --tag=laravel-assets --ansi --force"
        ],
        "post-root-package-install": [
            "@php -r \\"file_exists('.env') || copy('.env.example', '.env');\\""
        ],
        "post-create-project-cmd": [
            "@php artisan key:generate --ansi"
        ]
    },
    "config": {
        "optimize-autoloader": true,
        "preferred-install": "dist",
        "sort-packages": true
    },
    "minimum-stability": "stable",
    "prefer-stable": true
}
""".formatted(projectName, description);
    }

    public String generateEnvFile(MetaModel metaModel) {
        String appName = metaModel.getMetadata().getName();
        String dbName = appName.toLowerCase();

        return """
APP_NAME="%s"
APP_ENV=local
APP_KEY=
APP_DEBUG=true
APP_TIMEZONE=America/Sao_Paulo
APP_URL=http://localhost:8000
APP_LOCALE=pt_BR
APP_FALLBACK_LOCALE=en

# SQLite para desenvolvimento (modo em memória)
DB_CONNECTION=sqlite
DB_DATABASE=database/database.sqlite

# MySQL para produção (descomente e configure quando necessário)
# DB_CONNECTION=mysql
# DB_HOST=127.0.0.1
# DB_PORT=3306
# DB_DATABASE=%s
# DB_USERNAME=root
# DB_PASSWORD=

CACHE_STORE=file
QUEUE_CONNECTION=sync
SESSION_DRIVER=file
SESSION_LIFETIME=120
SESSION_DOMAIN=null
SESSION_SECURE_COOKIE=false
SESSION_HTTP_ONLY=true
SESSION_SAME_SITE=lax

MAIL_MAILER=smtp
MAIL_HOST=smtp.mailtrap.io
MAIL_PORT=2525
MAIL_USERNAME=null
MAIL_PASSWORD=null
MAIL_ENCRYPTION=null
MAIL_FROM_ADDRESS="noreply@%s.com"
MAIL_FROM_NAME="${APP_NAME}"

JWT_SECRET=
JWT_ALGO=HS256
JWT_TTL=1440
JWT_REFRESH_TTL=20160

SANCTUM_STATEFUL_DOMAINS=localhost,localhost:3000,127.0.0.1
""".formatted(appName, dbName, dbName);
    }

    public String generateEnvExample(MetaModel metaModel) {
        return generateEnvFile(metaModel);
    }

    public String generateArtisan() {
        return """
#!/usr/bin/env php
<?php

define('LARAVEL_START', microtime(true));

require __DIR__.'/vendor/autoload.php';

$app = require_once __DIR__.'/bootstrap/app.php';

$kernel = $app->make(Illuminate\\Contracts\\Console\\Kernel::class);

$status = $kernel->handle(
    $input = new Symfony\\Component\\Console\\Input\\ArgvInput,
    new Symfony\\Component\\Console\\Output\\ConsoleOutput
);

$kernel->terminate($input, $status);

exit($status);
""";
    }

    public String generateBaseModel() {
        boolean auditEnabled = projectConfig != null && projectConfig.isAuditEnabled();

        if (auditEnabled) {
            return """
<?php

namespace App\\Models;

use Illuminate\\Database\\Eloquent\\Model;
use OwenIt\\Auditing\\Contracts\\Auditable;
use OwenIt\\Auditing\\Auditable as AuditableTrait;

class BaseModel extends Model implements Auditable
{
    use AuditableTrait;
}
""";
        } else {
            return """
<?php

namespace App\\Models;

use Illuminate\\Database\\Eloquent\\Model;

class BaseModel extends Model
{
    // Base model for all application models
}
""";
        }
    }

    public String generateBaseController() {
        return """
<?php

namespace App\\Http\\Controllers;

abstract class Controller
{
    //
}
""";
    }

    public String generateAppServiceProvider() {
        return """
<?php

namespace App\\Providers;

use Illuminate\\Support\\ServiceProvider;
use Illuminate\\Pagination\\Paginator;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     */
    public function register(): void
    {
        //
    }

    /**
     * Bootstrap any application services.
     */
    public function boot(): void
    {
        // Configura a paginação para usar Bootstrap 5
        Paginator::useBootstrapFive();
    }
}
""";
    }

    public String generateWebRoutes() {
        return """
<?php

use Illuminate\\Support\\Facades\\Route;

Route::get('/', function () {
    return view('welcome');
});
""";
    }

    public String generateApiRoutes() {
        return """
<?php

use Illuminate\\Support\\Facades\\Route;

// API Routes V1
Route::prefix('v1')->group(base_path('routes/api/v1.php'));
""";
    }

    public String generateDatabaseConfig() {
        return """
<?php

use Illuminate\\Support\\Str;

return [
    'default' => env('DB_CONNECTION', 'mysql'),

    'connections' => [
        'mysql' => [
            'driver' => 'mysql',
            'url' => env('DB_URL'),
            'host' => env('DB_HOST', '127.0.0.1'),
            'port' => env('DB_PORT', '3306'),
            'database' => env('DB_DATABASE', 'laravel'),
            'username' => env('DB_USERNAME', 'root'),
            'password' => env('DB_PASSWORD', ''),
            'unix_socket' => env('DB_SOCKET', ''),
            'charset' => env('DB_CHARSET', 'utf8mb4'),
            'collation' => env('DB_COLLATION', 'utf8mb4_unicode_ci'),
            'prefix' => '',
            'prefix_indexes' => true,
            'strict' => true,
            'engine' => null,
            'options' => extension_loaded('pdo_mysql') ? array_filter([
                PDO::MYSQL_ATTR_SSL_CA => env('MYSQL_ATTR_SSL_CA'),
            ]) : [],
        ],
    ],

    'migrations' => [
        'table' => 'migrations',
        'update_date_on_publish' => true,
    ],
];
""";
    }

    public String generateCorsConfig() {
        return """
<?php

return [
    'paths' => ['api/*', 'sanctum/csrf-cookie'],
    'allowed_methods' => ['*'],
    'allowed_origins' => ['*'],
    'allowed_origins_patterns' => [],
    'allowed_headers' => ['*'],
    'exposed_headers' => [],
    'max_age' => 0,
    'supports_credentials' => true,
];
""";
    }

    public String generateSessionConfig() {
        return """
<?php

use Illuminate\\Support\\Str;

return [
    'driver' => env('SESSION_DRIVER', 'file'),
    'lifetime' => env('SESSION_LIFETIME', 120),
    'expire_on_close' => false,
    'encrypt' => false,
    'files' => storage_path('framework/sessions'),
    'connection' => env('SESSION_CONNECTION'),
    'table' => env('SESSION_TABLE', 'sessions'),
    'store' => env('SESSION_STORE'),
    'lottery' => [2, 100],
    'cookie' => env(
        'SESSION_COOKIE',
        Str::slug(env('APP_NAME', 'laravel'), '_').'_session'
    ),
    'path' => env('SESSION_PATH', '/'),
    'domain' => env('SESSION_DOMAIN'),
    'secure' => env('SESSION_SECURE_COOKIE', false),
    'http_only' => env('SESSION_HTTP_ONLY', true),
    'same_site' => env('SESSION_SAME_SITE', 'lax'),
    'partitioned' => env('SESSION_PARTITIONED_COOKIE', false),
];
""";
    }

    public String generateAuthConfig() {
        return """
<?php

return [
    'defaults' => [
        'guard' => env('AUTH_GUARD', 'web'),
        'passwords' => env('AUTH_PASSWORD_BROKER', 'users'),
    ],

    'guards' => [
        'web' => [
            'driver' => 'session',
            'provider' => 'users',
        ],
        'api' => [
            'driver' => 'sanctum',
            'provider' => 'users',
        ],
    ],

    'providers' => [
        'users' => [
            'driver' => 'eloquent',
            'model' => env('AUTH_MODEL', App\\Models\\User::class),
        ],
    ],

    'passwords' => [
        'users' => [
            'provider' => 'users',
            'table' => env('AUTH_PASSWORD_RESET_TOKEN_TABLE', 'password_reset_tokens'),
            'expire' => 60,
            'throttle' => 60,
        ],
    ],

    'password_timeout' => env('AUTH_PASSWORD_TIMEOUT', 10800),
];
""";
    }

    public String generateDatabaseSeeder(MetaModel metaModel) {
        return """
<?php

namespace Database\\Seeders;

use Illuminate\\Database\\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     */
    public function run(): void
    {
        // Chama os seeders de carga inicial
        $this->call([
            InitialDataSeeder::class,
        ]);
    }
}
""";
    }

    public String generateInitialDataSeeder(String tableName, java.util.List<java.util.Map<String, String>> data) {
        StringBuilder insertStatements = new StringBuilder();

        for (java.util.Map<String, String> row : data) {
            insertStatements.append("            DB::table('").append(tableName).append("')->insert([\n");

            for (java.util.Map.Entry<String, String> entry : row.entrySet()) {
                String column = entry.getKey();
                String value = entry.getValue();

                if (value == null || value.trim().isEmpty()) {
                    insertStatements.append("                '").append(column).append("' => null,\n");
                } else if (column.toLowerCase().matches(".*(id|quantidade|ordem)$") && value.matches("\\d+")) {
                    // Numeric values
                    insertStatements.append("                '").append(column).append("' => ").append(value).append(",\n");
                } else {
                    // String values - escape single quotes
                    String escapedValue = value.replace("'", "\\'");
                    insertStatements.append("                '").append(column).append("' => '").append(escapedValue).append("',\n");
                }
            }

            insertStatements.append("            ]);\n\n");
        }

        return """
<?php

namespace Database\\Seeders;

use Illuminate\\Database\\Seeder;
use Illuminate\\Support\\Facades\\DB;

class InitialDataSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        // Carga inicial de dados
%s    }
}
""".formatted(insertStatements.toString());
    }

    public String generateReadme(MetaModel metaModel) {
        String projectName = metaModel.getMetadata().getName();
        String description = metaModel.getMetadata().getDescription();

        return """
# %s

%s

## Instalação

1. Clone o repositório
2. Instale as dependências:
   ```bash
   composer install
   ```
3. Copie o arquivo .env.example para .env:
   ```bash
   cp .env.example .env
   ```
4. Gere a chave da aplicação:
   ```bash
   php artisan key:generate
   ```
5. Configure o banco de dados no arquivo .env
6. Execute as migrations:
   ```bash
   php artisan migrate
   ```

## Executar

```bash
php artisan serve
```

A aplicação estará disponível em http://localhost:8000

## API

A API REST está disponível em http://localhost:8000/api/v1

## Documentação

A documentação da API (Swagger) está disponível em http://localhost:8000/api/documentation

## Gerado automaticamente

Este código foi gerado automaticamente pelo Gerador MetaModel.
""".formatted(projectName, description);
    }

    public String generateBootstrapApp() {
        return """
<?php

use Illuminate\\Foundation\\Application;
use Illuminate\\Foundation\\Configuration\\Exceptions;
use Illuminate\\Foundation\\Configuration\\Middleware;

return Application::configure(basePath: dirname(__DIR__))
    ->withRouting(
        web: __DIR__.'/../routes/web.php',
        api: __DIR__.'/../routes/api.php',
        commands: __DIR__.'/../routes/console.php',
        health: '/up',
    )
    ->withMiddleware(function (Middleware $middleware) {
        // Middlewares API
        $middleware->api(prepend: [
            \\Laravel\\Sanctum\\Http\\Middleware\\EnsureFrontendRequestsAreStateful::class,
        ]);

        // Aliases de middleware
        $middleware->alias([
            'auth' => \\Illuminate\\Auth\\Middleware\\Authenticate::class,
            'verified' => \\App\\Http\\Middleware\\EnsureEmailIsVerified::class,
        ]);

        // Os middlewares web (session, CSRF, etc) são carregados automaticamente pelo Laravel 12
    })
    ->withExceptions(function (Exceptions $exceptions) {
        //
    })->create();
""";
    }

    public String generateWelcomeView(MetaModel metaModel) {
        String projectName = metaModel.getMetadata() != null
            ? metaModel.getMetadata().getName()
            : "Laravel";

        return """
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>%s - Laravel API</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .container {
            background: white;
            border-radius: 20px;
            padding: 60px 40px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            max-width: 800px;
            width: 100%%;
            text-align: center;
        }
        h1 {
            font-size: 3em;
            color: #667eea;
            margin-bottom: 10px;
            font-weight: 700;
        }
        .subtitle {
            font-size: 1.2em;
            color: #666;
            margin-bottom: 40px;
        }
        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }
        .info-card {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            border-left: 4px solid #667eea;
        }
        .info-card h3 {
            color: #667eea;
            margin-bottom: 10px;
            font-size: 1.1em;
        }
        .info-card p {
            color: #333;
            font-size: 1.1em;
            font-weight: 600;
        }
        .api-section {
            background: #f0f4ff;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        .api-section h2 {
            color: #764ba2;
            margin-bottom: 15px;
        }
        .endpoint {
            background: white;
            padding: 15px;
            border-radius: 5px;
            margin: 10px 0;
            font-family: 'Courier New', monospace;
            color: #333;
            border-left: 3px solid #764ba2;
            text-align: left;
        }
        .badge {
            display: inline-block;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: 600;
            margin: 5px;
        }
        .badge-success {
            background: #28a745;
            color: white;
        }
        .features {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 10px;
            margin-top: 30px;
        }
        .footer {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 2px solid #eee;
            color: #999;
            font-size: 0.9em;
        }
        .status-indicator {
            width: 12px;
            height: 12px;
            background: #28a745;
            border-radius: 50%%;
            display: inline-block;
            animation: pulse 2s infinite;
            margin-right: 5px;
        }
        @keyframes pulse {
            0%%, 100%% { opacity: 1; }
            50%% { opacity: 0.5; }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><span class="status-indicator"></span>%s</h1>
        <p class="subtitle">API REST Laravel - Gerado Automaticamente</p>

        <div class="info-grid">
            <div class="info-card">
                <h3>Framework</h3>
                <p>Laravel 12.0</p>
            </div>
            <div class="info-card">
                <h3>PHP Version</h3>
                <p>{{ PHP_VERSION }}</p>
            </div>
            <div class="info-card">
                <h3>Ambiente</h3>
                <p>{{ config('app.env') }}</p>
            </div>
            <div class="info-card">
                <h3>Status</h3>
                <p>Online</p>
            </div>
        </div>

        <div class="api-section">
            <h2>📡 Endpoints da API</h2>
            <div class="endpoint">
                <strong>Base URL:</strong> http://127.0.0.1:8000/api/v1
            </div>
            <div class="endpoint">
                <strong>Exemplo:</strong> GET /api/v1/{resource}
            </div>
            <div class="endpoint">
                <strong>Docs:</strong> /api/documentation (Swagger)
            </div>
        </div>

        <div class="features">
            <span class="badge badge-success">✓ Autenticação JWT</span>
            <span class="badge badge-success">✓ Laravel Sanctum</span>
            <span class="badge badge-success">✓ Soft Deletes</span>
            <span class="badge badge-success">✓ Form Requests</span>
            <span class="badge badge-success">✓ API Resources</span>
            <span class="badge badge-success">✓ Auditoria</span>
            <span class="badge badge-success">✓ Swagger Docs</span>
            <span class="badge badge-success">✓ CORS Configurado</span>
        </div>

        <div class="footer">
            <p>🤖 Gerado automaticamente pelo Gerador Full-Stack</p>
            <p>Laravel {{ app()->version() }} | API RESTful</p>
        </div>
    </div>
</body>
</html>
""".formatted(projectName, projectName);
    }

    public String generatePublicIndexPhp() {
        return """
<?php

use Illuminate\\Http\\Request;

define('LARAVEL_START', microtime(true));

// Determine if the application is in maintenance mode...
if (file_exists($maintenance = __DIR__.'/../storage/framework/maintenance.php')) {
    require $maintenance;
}

// Register the Composer autoloader...
require __DIR__.'/../vendor/autoload.php';

// Bootstrap Laravel and handle the request...
(require_once __DIR__.'/../bootstrap/app.php')
    ->handleRequest(Request::capture());
""";
    }

    public String generateLayoutBlade(MetaModel metaModel) {
        // Gerar menu dinâmico a partir da configuração UI ou entidades
        StringBuilder menuItems = new StringBuilder();

        if (projectConfig != null && hasMenuConfiguration()) {
            // Gerar menu a partir da configuração JSON
            menuItems.append(generateMenuFromConfig());
        } else if (metaModel != null && metaModel.getEntities() != null) {
            // Fallback: Gerar menu simples a partir das entidades
            java.util.Set<String> uniqueEntities = new java.util.LinkedHashSet<>();
            for (br.com.gerador.metamodel.model.Entity entity : metaModel.getEntities()) {
                uniqueEntities.add(entity.getName());
            }

            for (String entityName : uniqueEntities) {
                String entityNameLower = toLowerCamelCase(entityName);
                String entityLabel = toHumanReadable(entityName);

                menuItems.append("            <li class=\"menu-item\">\n")
                         .append("                <a href=\"{{ url('/")
                         .append(entityNameLower)
                         .append("') }}\">\n")
                         .append("                    <i class=\"fas fa-list\"></i> ")
                         .append(entityLabel)
                         .append("\n")
                         .append("                </a>\n")
                         .append("            </li>\n");
            }
        }

        return """
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>@yield('title', config('app.name'))</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
            min-height: 100vh;
            display: flex;
        }

        /* Sidebar */
        .sidebar {
            width: 250px;
            background: linear-gradient(180deg, #667eea 0%%, #764ba2 100%%);
            min-height: 100vh;
            padding: 0;
            position: fixed;
            left: 0;
            top: 0;
            bottom: 0;
            overflow-y: auto;
            box-shadow: 2px 0 10px rgba(0,0,0,0.1);
        }

        .sidebar-header {
            padding: 1.5rem 1rem;
            background: rgba(0,0,0,0.1);
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }

        .sidebar-header h3 {
            color: white;
            font-size: 1.2rem;
            margin: 0;
            font-weight: 600;
        }

        .sidebar-menu {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .sidebar-menu .menu-item {
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }

        .sidebar-menu .menu-item > a {
            display: block;
            padding: 0.9rem 1rem;
            color: rgba(255,255,255,0.9);
            text-decoration: none;
            transition: all 0.3s;
        }

        .sidebar-menu .menu-item > a:hover {
            background: rgba(255,255,255,0.1);
            color: white;
            padding-left: 1.5rem;
        }

        .sidebar-menu .menu-item > a i {
            width: 20px;
            margin-right: 10px;
        }

        .sidebar-menu .submenu {
            list-style: none;
            padding: 0;
            margin: 0;
            background: rgba(0,0,0,0.1);
            display: none;
        }

        .sidebar-menu .submenu.show {
            display: block;
        }

        .sidebar-menu .submenu a {
            display: block;
            padding: 0.7rem 1rem 0.7rem 3rem;
            color: rgba(255,255,255,0.8);
            text-decoration: none;
            font-size: 0.9rem;
            transition: all 0.3s;
        }

        .sidebar-menu .submenu a:hover {
            background: rgba(255,255,255,0.1);
            color: white;
        }

        .sidebar-footer {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 1rem;
            background: rgba(0,0,0,0.2);
            border-top: 1px solid rgba(255,255,255,0.1);
        }

        .sidebar-footer .user-info {
            color: white;
            font-size: 0.9rem;
            margin-bottom: 0.5rem;
        }

        .sidebar-footer .btn-logout {
            background: rgba(255,255,255,0.2);
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            width: 100%%;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s;
        }

        .sidebar-footer .btn-logout:hover {
            background: rgba(255,255,255,0.3);
        }

        /* Main Content */
        .main-content {
            margin-left: 250px;
            flex: 1;
            padding: 2rem;
            min-height: 100vh;
        }

        .container-fluid {
            max-width: 100%%;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 2rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
        }

        .alert {
            padding: 1rem;
            border-radius: 5px;
            margin-bottom: 1rem;
        }

        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        /* Pagination styling */
        .pagination {
            margin: 0;
        }
        .pagination .page-link {
            padding: 0.5rem 0.75rem;
            font-size: 1rem;
            line-height: 1.25;
        }
        .pagination .page-item.active .page-link {
            background-color: #667eea;
            border-color: #667eea;
        }
        .pagination .page-item.disabled .page-link {
            color: #6c757d;
        }

        /* Menu toggle for mobile */
        .menu-toggle {
            cursor: pointer;
            user-select: none;
        }

        .menu-toggle i {
            transition: transform 0.3s;
        }

        .menu-toggle.active i {
            transform: rotate(90deg);
        }
    </style>
    @stack('styles')
</head>
<body>
    @auth
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="sidebar-header">
            <h3><i class="fas fa-graduation-cap"></i> {{ config('app.name') }}</h3>
        </div>

        <ul class="sidebar-menu">
            <li class="menu-item">
                <a href="{{ url('/dashboard') }}">
                    <i class="fas fa-home"></i> Dashboard
                </a>
            </li>

%s
        </ul>

        <div class="sidebar-footer">
            <div class="user-info">
                <i class="fas fa-user-circle"></i> {{ auth()->user()->name }}
            </div>
            <button class="btn-logout" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">
                <i class="fas fa-sign-out-alt"></i> Sair
            </button>
            <form id="logout-form" action="{{ url('/logout') }}" method="POST" style="display: none;">
                @csrf
            </form>
        </div>
    </div>
    @endauth

    <!-- Main Content -->
    <div class="main-content">
        @if(session('success'))
            <div class="alert alert-success">{{ session('success') }}</div>
        @endif

        @if(session('error'))
            <div class="alert alert-error">{{ session('error') }}</div>
        @endif

        @yield('content')
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function toggleSubmenu(event, submenuId) {
            event.preventDefault();
            const submenu = document.getElementById(submenuId);
            const toggle = event.currentTarget;

            submenu.classList.toggle('show');
            toggle.classList.toggle('active');
        }
    </script>

    @stack('scripts')
</body>
</html>
""".formatted(menuItems.toString());
    }

    // Métodos auxiliares para formatação
    private String toLowerCamelCase(String name) {
        if (name == null || name.isEmpty()) return name;
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    private String toHumanReadable(String name) {
        if (name == null || name.isEmpty()) return name;

        // Converte CamelCase para espaços
        String result = name.replaceAll("([A-Z])", " $1").trim();

        // Capitaliza primeira letra
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    public String generateLoginView() {
        return """
@extends('layouts.app')

@section('title', 'Login')

@section('content')
<div class="card" style="max-width: 450px; margin: 0 auto;">
    <h2 style="color: #667eea; margin-bottom: 1.5rem; text-align: center;">Login</h2>

    <form method="POST" action="{{ url('/login') }}">
        @csrf

        <div style="margin-bottom: 1rem;">
            <label style="display: block; margin-bottom: 0.5rem; color: #333; font-weight: 500;">Email</label>
            <input type="email" name="email" value="{{ old('email') }}" required
                   style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px; font-size: 1rem;">
            @error('email')
                <span style="color: #e3342f; font-size: 0.875rem;">{{ $message }}</span>
            @enderror
        </div>

        <div style="margin-bottom: 1.5rem;">
            <label style="display: block; margin-bottom: 0.5rem; color: #333; font-weight: 500;">Senha</label>
            <input type="password" name="password" required
                   style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px; font-size: 1rem;">
            @error('password')
                <span style="color: #e3342f; font-size: 0.875rem;">{{ $message }}</span>
            @enderror
        </div>

        <div style="margin-bottom: 1.5rem;">
            <label style="display: flex; align-items: center;">
                <input type="checkbox" name="remember" style="margin-right: 0.5rem;">
                <span style="color: #666;">Lembrar-me</span>
            </label>
        </div>

        <button type="submit" style="width: 100%; padding: 0.75rem; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border: none; border-radius: 5px; font-size: 1rem; font-weight: 600; cursor: pointer;">
            Entrar
        </button>
    </form>

    <p style="text-align: center; margin-top: 1.5rem; color: #666;">
        Não tem uma conta? <a href="{{ url('/register') }}" style="color: #667eea; text-decoration: none; font-weight: 500;">Cadastre-se</a>
    </p>
</div>
@endsection
""";
    }

    public String generateRegisterView() {
        return """
@extends('layouts.app')

@section('title', 'Cadastrar')

@section('content')
<div class="card" style="max-width: 450px; margin: 0 auto;">
    <h2 style="color: #667eea; margin-bottom: 1.5rem; text-align: center;">Cadastrar</h2>

    <form method="POST" action="{{ url('/register') }}">
        @csrf

        <div style="margin-bottom: 1rem;">
            <label style="display: block; margin-bottom: 0.5rem; color: #333; font-weight: 500;">Nome</label>
            <input type="text" name="name" value="{{ old('name') }}" required
                   style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px; font-size: 1rem;">
            @error('name')
                <span style="color: #e3342f; font-size: 0.875rem;">{{ $message }}</span>
            @enderror
        </div>

        <div style="margin-bottom: 1rem;">
            <label style="display: block; margin-bottom: 0.5rem; color: #333; font-weight: 500;">Email</label>
            <input type="email" name="email" value="{{ old('email') }}" required
                   style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px; font-size: 1rem;">
            @error('email')
                <span style="color: #e3342f; font-size: 0.875rem;">{{ $message }}</span>
            @enderror
        </div>

        <div style="margin-bottom: 1rem;">
            <label style="display: block; margin-bottom: 0.5rem; color: #333; font-weight: 500;">Senha</label>
            <input type="password" name="password" required
                   style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px; font-size: 1rem;">
            @error('password')
                <span style="color: #e3342f; font-size: 0.875rem;">{{ $message }}</span>
            @enderror
        </div>

        <div style="margin-bottom: 1.5rem;">
            <label style="display: block; margin-bottom: 0.5rem; color: #333; font-weight: 500;">Confirmar Senha</label>
            <input type="password" name="password_confirmation" required
                   style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px; font-size: 1rem;">
        </div>

        <button type="submit" style="width: 100%; padding: 0.75rem; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border: none; border-radius: 5px; font-size: 1rem; font-weight: 600; cursor: pointer;">
            Cadastrar
        </button>
    </form>

    <p style="text-align: center; margin-top: 1.5rem; color: #666;">
        Já tem uma conta? <a href="{{ url('/login') }}" style="color: #667eea; text-decoration: none; font-weight: 500;">Faça login</a>
    </p>
</div>
@endsection
""";
    }

    public String generateDashboardView() {
        return """
@extends('layouts.app')

@section('title', 'Dashboard')

@section('content')
<div class="card">
    <h2 style="color: #667eea; margin-bottom: 1.5rem;">Dashboard</h2>

    <p style="margin-bottom: 1rem;">Bem-vindo, <strong>{{ auth()->user()->name }}</strong>!</p>

    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem; margin-top: 2rem;">
        <div style="padding: 1.5rem; background: #f0f4ff; border-radius: 8px; border-left: 4px solid #667eea;">
            <h3 style="color: #667eea; margin-bottom: 0.5rem;">API REST</h3>
            <p style="color: #666;">Acesse a API em /api/v1</p>
        </div>

        <div style="padding: 1.5rem; background: #f0f4ff; border-radius: 8px; border-left: 4px solid #764ba2;">
            <h3 style="color: #764ba2; margin-bottom: 0.5rem;">Documentação</h3>
            <p style="color: #666;">Swagger em /api/documentation</p>
        </div>
    </div>
</div>
@endsection
""";
    }

    public String generateAuthController() {
        return """
<?php

namespace App\\Http\\Controllers;

use App\\Models\\User;
use Illuminate\\Http\\Request;
use Illuminate\\Support\\Facades\\Auth;
use Illuminate\\Support\\Facades\\Hash;

class AuthController extends Controller
{
    public function showLogin()
    {
        return view('auth.login');
    }

    public function login(Request $request)
    {
        $credentials = $request->validate([
            'email' => ['required', 'email'],
            'password' => ['required'],
        ]);

        if (Auth::attempt($credentials, $request->filled('remember'))) {
            $request->session()->regenerate();
            return redirect()->intended('/dashboard');
        }

        return back()->withErrors([
            'email' => 'As credenciais fornecidas não correspondem aos nossos registros.',
        ])->onlyInput('email');
    }

    public function showRegister()
    {
        return view('auth.register');
    }

    public function register(Request $request)
    {
        $validated = $request->validate([
            'name' => ['required', 'string', 'max:255'],
            'email' => ['required', 'string', 'email', 'max:255', 'unique:users'],
            'password' => ['required', 'string', 'min:8', 'confirmed'],
        ]);

        $user = User::create([
            'name' => $validated['name'],
            'email' => $validated['email'],
            'password' => Hash::make($validated['password']),
        ]);

        Auth::login($user);

        return redirect('/dashboard');
    }

    public function logout(Request $request)
    {
        Auth::logout();
        $request->session()->invalidate();
        $request->session()->regenerateToken();
        return redirect('/');
    }

    public function dashboard()
    {
        return view('dashboard');
    }
}
""";
    }

    public String generateUserModel() {
        return """
<?php

namespace App\\Models;

use Illuminate\\Database\\Eloquent\\Factories\\HasFactory;
use Illuminate\\Foundation\\Auth\\User as Authenticatable;
use Illuminate\\Notifications\\Notifiable;
use Laravel\\Sanctum\\HasApiTokens;

class User extends Authenticatable
{
    use HasApiTokens, HasFactory, Notifiable;

    protected $fillable = [
        'name',
        'email',
        'password',
    ];

    protected $hidden = [
        'password',
        'remember_token',
    ];

    protected $casts = [
        'email_verified_at' => 'datetime',
        'password' => 'hashed',
    ];
}
""";
    }

    public String generateUsersMigration() {
        return """
<?php

use Illuminate\\Database\\Migrations\\Migration;
use Illuminate\\Database\\Schema\\Blueprint;
use Illuminate\\Support\\Facades\\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('users', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->string('email')->unique();
            $table->timestamp('email_verified_at')->nullable();
            $table->string('password');
            $table->rememberToken();
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('users');
    }
};
""";
    }

    public String generateWebRoutesWithAuth() {
        return """
<?php

use Illuminate\\Support\\Facades\\Route;
use App\\Http\\Controllers\\AuthController;

// Página inicial redireciona conforme autenticação
Route::get('/', function () {
    return auth()->check() ? redirect('/dashboard') : redirect('/login');
});

// Rotas de autenticação
Route::get('/login', [AuthController::class, 'showLogin'])->name('login');
Route::post('/login', [AuthController::class, 'login']);
Route::get('/register', [AuthController::class, 'showRegister'])->name('register');
Route::post('/register', [AuthController::class, 'register']);
Route::post('/logout', [AuthController::class, 'logout'])->name('logout');

// Rotas protegidas
Route::middleware(['auth'])->group(function () {
    Route::get('/dashboard', [AuthController::class, 'dashboard'])->name('dashboard');
});
""";
    }

    /**
     * Verifica se existe configuração de menu na configuração do projeto.
     */
    private boolean hasMenuConfiguration() {
        try {
            if (projectConfig == null) return false;
            com.google.gson.JsonObject config = projectConfig.getConfig();
            return config.has("ui") &&
                   config.get("ui").isJsonObject() &&
                   config.getAsJsonObject("ui").has("menu") &&
                   config.getAsJsonObject("ui").get("menu").isJsonObject() &&
                   config.getAsJsonObject("ui").getAsJsonObject("menu").has("groups");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gera HTML do menu a partir da configuração JSON.
     */
    private String generateMenuFromConfig() {
        StringBuilder menu = new StringBuilder();

        try {
            com.google.gson.JsonObject config = projectConfig.getConfig();
            if (config.has("ui") && config.get("ui").isJsonObject()) {
                com.google.gson.JsonObject ui = config.getAsJsonObject("ui");
                if (ui.has("menu") && ui.get("menu").isJsonObject()) {
                    com.google.gson.JsonObject menuConfig = ui.getAsJsonObject("menu");
                    if (menuConfig.has("groups") && menuConfig.get("groups").isJsonArray()) {
                        com.google.gson.JsonArray groups = menuConfig.getAsJsonArray("groups");

                        for (com.google.gson.JsonElement groupElement : groups) {
                            com.google.gson.JsonObject group = groupElement.getAsJsonObject();
                            String groupName = group.get("name").getAsString();
                            String groupIcon = group.has("icon") ? group.get("icon").getAsString() : "fa-folder";

                            // Gerar ID único para o submenu
                            String submenuId = groupName.toLowerCase().replaceAll("\\s+", "_").replaceAll("[^a-z0-9_]", "");

                            menu.append("\n            <li class=\"menu-item\">\n");
                            menu.append("                <a href=\"#\" class=\"menu-toggle\" onclick=\"toggleSubmenu(event, '").append(submenuId).append("')\">\n");
                            menu.append("                    <i class=\"fas ").append(groupIcon).append("\"></i> ").append(groupName).append("\n");
                            menu.append("                    <i class=\"fas fa-chevron-right float-end\"></i>\n");
                            menu.append("                </a>\n");
                            menu.append("                <ul class=\"submenu\" id=\"").append(submenuId).append("\">\n");

                            if (group.has("items") && group.get("items").isJsonArray()) {
                                com.google.gson.JsonArray items = group.getAsJsonArray("items");
                                for (com.google.gson.JsonElement itemElement : items) {
                                    com.google.gson.JsonObject item = itemElement.getAsJsonObject();
                                    String entity = item.get("entity").getAsString();
                                    String label = item.has("label") ? item.get("label").getAsString() : entity;
                                    String icon = item.has("icon") ? item.get("icon").getAsString() : "fa-circle";

                                    String entityNameLower = toLowerCamelCase(entity);

                                    menu.append("                    <li><a href=\"{{ url('/").append(entityNameLower).append("') }}\">");
                                    menu.append("<i class=\"fas ").append(icon).append("\"></i> ").append(label);
                                    menu.append("</a></li>\n");
                                }
                            }

                            menu.append("                </ul>\n");
                            menu.append("            </li>");
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Em caso de erro, retornar vazio
            return "";
        }

        return menu.toString();
    }
}
