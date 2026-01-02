package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.MetaModel;
import br.com.gerador.metamodel.model.SessionContext;

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
APP_FALLBACK_LOCALE=pt_BR

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

    /**
     * Indicates if the model should use snake_case for attributes.
     *
     * Set to false to preserve the exact column names from the database.
     */
    public static $snakeAttributes = false;
}
""";
        } else {
            return """
<?php

namespace App\\Models;

use Illuminate\\Database\\Eloquent\\Model;

class BaseModel extends Model
{
    /**
     * Indicates if the model should use snake_case for attributes.
     *
     * Set to false to preserve the exact column names from the database.
     */
    public static $snakeAttributes = false;
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

    public String generateBootstrapApp(MetaModel metaModel) {
        // Verificar se há sessionContext configurado
        var metadata = metaModel.getMetadata();
        boolean hasSessionContext = metadata.hasSessionContext();

        String middlewareAliases;
        if (hasSessionContext) {
            middlewareAliases = """
            'auth' => \\Illuminate\\Auth\\Middleware\\Authenticate::class,
            'verified' => \\App\\Http\\Middleware\\EnsureEmailIsVerified::class,
            'session.context' => \\App\\Http\\Middleware\\EnsureSessionContextSelected::class,""";
        } else {
            middlewareAliases = """
            'auth' => \\Illuminate\\Auth\\Middleware\\Authenticate::class,
            'verified' => \\App\\Http\\Middleware\\EnsureEmailIsVerified::class,""";
        }

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
%s
        ]);

        // Os middlewares web (session, CSRF, etc) são carregados automaticamente pelo Laravel 12
    })
    ->withExceptions(function (Exceptions $exceptions) {
        //
    })->create();
""".formatted(middlewareAliases);
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
        // Verificar se há sessionContext configurado
        var metadata = metaModel.getMetadata();
        boolean hasSessionContext = metadata.hasSessionContext();

        // Gerar menu dinâmico a partir da configuração UI ou entidades
        StringBuilder menuItems = new StringBuilder();

        if (metaModel != null && metaModel.getModules() != null && !metaModel.getModules().isEmpty()) {
            // Gerar menu hierárquico a partir dos módulos do MetaModel
            menuItems.append(generateMenuFromModules(metaModel));
        } else if (projectConfig != null && hasMenuConfiguration()) {
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

        // Gerar HTML da barra de contexto se necessário
        String sessionContextBar = "";
        if (hasSessionContext) {
            java.util.List<SessionContext> sessionContextList = metadata.getSessionContext();

            StringBuilder contextItems = new StringBuilder();
            for (SessionContext ctx : sessionContextList) {
                String entity = ctx.getEntity();
                String field = ctx.getField();
                String displayField = ctx.getDisplayField();
                String entityLower = entity.toLowerCase();
                String fieldSnake = toSnakeCase(field);
                String icon = getIconForEntity(entity);

                contextItems.append("        <div class=\"session-context-item\">\n")
                           .append("            <i class=\"fas ").append(icon).append("\"></i>\n")
                           .append("            <span class=\"session-context-label\">").append(entity).append(":</span>\n")
                           .append("            <a href=\"{{ route('session.select') }}\" class=\"session-context-value\" title=\"Clique para alterar\">\n")
                           .append("                {{ session('").append(fieldSnake).append("_nome') }}\n")
                           .append("            </a>\n")
                           .append("        </div>\n");
            }

            // Gera condição dinâmica para exibir a barra
            StringBuilder sessionConditions = new StringBuilder();
            for (int i = 0; i < sessionContextList.size(); i++) {
                SessionContext ctx = sessionContextList.get(i);
                String fieldSnake = toSnakeCase(ctx.getField());
                if (i > 0) {
                    sessionConditions.append(" || ");
                }
                sessionConditions.append("session()->has('").append(fieldSnake).append("')");
            }

            sessionContextBar = String.format("""
    @if(%s)
    <div class="session-context-bar">
        <div class="d-flex align-items-center justify-content-between w-100">
            <div class="d-flex align-items-center gap-3">
%s            </div>
            <div class="d-flex align-items-center gap-3">
                <div class="session-context-item">
                    <i class="fas fa-user"></i>
                    <span class="session-context-label">Usuário:</span>
                    <span class="session-context-value">{{ Auth::user()->name }}</span>
                </div>
                <form action="{{ url('/logout') }}" method="POST" class="d-inline">
                    @csrf
                    <button type="submit" class="btn btn-sm btn-outline-light" title="Sair">
                        <i class="fas fa-sign-out-alt"></i> Sair
                    </button>
                </form>
            </div>
        </div>
    </div>
    @endif

    """, sessionConditions.toString(), contextItems.toString());
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

        /* Session Context Bar */
        .session-context-bar {
            background: linear-gradient(90deg, #667eea 0%%, #764ba2 100%%);
            padding: 0.8rem 2rem;
            color: white;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 1rem;
            margin-left: 250px;
        }

        .session-context-item {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-size: 0.95rem;
        }

        .session-context-item i {
            font-size: 1.1rem;
        }

        .session-context-label {
            font-weight: 500;
        }

        .session-context-value {
            font-weight: 600;
            font-size: 1rem;
            padding: 0.3rem 0.8rem;
            background: rgba(255,255,255,0.2);
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s;
            color: white;
            text-decoration: none;
            display: inline-block;
        }

        .session-context-value:hover {
            background: rgba(255,255,255,0.3);
            transform: translateY(-2px);
            color: white;
            text-decoration: none;
        }

        .session-context-bar .btn-light {
            background: white;
            color: #667eea;
            font-weight: 600;
            border: none;
            transition: all 0.3s;
        }

        .session-context-bar .btn-light:hover {
            background: #f8f9fa;
            transform: translateY(-2px);
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }

        /* Ajustar main-content quando há barra de contexto */
        body {
            display: flex;
            flex-direction: column;
        }

        .main-content {
            margin-left: 250px;
            flex: 1;
            padding: 2rem;
            min-height: 100vh;
        }
    </style>
    @stack('styles')
</head>
<body>
    @auth
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="sidebar-header">
            <h3><i class="fas %s"></i> {{ config('app.name') }}</h3>
        </div>

        <ul class="sidebar-menu">
            <li class="menu-item">
                <a href="{{ url('/dashboard') }}">
                    <i class="fas fa-home"></i> Dashboard
                </a>
            </li>

%s
        </ul>
    </div>
    @endauth

    <!-- Session Context Bar -->
%s
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
""".formatted(getAppIcon(), menuItems.toString(), sessionContextBar);
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

    public String generateAuthController(MetaModel metaModel) {
        var metadata = metaModel.getMetadata();
        boolean hasSessionContext = metadata.hasSessionContext();

        // Define para onde redirecionar após login
        String loginRedirect = hasSessionContext ? "'/session/select'" : "'/dashboard'";

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
            return redirect()->intended(%s);
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

        return redirect(%s);
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
""".formatted(loginRedirect, loginRedirect);
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

    public String generateWebRoutesWithAuthAndSession(MetaModel metaModel) {
        var metadata = metaModel.getMetadata();
        boolean hasSessionContext = metadata.hasSessionContext();

        if (!hasSessionContext) {
            return generateWebRoutesWithAuth();
        }

        return """
<?php

use Illuminate\\Support\\Facades\\Route;
use App\\Http\\Controllers\\AuthController;
use App\\Http\\Controllers\\SessionController;

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
    // Seleção de contexto de sessão
    Route::get('/session/select', [SessionController::class, 'select'])->name('session.select');
    Route::post('/session/store', [SessionController::class, 'store'])->name('session.store');
    Route::post('/session/update', [SessionController::class, 'update'])->name('session.update');

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

    /**
     * Gera HTML do menu a partir dos módulos do MetaModel.
     */
    private String generateMenuFromModules(MetaModel metaModel) {
        StringBuilder menu = new StringBuilder();

        try {
            java.util.List<br.com.gerador.metamodel.model.Module> modules = metaModel.getModules();

            // Ordenar módulos por order
            modules.sort((m1, m2) -> Integer.compare(m1.getOrder(), m2.getOrder()));

            for (br.com.gerador.metamodel.model.Module module : modules) {
                String moduleName = module.getName();
                String moduleIcon = convertMaterialIconToFontAwesome(module.getIcon());

                // Gerar ID único para o submenu
                String submenuId = module.getId();

                menu.append("\n            <li class=\"menu-item\">\n");
                menu.append("                <a href=\"#\" class=\"menu-toggle\" onclick=\"toggleSubmenu(event, '").append(submenuId).append("')\">\n");
                menu.append("                    <i class=\"fas ").append(moduleIcon).append("\"></i> ").append(moduleName).append("\n");
                menu.append("                    <i class=\"fas fa-chevron-right float-end\"></i>\n");
                menu.append("                </a>\n");
                menu.append("                <ul class=\"submenu\" id=\"").append(submenuId).append("\">\n");

                // Ordenar itens por order
                java.util.List<br.com.gerador.metamodel.model.MenuItem> items = module.getItems();
                if (items != null) {
                    items.sort((i1, i2) -> Integer.compare(i1.getOrder(), i2.getOrder()));

                    for (br.com.gerador.metamodel.model.MenuItem item : items) {
                        String itemName = item.getName();
                        String entityRef = item.getEntityRef();
                        String itemIcon = convertMaterialIconToFontAwesome(item.getIcon());

                        if (entityRef != null && !entityRef.isEmpty()) {
                            String entityUrl = toLowerCamelCase(entityRef);

                            menu.append("                    <li>\n");
                            menu.append("                        <a href=\"{{ url('/").append(entityUrl).append("') }}\">\n");
                            menu.append("                            <i class=\"fas ").append(itemIcon).append("\"></i> ").append(itemName).append("\n");
                            menu.append("                        </a>\n");
                            menu.append("                    </li>\n");
                        }
                    }
                }

                menu.append("                </ul>\n");
                menu.append("            </li>");
            }
        } catch (Exception e) {
            System.err.println("Erro ao gerar menu a partir dos módulos: " + e.getMessage());
            e.printStackTrace();
            return "";
        }

        return menu.toString();
    }

    /**
     * Converte ícones do Material Icons para Font Awesome equivalentes.
     * Se o ícone já estiver no formato Font Awesome (fa-*), retorna sem conversão.
     */
    private String convertMaterialIconToFontAwesome(String icon) {
        if (icon == null || icon.isEmpty()) {
            return "fa-circle";
        }

        // Se já é um ícone Font Awesome, retorna direto
        if (icon.startsWith("fa-")) {
            return icon;
        }

        // Senão, faz a conversão de Material Icons para Font Awesome
        return switch (icon.toLowerCase()) {
            case "business_center" -> "fa-briefcase";
            case "person" -> "fa-user";
            case "apartment" -> "fa-building";
            case "corporate_fare" -> "fa-building";
            case "account_balance" -> "fa-university";
            case "payments" -> "fa-credit-card";
            case "receipt" -> "fa-receipt";
            case "description" -> "fa-file-alt";
            case "account_balance_wallet" -> "fa-wallet";
            case "paid" -> "fa-money-bill-wave";
            case "trending_up" -> "fa-chart-line";
            case "payment" -> "fa-money-check";
            case "settings" -> "fa-cog";
            case "location_city" -> "fa-city";
            case "people" -> "fa-users";
            case "school" -> "fa-school";
            case "medical_services" -> "fa-briefcase-medical";
            case "business" -> "fa-building";
            case "manage_accounts" -> "fa-user-cog";
            case "calendar_today" -> "fa-calendar-check";
            case "database" -> "fa-database";
            case "storage" -> "fa-database";
            case "map" -> "fa-map-marked-alt";
            case "contact_page" -> "fa-address-book";
            case "badge" -> "fa-id-card";
            case "favorite" -> "fa-ring";
            case "local_hospital" -> "fa-stethoscope";
            case "biotech" -> "fa-microscope";
            case "science" -> "fa-flask";
            case "medical_information" -> "fa-heartbeat";
            case "health_and_safety" -> "fa-shield-alt";
            case "medication" -> "fa-pills";
            // Ícones adicionais do ICEP
            case "folder" -> "fa-folder";
            case "folder_open" -> "fa-folder-open";
            case "event_note" -> "fa-calendar-alt";
            case "event_available" -> "fa-calendar-check";
            case "event" -> "fa-calendar";
            case "public" -> "fa-globe";
            case "groups" -> "fa-users";
            case "category" -> "fa-tag";
            case "list_alt" -> "fa-list-alt";
            case "list" -> "fa-list";
            case "auto_stories" -> "fa-book-open";
            case "lightbulb" -> "fa-lightbulb";
            case "account_tree" -> "fa-sitemap";
            case "speed" -> "fa-tachometer-alt";
            case "grading" -> "fa-clipboard-check";
            case "fact_check" -> "fa-check-square";
            case "assessment" -> "fa-chart-bar";
            case "chat" -> "fa-comments";
            case "schedule" -> "fa-clock";
            // Ícones adicionais do Xandel
            case "tune" -> "fa-sliders-h";
            case "percent" -> "fa-percentage";
            case "compare_arrows" -> "fa-exchange-alt";
            case "request_quote" -> "fa-file-invoice-dollar";
            case "settings_applications" -> "fa-cogs";
            case "star_rate" -> "fa-star";
            case "email" -> "fa-envelope";
            case "receipt_long" -> "fa-receipt";
            case "store" -> "fa-store";
            case "contact_phone" -> "fa-phone";
            case "admin_panel_settings" -> "fa-user-shield";
            case "domain" -> "fa-building";
            default -> "fa-circle";
        };
    }

    /**
     * Obtém o ícone da aplicação configurado no JSON.
     * Se não estiver configurado, retorna um ícone padrão.
     */
    private String getAppIcon() {
        if (projectConfig != null) {
            String icon = projectConfig.getString("project.appIcon", null);
            if (icon != null && !icon.isEmpty()) {
                return icon;
            }
        }
        // Ícone padrão caso não esteja configurado
        return "fa-desktop";
    }

    public String generatePaginationTranslation() {
        return """
<?php

return [

    /*
    |--------------------------------------------------------------------------
    | Pagination Language Lines
    |--------------------------------------------------------------------------
    |
    | The following language lines are used by the paginator library to build
    | the simple pagination links. You are free to change them to anything
    | you want to customize your views to better match your application.
    |
    */

    'previous' => '&laquo; Anterior',
    'next' => 'Próximo &raquo;',

];
""";
    }

    public String generateValidationTranslation() {
        return """
<?php

return [

    /*
    |--------------------------------------------------------------------------
    | Validation Language Lines
    |--------------------------------------------------------------------------
    |
    | The following language lines contain the default error messages used by
    | the validator class. Some of these rules have multiple versions such
    | as the size rules. Feel free to tweak each of these messages here.
    |
    */

    'accepted' => 'O campo :attribute deve ser aceito.',
    'accepted_if' => 'O campo :attribute deve ser aceito quando :other for :value.',
    'active_url' => 'O campo :attribute não é uma URL válida.',
    'after' => 'O campo :attribute deve ser uma data posterior a :date.',
    'after_or_equal' => 'O campo :attribute deve ser uma data posterior ou igual a :date.',
    'alpha' => 'O campo :attribute só pode conter letras.',
    'alpha_dash' => 'O campo :attribute só pode conter letras, números e traços.',
    'alpha_num' => 'O campo :attribute só pode conter letras e números.',
    'array' => 'O campo :attribute deve ser uma matriz.',
    'before' => 'O campo :attribute deve ser uma data anterior a :date.',
    'before_or_equal' => 'O campo :attribute deve ser uma data anterior ou igual a :date.',
    'between' => [
        'numeric' => 'O campo :attribute deve estar entre :min e :max.',
        'file' => 'O campo :attribute deve estar entre :min e :max kilobytes.',
        'string' => 'O campo :attribute deve estar entre :min e :max caracteres.',
        'array' => 'O campo :attribute deve ter entre :min e :max itens.',
    ],
    'boolean' => 'O campo :attribute deve ser verdadeiro ou falso.',
    'confirmed' => 'A confirmação de :attribute não confere.',
    'current_password' => 'A senha está incorreta.',
    'date' => 'O campo :attribute não é uma data válida.',
    'date_equals' => 'O campo :attribute deve ser uma data igual a :date.',
    'date_format' => 'O campo :attribute não corresponde ao formato :format.',
    'declined' => 'O campo :attribute deve ser recusado.',
    'declined_if' => 'O campo :attribute deve ser recusado quando :other for :value.',
    'different' => 'Os campos :attribute e :other devem ser diferentes.',
    'digits' => 'O campo :attribute deve ter :digits dígitos.',
    'digits_between' => 'O campo :attribute deve ter entre :min e :max dígitos.',
    'dimensions' => 'O campo :attribute tem dimensões de imagem inválidas.',
    'distinct' => 'O campo :attribute possui um valor duplicado.',
    'doesnt_end_with' => 'O campo :attribute não pode terminar com um dos seguintes: :values.',
    'doesnt_start_with' => 'O campo :attribute não pode começar com um dos seguintes: :values.',
    'email' => 'O campo :attribute deve ser um endereço de e-mail válido.',
    'ends_with' => 'O campo :attribute deve terminar com um dos seguintes: :values.',
    'enum' => 'O valor selecionado para :attribute é inválido.',
    'exists' => 'O valor selecionado para :attribute é inválido.',
    'file' => 'O campo :attribute deve ser um arquivo.',
    'filled' => 'O campo :attribute deve ter um valor.',
    'gt' => [
        'numeric' => 'O campo :attribute deve ser maior que :value.',
        'file' => 'O campo :attribute deve ser maior que :value kilobytes.',
        'string' => 'O campo :attribute deve ser maior que :value caracteres.',
        'array' => 'O campo :attribute deve ter mais de :value itens.',
    ],
    'gte' => [
        'numeric' => 'O campo :attribute deve ser maior ou igual a :value.',
        'file' => 'O campo :attribute deve ser maior ou igual a :value kilobytes.',
        'string' => 'O campo :attribute deve ser maior ou igual a :value caracteres.',
        'array' => 'O campo :attribute deve ter :value itens ou mais.',
    ],
    'image' => 'O campo :attribute deve ser uma imagem.',
    'in' => 'O valor selecionado para :attribute é inválido.',
    'in_array' => 'O campo :attribute não existe em :other.',
    'integer' => 'O campo :attribute deve ser um número inteiro.',
    'ip' => 'O campo :attribute deve ser um endereço de IP válido.',
    'ipv4' => 'O campo :attribute deve ser um endereço IPv4 válido.',
    'ipv6' => 'O campo :attribute deve ser um endereço IPv6 válido.',
    'json' => 'O campo :attribute deve ser uma string JSON válida.',
    'lt' => [
        'numeric' => 'O campo :attribute deve ser menor que :value.',
        'file' => 'O campo :attribute deve ser menor que :value kilobytes.',
        'string' => 'O campo :attribute deve ser menor que :value caracteres.',
        'array' => 'O campo :attribute deve ter menos de :value itens.',
    ],
    'lte' => [
        'numeric' => 'O campo :attribute deve ser menor ou igual a :value.',
        'file' => 'O campo :attribute deve ser menor ou igual a :value kilobytes.',
        'string' => 'O campo :attribute deve ser menor ou igual a :value caracteres.',
        'array' => 'O campo :attribute não deve ter mais que :value itens.',
    ],
    'mac_address' => 'O campo :attribute deve ser um endereço MAC válido.',
    'max' => [
        'numeric' => 'O campo :attribute não pode ser superior a :max.',
        'file' => 'O campo :attribute não pode ser superior a :max kilobytes.',
        'string' => 'O campo :attribute não pode ser superior a :max caracteres.',
        'array' => 'O campo :attribute não pode ter mais do que :max itens.',
    ],
    'max_digits' => 'O campo :attribute não pode ter mais que :max dígitos.',
    'mimes' => 'O campo :attribute deve ser um arquivo do tipo: :values.',
    'mimetypes' => 'O campo :attribute deve ser um arquivo do tipo: :values.',
    'min' => [
        'numeric' => 'O campo :attribute deve ser pelo menos :min.',
        'file' => 'O campo :attribute deve ter pelo menos :min kilobytes.',
        'string' => 'O campo :attribute deve ter pelo menos :min caracteres.',
        'array' => 'O campo :attribute deve ter pelo menos :min itens.',
    ],
    'min_digits' => 'O campo :attribute deve ter pelo menos :min dígitos.',
    'multiple_of' => 'O campo :attribute deve ser um múltiplo de :value.',
    'not_in' => 'O valor selecionado para :attribute é inválido.',
    'not_regex' => 'O formato do campo :attribute é inválido.',
    'numeric' => 'O campo :attribute deve ser um número.',
    'password' => [
        'letters' => 'O campo :attribute deve conter pelo menos uma letra.',
        'mixed' => 'O campo :attribute deve conter pelo menos uma letra maiúscula e uma minúscula.',
        'numbers' => 'O campo :attribute deve conter pelo menos um número.',
        'symbols' => 'O campo :attribute deve conter pelo menos um símbolo.',
        'uncompromised' => 'O valor fornecido para :attribute apareceu em um vazamento de dados. Por favor, escolha um valor diferente.',
    ],
    'present' => 'O campo :attribute deve estar presente.',
    'prohibited' => 'O campo :attribute é proibido.',
    'prohibited_if' => 'O campo :attribute é proibido quando :other for :value.',
    'prohibited_unless' => 'O campo :attribute é proibido a menos que :other esteja em :values.',
    'prohibits' => 'O campo :attribute proíbe :other de estar presente.',
    'regex' => 'O formato do campo :attribute é inválido.',
    'required' => 'O campo :attribute é obrigatório.',
    'required_array_keys' => 'O campo :attribute deve conter entradas para: :values.',
    'required_if' => 'O campo :attribute é obrigatório quando :other for :value.',
    'required_if_accepted' => 'O campo :attribute é obrigatório quando :other for aceito.',
    'required_unless' => 'O campo :attribute é obrigatório a menos que :other esteja em :values.',
    'required_with' => 'O campo :attribute é obrigatório quando :values está presente.',
    'required_with_all' => 'O campo :attribute é obrigatório quando :values estão presentes.',
    'required_without' => 'O campo :attribute é obrigatório quando :values não está presente.',
    'required_without_all' => 'O campo :attribute é obrigatório quando nenhum dos :values estão presentes.',
    'same' => 'Os campos :attribute e :other devem corresponder.',
    'size' => [
        'numeric' => 'O campo :attribute deve ser :size.',
        'file' => 'O campo :attribute deve ser :size kilobytes.',
        'string' => 'O campo :attribute deve ser :size caracteres.',
        'array' => 'O campo :attribute deve conter :size itens.',
    ],
    'starts_with' => 'O campo :attribute deve começar com um dos seguintes valores: :values.',
    'string' => 'O campo :attribute deve ser uma string.',
    'timezone' => 'O campo :attribute deve ser um fuso horário válido.',
    'unique' => 'O valor do campo :attribute já está sendo utilizado.',
    'uploaded' => 'Ocorreu uma falha no upload do campo :attribute.',
    'url' => 'O campo :attribute deve ser uma URL válida.',
    'uuid' => 'O campo :attribute deve ser um UUID válido.',

    /*
    |--------------------------------------------------------------------------
    | Custom Validation Language Lines
    |--------------------------------------------------------------------------
    |
    | Here you may specify custom validation messages for attributes using the
    | convention "attribute.rule" to name the lines. This makes it quick to
    | specify a specific custom language line for a given attribute rule.
    |
    */

    'custom' => [
        'attribute-name' => [
            'rule-name' => 'custom-message',
        ],
    ],

    /*
    |--------------------------------------------------------------------------
    | Custom Validation Attributes
    |--------------------------------------------------------------------------
    |
    | The following language lines are used to swap our attribute placeholder
    | with something more reader friendly such as "E-Mail Address" instead
    | of "email". This simply helps us make our message more expressive.
    |
    */

    'attributes' => [],

];
""";
    }

    public String generateBootstrap5PaginationView() {
        return """
@if ($paginator->hasPages())
    <nav role="navigation" aria-label="{{ __('Pagination Navigation') }}" class="w-100">
        <div class="d-flex flex-column align-items-center gap-2">
            {{-- Informação de resultados --}}
            <div class="text-muted small">
                Mostrando
                <span class="fw-semibold">{{ $paginator->firstItem() }}</span>
                a
                <span class="fw-semibold">{{ $paginator->lastItem() }}</span>
                de
                <span class="fw-semibold">{{ $paginator->total() }}</span>
                resultados
            </div>

            {{-- Links de paginação --}}
            <ul class="pagination mb-0">
                {{-- Previous Page Link --}}
                @if ($paginator->onFirstPage())
                    <li class="page-item disabled" aria-disabled="true" aria-label="@lang('pagination.previous')">
                        <span class="page-link" aria-hidden="true">&lsaquo;</span>
                    </li>
                @else
                    <li class="page-item">
                        <a class="page-link" href="{{ $paginator->previousPageUrl() }}" rel="prev" aria-label="@lang('pagination.previous')">&lsaquo;</a>
                    </li>
                @endif

                {{-- Pagination Elements --}}
                @foreach ($elements as $element)
                    {{-- "Three Dots" Separator --}}
                    @if (is_string($element))
                        <li class="page-item disabled" aria-disabled="true"><span class="page-link">{{ $element }}</span></li>
                    @endif

                    {{-- Array Of Links --}}
                    @if (is_array($element))
                        @foreach ($element as $page => $url)
                            @if ($page == $paginator->currentPage())
                                <li class="page-item active" aria-current="page"><span class="page-link">{{ $page }}</span></li>
                            @else
                                <li class="page-item"><a class="page-link" href="{{ $url }}">{{ $page }}</a></li>
                            @endif
                        @endforeach
                    @endif
                @endforeach

                {{-- Next Page Link --}}
                @if ($paginator->hasMorePages())
                    <li class="page-item">
                        <a class="page-link" href="{{ $paginator->nextPageUrl() }}" rel="next" aria-label="@lang('pagination.next')">&rsaquo;</a>
                    </li>
                @else
                    <li class="page-item disabled" aria-disabled="true" aria-label="@lang('pagination.next')">
                        <span class="page-link" aria-hidden="true">&rsaquo;</span>
                    </li>
                @endif
            </ul>
        </div>
    </nav>
@endif
""";
    }

    public String generateSessionController(MetaModel metaModel) {
        // Lê a configuração de sessionContext do metadata
        var metadata = metaModel.getMetadata();
        java.util.List<SessionContext> sessionContextList = metadata.getSessionContext();

        if (sessionContextList == null || sessionContextList.isEmpty()) {
            return ""; // Não gera se não houver sessionContext
        }

        StringBuilder uses = new StringBuilder();
        StringBuilder validationRules = new StringBuilder();
        StringBuilder sessionAssignments = new StringBuilder();
        StringBuilder sessionNames = new StringBuilder();
        StringBuilder selectFetches = new StringBuilder();
        StringBuilder selectCompact = new StringBuilder();
        StringBuilder updateMethods = new StringBuilder();

        for (SessionContext ctx : sessionContextList) {
            String entity = ctx.getEntity();
            String field = ctx.getField();
            String displayField = ctx.getDisplayField();

            String entityLower = Character.toLowerCase(entity.charAt(0)) + entity.substring(1);
            String entityPlural = entityLower + "s";

            // Buscar o columnName do campo no metamodel
            String fieldColumnName = getColumnNameFromMetaModel(metaModel, entity, field);
            String displayFieldColumnName = getColumnNameFromMetaModel(metaModel, entity, displayField);

            // Buscar o nome da coluna PK da entidade referenciada
            String pkColumnName = getPrimaryKeyColumnName(metaModel, entity);

            // Nome da tabela em snake_case
            String tableName = toSnakeCase(entity);

            uses.append("use App\\Models\\").append(entity).append(";\n");

            validationRules.append("            '").append(field).append("' => 'required|exists:")
                          .append(tableName).append(",").append(pkColumnName).append("',\n");

            sessionAssignments.append("            '").append(toSnakeCase(field)).append("' => $request->").append(field).append(",\n");

            selectFetches.append("        $").append(entityPlural).append(" = ").append(entity)
                        .append("::orderBy('").append(displayFieldColumnName).append("')->get();\n");

            selectCompact.append(", '").append(entityPlural).append("'");

            sessionNames.append("\n        // Busca nome de ").append(entity).append("\n")
                       .append("        $").append(entityLower).append(" = ").append(entity)
                       .append("::find($request->").append(field).append(");\n")
                       .append("        session([\n")
                       .append("            '").append(toSnakeCase(field)).append("_nome' => $").append(entityLower)
                       .append("->").append(displayFieldColumnName).append(",\n")
                       .append("        ]);\n");

            updateMethods.append("\n        if ($request->has('").append(field).append("')) {\n")
                       .append("            $").append(entityLower).append(" = ").append(entity)
                       .append("::findOrFail($request->").append(field).append(");\n")
                       .append("            session([\n")
                       .append("                '").append(toSnakeCase(field)).append("' => $").append(entityLower).append("->").append(fieldColumnName).append(",\n")
                       .append("                '").append(toSnakeCase(field)).append("_nome' => $").append(entityLower)
                       .append("->").append(displayFieldColumnName).append(",\n")
                       .append("            ]);\n")
                       .append("        }\n");
        }

        return """
<?php

namespace App\\Http\\Controllers;

%s
use Illuminate\\Http\\Request;

class SessionController extends Controller
{
    /**
     * Exibe a tela de seleção de contexto.
     */
    public function select()
    {
%s
        return view('session.select', compact(%s));
    }

    /**
     * Armazena o contexto selecionado na sessão.
     */
    public function store(Request $request)
    {
        $request->validate([
%s        ]);

        session([
%s        ]);
%s
        return redirect()->route('dashboard')->with('success', 'Contexto de sessão definido com sucesso!');
    }

    /**
     * Atualiza apenas um item do contexto.
     */
    public function update(Request $request)
    {
%s
        return back()->with('success', 'Contexto atualizado com sucesso!');
    }
}
""".formatted(
    uses.toString(),
    selectFetches.toString(),
    selectCompact.toString().substring(2), // Remove primeira vírgula
    validationRules.toString(),
    sessionAssignments.toString(),
    sessionNames.toString(),
    updateMethods.toString()
);
    }

    public String generateSessionMiddleware(MetaModel metaModel) {
        var metadata = metaModel.getMetadata();
        java.util.List<SessionContext> sessionContextList = metadata.getSessionContext();

        if (sessionContextList == null || sessionContextList.isEmpty()) {
            return "";
        }

        StringBuilder requiredContexts = new StringBuilder();
        for (SessionContext ctx : sessionContextList) {
            String field = ctx.getField();
            // Converter para snake_case
            String fieldSnake = toSnakeCase(field);
            requiredContexts.append("'").append(fieldSnake).append("', ");
        }

        // Remove última vírgula
        if (requiredContexts.length() > 0) {
            requiredContexts.setLength(requiredContexts.length() - 2);
        }

        return """
<?php

namespace App\\Http\\Middleware;

use Closure;
use Illuminate\\Http\\Request;
use Symfony\\Component\\HttpFoundation\\Response;

class EnsureSessionContextSelected
{
    /**
     * Handle an incoming request.
     */
    public function handle(Request $request, Closure $next): Response
    {
        // Define os itens de contexto requeridos
        $requiredContexts = [%s];

        // Rotas que não precisam de contexto
        $excludedRoutes = ['login', 'logout', 'register', 'session.select', 'session.store'];

        // Verifica se a rota atual está excluída
        if ($request->routeIs($excludedRoutes)) {
            return $next($request);
        }

        // Verifica se todos os contextos necessários estão na sessão
        foreach ($requiredContexts as $context) {
            if (!session()->has($context)) {
                return redirect()->route('session.select');
            }
        }

        return $next($request);
    }
}
""".formatted(requiredContexts.toString());
    }

    public String generateSessionSelectView(MetaModel metaModel) {
        var metadata = metaModel.getMetadata();
        java.util.List<SessionContext> sessionContextList = metadata.getSessionContext();

        if (sessionContextList == null || sessionContextList.isEmpty()) {
            return "";
        }

        StringBuilder selectionSections = new StringBuilder();

        for (SessionContext ctx : sessionContextList) {
            String entity = ctx.getEntity();
            String field = ctx.getField();
            String displayField = ctx.getDisplayField();
            String label = ctx.getLabel();
            String inputType = ctx.getInputType() != null ? ctx.getInputType() : "radio";

            String entityLower = Character.toLowerCase(entity.charAt(0)) + entity.substring(1);
            String entityPlural = entityLower + "s";
            String icon = getIconForEntity(entity);

            // Buscar columnNames do metamodel
            String fieldColumnName = getColumnNameFromMetaModel(metaModel, entity, field);
            String displayFieldColumnName = getColumnNameFromMetaModel(metaModel, entity, displayField);

            selectionSections.append("""
                        {{-- Seleção de %s --}}
                        <div class="mb-4">
                            <label class="form-label fw-bold">
                                <i class="fas %s text-primary"></i> %s *
                            </label>
                            <div class="row">
                                @foreach($%s as $%s)
                                    <div class="col-md-6 mb-2">
                                        <div class="form-check">
                                            <input
                                                class="form-check-input"
                                                type="%s"
                                                name="%s"
                                                id="%s_{{ $%s->%s }}"
                                                value="{{ $%s->%s }}"
                                                {{ old('%s', session('%s')) == $%s->%s ? 'checked' : '' }}
                                                required
                                            >
                                            <label class="form-check-label" for="%s_{{ $%s->%s }}">
                                                {{ $%s->%s }}
                                            </label>
                                        </div>
                                    </div>
                                @endforeach
                            </div>
                            @error('%s')
                                <div class="text-danger small mt-1">{{ $message }}</div>
                            @enderror
                        </div>

""".formatted(
                entity, icon, label,
                entityPlural, entityLower,
                inputType, field,
                entityLower, entityLower, fieldColumnName,
                entityLower, fieldColumnName,
                field, toSnakeCase(field), entityLower, fieldColumnName,
                entityLower, entityLower, fieldColumnName,
                entityLower, displayFieldColumnName,
                field
            ));
        }

        return """
@extends('layouts.app')

@section('title', 'Selecionar Contexto')

@section('content')
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0"><i class="fas fa-filter"></i> Selecione o Contexto de Trabalho</h4>
                </div>
                <div class="card-body">
                    <p class="text-muted mb-4">
                        Para continuar, selecione o contexto que deseja trabalhar.
                        Esses valores serão usados para filtrar os dados do sistema.
                    </p>

                    <form action="{{ route('session.store') }}" method="POST">
                        @csrf

%s
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-check"></i> Confirmar Seleção
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
""".formatted(selectionSections.toString());
    }

    private String getIconForEntity(String entity) {
        return switch (entity.toLowerCase()) {
            case "avaliacao" -> "fa-clipboard-check";
            case "municipio" -> "fa-city";
            case "territorio" -> "fa-map";
            case "escola" -> "fa-school";
            case "turma" -> "fa-users";
            case "empresa" -> "fa-building";
            default -> "fa-circle";
        };
    }

    /**
     * Busca o columnName do campo no metamodel.
     * Se não encontrar ou se columnName não estiver definido, retorna snake_case do fieldName.
     */
    private String getColumnNameFromMetaModel(MetaModel metaModel, String entityName, String fieldName) {
        if (metaModel == null || metaModel.getEntities() == null) {
            return toSnakeCase(fieldName);
        }

        // Busca a entidade
        for (br.com.gerador.metamodel.model.Entity entity : metaModel.getEntities()) {
            if (entity.getName().equals(entityName)) {
                // Busca o campo
                if (entity.getFields() != null) {
                    for (br.com.gerador.metamodel.model.Field field : entity.getFields()) {
                        if (field.getName().equals(fieldName)) {
                            // Retorna columnName se existir, senão snake_case
                            if (field.getColumnName() != null && !field.getColumnName().isEmpty()) {
                                return field.getColumnName();
                            }
                            return toSnakeCase(fieldName);
                        }
                    }
                }
            }
        }

        // Se não encontrou, retorna snake_case
        return toSnakeCase(fieldName);
    }

    private String getPrimaryKeyColumnName(MetaModel metaModel, String entityName) {
        if (metaModel == null || metaModel.getEntities() == null) {
            return "id"; // default
        }

        // Busca a entidade
        for (br.com.gerador.metamodel.model.Entity entity : metaModel.getEntities()) {
            if (entity.getName().equals(entityName)) {
                // Busca o campo com primaryKey = true
                if (entity.getFields() != null) {
                    for (br.com.gerador.metamodel.model.Field field : entity.getFields()) {
                        if (field.isPrimaryKey()) {
                            // Retorna columnName da PK
                            if (field.getColumnName() != null && !field.getColumnName().isEmpty()) {
                                return field.getColumnName();
                            }
                            return toSnakeCase(field.getName());
                        }
                    }
                }
            }
        }

        // Se não encontrou, retorna 'id' como padrão
        return "id";
    }

    private String toSnakeCase(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) return camelCase;

        StringBuilder result = new StringBuilder();
        result.append(Character.toLowerCase(camelCase.charAt(0)));

        for (int i = 1; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append('_');
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}
