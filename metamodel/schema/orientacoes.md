# 📘 Orientações para Criação de Projetos - MetaModel Framework

> **Versão:** 1.0.0
> **Última Atualização:** 2025-12-29
> **Autor:** Equipe Gerador MetaModel

---

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Estrutura de Arquivos](#estrutura-de-arquivos)
- [Metamodelo Principal](#metamodelo-principal)
- [Arquivos de Configuração](#arquivos-de-configuração)
- [Carga Inicial de Dados](#carga-inicial-de-dados)
- [Como Gerar o Projeto](#como-gerar-o-projeto)
- [Validação e Troubleshooting](#validação-e-troubleshooting)
- [Exemplos Completos](#exemplos-completos)

---

## 🎯 Visão Geral

O **MetaModel Framework** permite gerar aplicações completas (backend + frontend) a partir de uma definição declarativa em JSON. O framework segue o princípio de **Separação de Responsabilidades**:

- **Metamodelo (`*-model.json`)**: Define **O QUE** construir (domínio, entidades, regras)
- **Configuração (`config*.json`)**: Define **COMO** construir (tecnologia, infraestrutura)
- **Carga Inicial (`carga-inicial/*.csv`)**: Define os **DADOS** iniciais

### Tecnologias Suportadas

| Framework | Backend | Frontend | Banco de Dados |
|-----------|---------|----------|----------------|
| **Spring Boot + Angular** | Java 21, Spring Boot 3.2+ | Angular 17+ | SQL Server, MySQL, PostgreSQL, H2 |
| **Laravel** | PHP 8.2+, Laravel 12+ | Blade + Livewire | MySQL, PostgreSQL, SQLite |

---

## 📂 Estrutura de Arquivos

### Estrutura Mínima

```
metamodel/data/
└── meu-projeto/                      ← Nome do projeto (lowercase, sem espaços)
    ├── meu-projeto-model.json        ← OBRIGATÓRIO: Metamodelo
    └── config.json                   ← OPCIONAL: Config Spring Boot OU
        config-laravel.json           ← OPCIONAL: Config Laravel
```

### Estrutura Completa

```
metamodel/data/
└── meu-projeto/
    ├── meu-projeto-model.json        ← Metamodelo principal
    ├── config.json                   ← Configuração Spring Boot + Angular
    ├── config-laravel.json           ← Configuração Laravel + Blade
    └── carga-inicial/                ← Dados iniciais (OPCIONAL)
        ├── categoria.csv
        ├── cliente.csv
        ├── produto.csv
        └── ...
```

### Por Que 3 Arquivos?

1. **`*-model.json`** = Modelo de domínio **independente de tecnologia**
2. **`config.json`** = Como gerar com **Java/Spring/Angular**
3. **`config-laravel.json`** = Como gerar com **PHP/Laravel/Blade**

**Vantagens:**
- ✅ Mesmo modelo para **múltiplas tecnologias**
- ✅ Adicionar novos stacks sem alterar o modelo
- ✅ Versionamento independente

---

## 📄 Metamodelo Principal

### Arquivo: `<projeto>-model.json`

Este é o arquivo **mais importante**. Define toda a estrutura de dados, módulos, regras de negócio.

### Estrutura do JSON

```json
{
  "metadata": { ... },      // Informações do projeto
  "modules": [ ... ],       // Módulos/Menus da aplicação
  "enums": [ ... ],         // Enumerações (opcional)
  "entities": [ ... ]       // Entidades/Tabelas
}
```

### Campos Obrigatórios (conforme [metamodel-schema.json](metamodel-schema.json))

| Campo | Tipo | Obrigatório | Descrição |
|-------|------|-------------|-----------|
| `metadata` | Object | ✅ Sim | Informações do projeto |
| `metadata.name` | String | ✅ Sim | Nome do projeto |
| `metadata.version` | String | ✅ Sim | Versão do modelo |
| `modules` | Array | ✅ Sim | Módulos/menus da aplicação |
| `entities` | Array | ✅ Sim | Entidades do sistema |
| `enums` | Array | ❌ Não | Enumerações |

### Template Mínimo

```json
{
  "metadata": {
    "name": "MeuProjeto",
    "version": "1.0.0",
    "description": "Descrição do sistema",
    "author": "Seu Nome",
    "createdAt": "2025-12-29T00:00:00Z",
    "updatedAt": "2025-12-29T00:00:00Z",
    "database": {
      "type": "SQLSERVER",
      "schema": "dbo"
    }
  },
  "modules": [
    {
      "id": "cadastro",
      "name": "Cadastros",
      "description": "Cadastros básicos do sistema",
      "icon": "folder",
      "order": 1,
      "items": [
        {
          "id": "menu_cliente",
          "name": "Clientes",
          "entityRef": "Cliente",
          "icon": "person",
          "order": 1
        }
      ]
    }
  ],
  "enums": [],
  "entities": [
    {
      "id": "cliente",
      "name": "Cliente",
      "displayName": "Clientes",
      "tableName": "Cliente",
      "description": "Cadastro de clientes",
      "schema": "dbo",
      "type": "MAIN",
      "fields": [
        {
          "id": "cliente_id",
          "name": "id",
          "columnName": "id",
          "label": "Código",
          "dataType": "INTEGER",
          "databaseType": "int",
          "primaryKey": true,
          "autoIncrement": true,
          "required": true,
          "ui": {
            "component": "NUMBER",
            "grid": { "visible": true, "order": 1, "width": 80 },
            "form": { "visible": true, "order": 1 }
          }
        },
        {
          "id": "cliente_nome",
          "name": "nome",
          "columnName": "nome",
          "label": "Nome",
          "dataType": "STRING",
          "databaseType": "nvarchar",
          "length": 100,
          "required": true,
          "ui": {
            "component": "TEXT",
            "grid": { "visible": true, "order": 2, "sortable": true },
            "form": { "visible": true, "order": 2 },
            "search": { "enabled": true, "operator": "CONTAINS" }
          }
        }
      ]
    }
  ]
}
```

### Tipos de Entidade

| Tipo | Descrição | Exemplo |
|------|-----------|---------|
| `MAIN` | Entidade principal do domínio | Cliente, Produto, Pedido |
| `LOOKUP` | Tabela de domínio/lookup | Estado Civil, Categoria, Status |
| `JUNCTION` | Tabela de relacionamento N:N | PedidoItem, AlunoTurma |
| `CHILD` | Entidade dependente | Endereço (de Cliente), ItemPedido |
| `CONFIG` | Configuração do sistema | Parâmetros, Configurações |

### Tipos de Dados

| DataType | DatabaseType (SQL Server) | DatabaseType (MySQL) | Descrição |
|----------|---------------------------|----------------------|-----------|
| `STRING` | `nvarchar`, `char`, `varchar` | `varchar`, `char` | Texto |
| `INTEGER` | `int`, `smallint`, `tinyint` | `int`, `smallint` | Número inteiro |
| `LONG` | `bigint` | `bigint` | Número inteiro longo |
| `DECIMAL` | `decimal`, `numeric`, `money` | `decimal`, `numeric` | Decimal |
| `BOOLEAN` | `bit` | `tinyint(1)`, `boolean` | Verdadeiro/Falso |
| `DATE` | `date` | `date` | Data |
| `DATETIME` | `datetime`, `datetime2` | `datetime`, `timestamp` | Data e Hora |
| `TIME` | `time` | `time` | Hora |
| `TEXT` | `nvarchar(max)`, `text` | `text`, `longtext` | Texto longo |
| `BINARY` | `varbinary`, `image` | `blob`, `longblob` | Binário |
| `MONEY` | `money` | `decimal(15,2)` | Monetário |

### Componentes de UI

| Component | Descrição | Uso |
|-----------|-----------|-----|
| `TEXT` | Campo de texto simples | Nome, Título |
| `TEXTAREA` | Campo de texto multilinha | Descrição, Observações |
| `NUMBER` | Campo numérico | Quantidade, Código |
| `DECIMAL` | Campo decimal | Preço, Valor |
| `DATE` | Seletor de data | Data Nascimento |
| `DATETIME` | Seletor de data e hora | Data/Hora Cadastro |
| `CHECKBOX` | Caixa de seleção | Ativo, Habilitado |
| `SELECT` | Lista suspensa | Status, Tipo |
| `AUTOCOMPLETE` | Busca com sugestões | Cliente, Produto (FK) |
| `RADIO` | Botões de opção | Sexo, Tipo |
| `PASSWORD` | Campo de senha | Senha |
| `EMAIL` | Campo de e-mail | E-mail |
| `PHONE` | Campo de telefone | Telefone |
| `CPF` | Campo de CPF (BR) | CPF |
| `CNPJ` | Campo de CNPJ (BR) | CNPJ |
| `CEP` | Campo de CEP (BR) | CEP |
| `HIDDEN` | Campo oculto | IDs, campos técnicos |

### Relacionamentos (Foreign Keys)

```json
{
  "id": "pedido_cliente_id",
  "name": "clienteId",
  "columnName": "cliente_id",
  "label": "Cliente",
  "dataType": "INTEGER",
  "databaseType": "int",
  "required": true,
  "reference": {
    "entity": "Cliente",
    "field": "id",
    "onDelete": "CASCADE",
    "onUpdate": "NO_ACTION"
  },
  "ui": {
    "component": "AUTOCOMPLETE",
    "grid": { "visible": true, "order": 2, "sortable": true },
    "form": { "visible": true, "order": 2 },
    "search": { "enabled": true, "operator": "EQUALS" }
  }
}
```

**Ações de Integridade Referencial:**
- `CASCADE`: Propaga a operação
- `SET_NULL`: Define como NULL
- `RESTRICT`: Impede a operação
- `NO_ACTION`: Nenhuma ação (padrão)

### Enumerações

```json
"enums": [
  {
    "id": "enum_status",
    "name": "Status",
    "description": "Status do registro",
    "values": [
      { "code": "ATIVO", "label": "Ativo", "description": "Registro ativo" },
      { "code": "INATIVO", "label": "Inativo", "description": "Registro inativo" },
      { "code": "BLOQUEADO", "label": "Bloqueado", "description": "Registro bloqueado" }
    ]
  }
]
```

**Uso no campo:**

```json
{
  "id": "cliente_status",
  "name": "status",
  "columnName": "status",
  "label": "Status",
  "dataType": "STRING",
  "databaseType": "varchar",
  "length": 20,
  "required": true,
  "enumRef": "enum_status",
  "ui": {
    "component": "SELECT",
    "grid": { "visible": true, "order": 5 },
    "form": { "visible": true, "order": 5 }
  }
}
```

### Índices

```json
"indexes": [
  {
    "name": "IX_Pedido_Cliente",
    "fields": ["cliente_id"],
    "unique": false
  },
  {
    "name": "UK_Usuario_Email",
    "fields": ["email"],
    "unique": true
  },
  {
    "name": "IX_Pedido_Cliente_Data",
    "fields": ["cliente_id", "data_pedido"],
    "unique": false
  }
]
```

### Extensões Não Previstas no Schema (Recursos Avançados)

Estas propriedades não estão no schema oficial mas são suportadas:

#### 1. **Session Context** (Contexto de Sessão)

```json
"metadata": {
  "sessionContext": [
    {
      "entity": "Avaliacao",
      "field": "avaliacaoId",
      "displayField": "avaliacao",
      "label": "Selecione a Avaliação",
      "required": true,
      "inputType": "radio"
    },
    {
      "entity": "Municipio",
      "field": "municipioId",
      "displayField": "nome",
      "label": "Selecione o Município",
      "required": true,
      "inputType": "radio"
    }
  ]
}
```

#### 2. **Access Control** (Controle de Acesso)

```json
"metadata": {
  "accessControl": {
    "roles": [
      {
        "id": "admin",
        "name": "Administrador",
        "description": "Acesso total ao sistema",
        "permissions": [
          {
            "module": "*",
            "access": "full"
          }
        ]
      },
      {
        "id": "usuario",
        "name": "Usuário",
        "description": "Acesso limitado",
        "permissions": [
          {
            "module": "cadastro",
            "access": "readonly"
          }
        ]
      }
    ]
  }
}
```

#### 3. **Session Filter** (Filtro por Sessão)

```json
{
  "id": "avaliacao_indicador",
  "name": "AvaliacaoIndicador",
  "sessionFilter": {
    "field": "municipioId",
    "field2": "avaliacaoId"
  }
}
```

#### 4. **Child Entities** (Entidades Filhas)

```json
{
  "id": "publico_alvo",
  "name": "PublicoAlvo",
  "childEntities": [
    {
      "entity": "SegmentoAtendido",
      "foreignKey": "publicoAlvoId",
      "label": "Segmentos",
      "icon": "category"
    }
  ]
}
```

---

## ⚙️ Arquivos de Configuração

### config.json (Spring Boot + Angular)

```json
{
  "project": {
    "id": "meu-projeto",
    "name": "Meu Projeto",
    "description": "Sistema de gestão",
    "version": "1.0.0"
  },
  "output": {
    "baseDir": "generated/meu-projeto",
    "backendDir": "back",
    "frontendDir": "front",
    "cleanBeforeGenerate": false
  },
  "backend": {
    "language": "java",
    "framework": "spring-boot",
    "javaVersion": "21",
    "springBootVersion": "3.2.0",
    "basePackage": "br.com.meuprojeto",
    "port": 8080,
    "contextPath": "/api",
    "features": {
      "swagger": true,
      "actuator": true,
      "security": true,
      "audit": true
    }
  },
  "frontend": {
    "framework": "angular",
    "angularVersion": "17",
    "port": 4200,
    "title": "Meu Projeto",
    "theme": {
      "primaryColor": "#667eea",
      "secondaryColor": "#764ba2",
      "gradient": "linear-gradient(135deg, #667eea 0%, #764ba2 100%)"
    }
  },
  "database": {
    "type": "sqlserver",
    "host": "localhost",
    "port": 1433,
    "name": "MeuProjetoDB",
    "schema": "dbo",
    "driver": "com.microsoft.sqlserver.jdbc.SQLServerDriver",
    "connectionString": "jdbc:sqlserver://localhost:1433;databaseName=MeuProjetoDB;encrypt=true;trustServerCertificate=true",
    "development": {
      "type": "h2",
      "name": "meuprojeto",
      "console": true,
      "consolePort": 8082
    }
  },
  "security": {
    "jwt": {
      "secret": "seu-secret-key-minimum-256-bits",
      "expiration": 86400000,
      "refreshExpiration": 604800000
    },
    "cors": {
      "allowedOrigins": ["http://localhost:4200"],
      "allowedMethods": ["GET", "POST", "PUT", "DELETE", "OPTIONS"],
      "allowCredentials": true
    },
    "defaultAdmin": {
      "username": "admin",
      "password": "admin123",
      "name": "Administrador",
      "email": "admin@meuprojeto.com.br"
    }
  },
  "logging": {
    "level": "INFO",
    "file": "logs/app.log",
    "maxFileSize": "10MB",
    "maxHistory": 30
  },
  "features": {
    "pagination": {
      "defaultPageSize": 20,
      "maxPageSize": 100
    },
    "upload": {
      "enabled": false,
      "maxFileSize": "10MB",
      "allowedTypes": ["image/jpeg", "image/png", "application/pdf"]
    },
    "email": {
      "enabled": false,
      "host": "smtp.example.com",
      "port": 587,
      "username": "",
      "password": ""
    }
  }
}
```

### config-laravel.json (Laravel + Blade)

```json
{
  "project": {
    "id": "meu-projeto",
    "name": "Meu Projeto",
    "description": "Sistema de gestão",
    "version": "1.0.0"
  },
  "generationType": "laravel",
  "output": {
    "baseDir": "generated/meu-projeto-laravel",
    "cleanBeforeGenerate": false
  },
  "backend": {
    "language": "php",
    "framework": "laravel",
    "phpVersion": "8.2",
    "laravelVersion": "12.0",
    "namespace": "App",
    "port": 8000,
    "apiPrefix": "api/v1",
    "features": {
      "swagger": true,
      "audit": false,
      "security": true,
      "sanctum": true,
      "jwt": true,
      "breeze": true,
      "horizon": false,
      "pulse": true,
      "softDeletes": true,
      "timestamps": true
    },
    "packages": {
      "darkaonline/l5-swagger": "^9.0",
      "tymon/jwt-auth": "^2.2",
      "laravel/sanctum": "^4.0",
      "laravel/breeze": "^2.3",
      "laravel/pulse": "^1.4",
      "livewire/livewire": "^3.6"
    }
  },
  "views": {
    "engine": "blade",
    "includeVue": false,
    "includeReact": false,
    "includeLivewire": true,
    "generateCRUD": true,
    "theme": {
      "primaryColor": "#667eea",
      "secondaryColor": "#764ba2",
      "gradient": "linear-gradient(135deg, #667eea 0%, #764ba2 100%)"
    }
  },
  "database": {
    "type": "mysql",
    "host": "localhost",
    "port": 3306,
    "name": "meu_projeto",
    "username": "root",
    "password": "",
    "charset": "utf8mb4",
    "collation": "utf8mb4_unicode_ci",
    "prefix": "",
    "strict": true,
    "engine": "InnoDB",
    "options": {
      "useMigrations": true,
      "useSeeds": true,
      "useFactories": false
    }
  },
  "security": {
    "jwt": {
      "secret": "${JWT_SECRET}",
      "ttl": 1440,
      "refreshTtl": 20160,
      "algo": "HS256"
    },
    "sanctum": {
      "expiration": 525600,
      "stateful": ["localhost", "localhost:3000", "127.0.0.1"]
    },
    "cors": {
      "allowedOrigins": ["http://localhost:3000", "http://localhost:8000"],
      "allowedMethods": ["GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"],
      "allowedHeaders": ["*"],
      "exposedHeaders": [],
      "maxAge": 0,
      "supportsCredentials": true
    },
    "defaultAdmin": {
      "name": "Administrador",
      "email": "admin@meuprojeto.com.br",
      "password": "admin123"
    }
  },
  "codeGeneration": {
    "models": {
      "extends": "App\\Models\\BaseModel",
      "useSoftDeletes": true,
      "useTimestamps": true,
      "useAuditing": false,
      "useCasts": true,
      "useHidden": true,
      "useGuarded": false
    },
    "controllers": {
      "extends": "App\\Http\\Controllers\\Controller",
      "useResourceController": true,
      "useApiResponses": true,
      "useValidation": true,
      "useTransactions": true
    },
    "migrations": {
      "useForeignKeys": true,
      "useIndexes": true,
      "useTimestamps": true,
      "useSoftDeletes": true
    },
    "routes": {
      "prefix": "api/v1",
      "middleware": ["api", "auth:sanctum"],
      "useResourceRoutes": true
    },
    "requests": {
      "generateFormRequests": true,
      "useAuthorization": true,
      "customErrorMessages": true
    }
  }
}
```

---

## 📊 Carga Inicial de Dados

### Formato: CSV com Ponto-e-Vírgula

**⚠️ IMPORTANTE:** Os arquivos de carga inicial são em **CSV**, não JSON!

### Regras

- ✅ **Separador:** Ponto-e-vírgula (`;`)
- ✅ **Primeira linha:** Nomes das colunas (igual ao `columnName` no metamodelo)
- ✅ **Encoding:** UTF-8 (para acentos)
- ✅ **Nome do arquivo:** `tableName` em **minúsculo com underscores**
- ✅ **Extensão:** `.csv`

### Convenção de Nomenclatura

| Entidade (metamodelo) | tableName | Arquivo CSV |
|----------------------|-----------|-------------|
| `Cliente` | `Cliente` | `cliente.csv` |
| `AnoEscolar` | `AnoEscolar` | `ano_escolar.csv` |
| `AmbitoGestao` | `AmbitoGestao` | `ambito_gestao.csv` |
| `CargaHorariaFormacao` | `CargaHorariaFormacao` | `carga_horaria_formacao.csv` |

### Exemplo 1: Tabela Simples (avaliacao.csv)

```csv
id;avaliacao
1;2025.2
```

### Exemplo 2: Tabela com Vários Campos (ambito_gestao.csv)

```csv
id;nome;ordem
1;Gestão da Rede;1
2;Gestão Escolar;2
3;Coordenação Pedagógica;3
4;Gestão do Infantil;4
```

### Exemplo 3: Tabela com Foreign Key (municipio.csv)

```csv
id;territorio_id;nome;uf;quantidade_escolas
1;1;Aporá;BA;18
2;1;Araçás;BA;18
3;1;Cachoeira;BA;35
13;2;Oliveira dos Brejinhos;BA;
14;2;Morro do Chapéu;BA;
```

**Observações:**
- `territorio_id` é FK que referencia `Territorio.id`
- Campos vazios são permitidos (fim da linha)
- O ID referenciado deve existir na tabela pai

### Exemplo 4: Tabela Completa (cliente.csv)

```csv
id;nome;email;cpf;data_nascimento;ativo
1;João Silva;joao@example.com;12345678901;1985-05-15;true
2;Maria Santos;maria@example.com;98765432109;1990-08-22;true
3;Pedro Oliveira;pedro@example.com;45678912301;1978-03-10;false
```

### Ordem de Importação (CRÍTICO!)

Se você tem relacionamentos (FK), a **ordem importa**:

```
1. Primeiro: Tabelas sem dependências (lookup)
   ├── territorio.csv
   ├── categoria.csv
   └── status.csv

2. Depois: Tabelas que dependem das primeiras
   ├── municipio.csv         (depende de territorio)
   ├── produto.csv           (depende de categoria)
   └── cliente.csv

3. Por último: Tabelas transacionais
   ├── pedido.csv            (depende de cliente)
   └── pedido_item.csv       (depende de pedido e produto)
```

**💡 Dica:** O gerador processa em **ordem alfabética**. Use prefixos numéricos:

```
carga-inicial/
├── 01_territorio.csv
├── 02_categoria.csv
├── 03_municipio.csv
├── 04_produto.csv
├── 05_cliente.csv
├── 10_pedido.csv
└── 11_pedido_item.csv
```

### Casos Especiais

#### Campos Booleanos
```csv
id;nome;ativo
1;João;true
2;Maria;false
3;Pedro;1
4;Ana;0
```

Valores aceitos: `true`, `false`, `1`, `0`, `TRUE`, `FALSE`

#### Campos de Data
```csv
id;nome;data_nascimento
1;João;1985-05-15
2;Maria;1990-08-22
```

Formato: `YYYY-MM-DD`

#### Campos de Data/Hora
```csv
id;descricao;data_hora
1;Evento 1;2025-12-29 14:30:00
2;Evento 2;2025-12-30 09:00:00
```

Formato: `YYYY-MM-DD HH:MM:SS`

#### Campos Decimais
```csv
id;nome;preco
1;Produto A;1250.50
2;Produto B;99.99
```

Use **ponto** como separador decimal (não vírgula)

#### Campos NULL
```csv
id;nome;observacao
1;João;Alguma observação
2;Maria;
3;Pedro;
```

Deixe vazio para NULL

### Checklist de Validação CSV

- [ ] Separador é ponto-e-vírgula (`;`)
- [ ] Primeira linha contém os nomes das colunas
- [ ] Nomes das colunas conferem com `columnName` no metamodelo
- [ ] Nome do arquivo está em minúsculo com underscores
- [ ] Encoding UTF-8 (se tiver acentos)
- [ ] FKs referenciam IDs que existem nas tabelas pai
- [ ] Ordem de importação respeita as dependências
- [ ] Campos decimais usam ponto (não vírgula)
- [ ] Datas no formato YYYY-MM-DD

### ❌ Erros Comuns

#### ERRADO - Usando vírgula como separador:
```csv
id,nome,email
1,João Silva,joao@example.com
```

#### ERRADO - Nome de arquivo incorreto:
```
Cliente.csv          ❌ (maiúscula)
clientes.csv         ❌ (plural)
cliente.csv          ✅ (correto)
```

#### ERRADO - Nomes de colunas diferentes:
```csv
codigo;nomeCompleto;endereco
```
Se no metamodelo é `id`, `nome`, `logradouro`

#### ERRADO - Decimal com vírgula:
```csv
id;preco
1;1250,50          ❌
2;99,99            ❌
```

Correto:
```csv
id;preco
1;1250.50          ✅
2;99.99            ✅
```

---

## 🚀 Como Gerar o Projeto

### Método 1: Comando Genérico

```bash
# Na raiz do projeto Gerador
gerar.bat meu-projeto
```

O gerador detecta automaticamente:
- Se existe `config-laravel.json` → Gera **Laravel**
- Se existe `config.json` → Gera **Spring Boot + Angular**

### Método 2: Script Personalizado

Crie `gerar_meuprojeto.bat` na raiz:

```batch
@echo off
echo ================================================================
echo   GERADOR MEU PROJETO
echo ================================================================

call "C:\Program Files\Apache\apache-maven-3.9.11\bin\mvn.cmd" -f pom.xml clean compile -q
call "C:\Program Files\Apache\apache-maven-3.9.11\bin\mvn.cmd" -f pom.xml exec:java -Dexec.mainClass="br.com.gerador.generator.UnifiedGeneratorMain" -Dexec.args="meu-projeto" -q

echo.
echo ================================================================
echo   GERACAO CONCLUIDA!
echo ================================================================
pause
```

Execute: `gerar_meuprojeto.bat`

### Método 3: Maven Direto

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="br.com.gerador.generator.UnifiedGeneratorMain" -Dexec.args="meu-projeto"
```

---

## ✅ Validação e Troubleshooting

### Checklist Pré-Geração

- [ ] Pasta criada em `metamodel/data/[seu-projeto]`
- [ ] Arquivo `*-model.json` existe e é válido
- [ ] Pelo menos um arquivo de config existe
- [ ] JSON válido (use um validador online)
- [ ] Campos obrigatórios preenchidos:
  - [ ] `metadata.name`
  - [ ] `metadata.version`
  - [ ] `modules` (pelo menos 1)
  - [ ] `entities` (pelo menos 1)
- [ ] Referências FK corretas (entity existe)
- [ ] `entityRef` nos menus existe no array `entities`
- [ ] Nomes de arquivos CSV corretos (se usar carga inicial)

### Validação do Metamodelo

O metamodelo deve seguir o schema em [metamodel-schema.json](metamodel-schema.json).

Validadores online:
- https://www.jsonschemavalidator.net/
- https://jsonschemalint.com/

### Erros Comuns

#### 1. "Pasta não encontrada"

```
Erro: Pasta não encontrada: c:/java/workspace/Gerador/metamodel/data/meu-projeto
```

**Solução:** Crie a pasta `metamodel/data/meu-projeto`

#### 2. "Nenhum arquivo de configuração encontrado"

```
Erro: Nenhum arquivo de configuração encontrado.
Esperado: config.json ou config-*.json
```

**Solução:** Crie `config.json` ou `config-laravel.json`

#### 3. "Nenhum arquivo *-model.json encontrado"

```
Erro: Nenhum arquivo *-model.json encontrado na pasta
```

**Solução:** O arquivo deve terminar com `-model.json`, ex: `meu-projeto-model.json`

#### 4. "JSON inválido"

```
Erro: Unexpected character at line 45
```

**Solução:** Valide o JSON em um validador online

#### 5. "Entidade não encontrada"

```
Erro: entityRef 'Cliente' no menu não corresponde a nenhuma entidade
```

**Solução:** Certifique-se que existe uma entidade com `"name": "Cliente"` no array `entities`

#### 6. "Referência inválida"

```
Erro: reference.entity 'Categoria' não encontrada
```

**Solução:** Verifique se existe uma entidade com `"name": "Categoria"`

### Logs e Debug

Durante a geração, o Maven exibe logs detalhados:

```
╔═══════════════════════════════════════════════════════════╗
║        GERADOR UNIFICADO - MetaModel Framework            ║
╚═══════════════════════════════════════════════════════════╝

Pasta selecionada: meu-projeto
Caminho: c:/java/workspace/Gerador/metamodel/data/meu-projeto

Configuração detectada: config.json

╔═══════════════════════════════════════════════════════════╗
║  FRAMEWORK DETECTADO: SPRING-BOOT
╚═══════════════════════════════════════════════════════════╝

Arquivo de modelo encontrado: meu-projeto-model.json

Carregando MetaModel...
MetaModel carregado: MeuProjeto
Total de entidades: 15

═══════════════════════════════════════════════════════════
GERANDO BACKEND (Spring Boot)
═══════════════════════════════════════════════════════════
...
```

Se houver erros, eles aparecerão aqui.

---

## 📚 Exemplos Completos

### Exemplo 1: Sistema Simples de Cadastro

**Estrutura:**
```
metamodel/data/cadastro-simples/
├── cadastro-simples-model.json
├── config.json
└── carga-inicial/
    ├── categoria.csv
    └── produto.csv
```

**cadastro-simples-model.json:**
```json
{
  "metadata": {
    "name": "CadastroSimples",
    "version": "1.0.0",
    "description": "Sistema simples de cadastro de produtos",
    "database": { "type": "SQLSERVER", "schema": "dbo" }
  },
  "modules": [
    {
      "id": "cadastro",
      "name": "Cadastros",
      "icon": "folder",
      "order": 1,
      "items": [
        { "id": "menu_categoria", "name": "Categorias", "entityRef": "Categoria", "icon": "category", "order": 1 },
        { "id": "menu_produto", "name": "Produtos", "entityRef": "Produto", "icon": "inventory", "order": 2 }
      ]
    }
  ],
  "entities": [
    {
      "id": "categoria",
      "name": "Categoria",
      "tableName": "Categoria",
      "type": "LOOKUP",
      "fields": [
        {
          "id": "categoria_id",
          "name": "id",
          "columnName": "id",
          "label": "Código",
          "dataType": "INTEGER",
          "databaseType": "int",
          "primaryKey": true,
          "autoIncrement": true,
          "required": true
        },
        {
          "id": "categoria_nome",
          "name": "nome",
          "columnName": "nome",
          "label": "Nome",
          "dataType": "STRING",
          "databaseType": "nvarchar",
          "length": 50,
          "required": true
        }
      ]
    },
    {
      "id": "produto",
      "name": "Produto",
      "tableName": "Produto",
      "type": "MAIN",
      "fields": [
        {
          "id": "produto_id",
          "name": "id",
          "columnName": "id",
          "dataType": "INTEGER",
          "databaseType": "int",
          "primaryKey": true,
          "autoIncrement": true
        },
        {
          "id": "produto_categoria_id",
          "name": "categoriaId",
          "columnName": "categoria_id",
          "dataType": "INTEGER",
          "databaseType": "int",
          "required": true,
          "reference": { "entity": "Categoria", "field": "id" }
        },
        {
          "id": "produto_nome",
          "name": "nome",
          "columnName": "nome",
          "dataType": "STRING",
          "databaseType": "nvarchar",
          "length": 100,
          "required": true
        },
        {
          "id": "produto_preco",
          "name": "preco",
          "columnName": "preco",
          "dataType": "DECIMAL",
          "databaseType": "decimal",
          "precision": 10,
          "scale": 2,
          "required": true
        }
      ]
    }
  ]
}
```

**categoria.csv:**
```csv
id;nome
1;Eletrônicos
2;Livros
3;Roupas
```

**produto.csv:**
```csv
id;categoria_id;nome;preco
1;1;Notebook;2500.00
2;1;Mouse;50.00
3;2;Clean Code;89.90
```

### Exemplo 2: Sistema com Enumerações

Ver exemplo completo em: `metamodel/data/icep/icep-model.json`

---

## 📖 Referências

- **Schema JSON:** [metamodel-schema.json](metamodel-schema.json)
- **Exemplo ICEP:** `metamodel/data/icep/icep-model.json`
- **Exemplo Xandel:** `metamodel/data/xandel/xandel-model.json`
- **Gerador Unificado:** `src/main/java/br/com/gerador/generator/UnifiedGeneratorMain.java`

---

## 📝 Histórico de Revisões

| Versão | Data | Alterações |
|--------|------|------------|
| 1.0.0 | 2025-12-29 | Versão inicial do documento |

---

## 💡 Dicas Finais

1. **Sempre valide o JSON** antes de gerar
2. **Use os exemplos ICEP e Xandel** como referência
3. **Comece simples** - adicione complexidade gradualmente
4. **Teste a geração** com um modelo mínimo primeiro
5. **Documente suas customizações** se adicionar extensões
6. **Mantenha os CSVs simples** - evite complexidade excessiva
7. **Respeite a ordem de FKs** nos arquivos CSV
8. **Use nomes consistentes** - camelCase para campos, PascalCase para entidades

---

**Última atualização:** 2025-12-29
**Mantido por:** Equipe Gerador MetaModel
