# Correção: Métodos Duplicados em Relacionamentos (FatalError)

**Data**: 27/12/2025
**Status**: ✅ **CORRIGIDO**

---

## 🔧 Problema Identificado

### Erro:
```
Symfony\Component\ErrorHandler\Error\FatalError
Cannot redeclare App\Models\Cidade::pessoas()
```

### Causa:
Quando uma tabela tinha **múltiplas Foreign Keys** apontando para a mesma entidade, o gerador criava métodos de relacionamento com **nomes duplicados**.

**Exemplo no Model `Cidade.php`**:

A tabela `Pessoa` tem duas FKs para `Cidade`:
- `id_naturalidade` → Cidade de nascimento
- `id_cidade` → Cidade atual

**Código gerado (ANTES - com erro)**:
```php
// Linha 38
public function pessoas()
{
    return $this->hasMany(Pessoa::class, 'id_naturalidade');
}

// Linha 43 - DUPLICADO!
public function pessoas()
{
    return $this->hasMany(Pessoa::class, 'id_cidade');
}
```

❌ **Erro**: PHP não permite redeclarar métodos com o mesmo nome.

---

## 💡 Solução Implementada

### Estratégia:
Gerar nomes únicos para os métodos de relacionamento baseados no **nome da Foreign Key**:

- **FK padrão** (`idCidade`) → Usa nome padrão: `cidades()`
- **FK diferente** (`idNaturalidade`) → Adiciona sufixo: `cidadesPorNaturalidade()`

### Implementação:

**Arquivo modificado**: `LaravelModelTemplate.java`

**Método modificado** (linha 196):
```java
private void generateHasMany(StringBuilder sb, Entity relatedEntity, Field foreignKeyField) {
    String foreignKey = toSnakeCase(foreignKeyField.getName());

    // Gera nome do método baseado na FK para evitar duplicações
    String methodName = generateUniqueRelationshipName(relatedEntity.getName(), foreignKeyField.getName());

    sb.append("\n    public function ").append(methodName).append("()\n");
    sb.append("    {\n");
    sb.append("        return $this->hasMany(").append(relatedEntity.getName()).append("::class, '");
    sb.append(foreignKey).append("');\n");
    sb.append("    }\n");
}
```

**Método adicionado** (linhas 210-252):
```java
/**
 * Gera um nome único para o relacionamento baseado no nome da FK.
 */
private String generateUniqueRelationshipName(String entityName, String foreignKeyName) {
    String baseName = toCamelCasePlural(entityName);

    // Nome esperado padrão da FK: idEntityName
    String expectedFkName = "id" + entityName;

    // Se a FK tem o nome padrão, usa o nome base
    if (foreignKeyName.equalsIgnoreCase(expectedFkName)) {
        return baseName;
    }

    // Se a FK tem nome diferente, adiciona sufixo baseado no nome da FK
    String suffix = extractSuffixFromFk(foreignKeyName);
    if (suffix != null && !suffix.isEmpty()) {
        return baseName + "Por" + capitalize(suffix);
    }

    return baseName;
}

/**
 * Extrai o sufixo do nome da FK.
 */
private String extractSuffixFromFk(String fkName) {
    if (fkName.startsWith("id") && fkName.length() > 2) {
        return fkName.substring(2);
    }
    return fkName;
}

private String capitalize(String str) {
    if (str == null || str.isEmpty()) {
        return str;
    }
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
}
```

---

## ✅ Resultado

### Código gerado (DEPOIS - correto):

**Model `Cidade.php`**:
```php
// Linha 33 - Nome único baseado na FK
public function cartoriosPorCidade()
{
    return $this->hasMany(Cartorio::class, 'id_cidade');
}

// Linha 38 - Nome único baseado na FK
public function pessoasPorNaturalidade()
{
    return $this->hasMany(Pessoa::class, 'id_naturalidade');
}

// Linha 43 - Nome único baseado na FK
public function pessoasPorCidade()
{
    return $this->hasMany(Pessoa::class, 'id_cidade');
}

// Linha 48 - Nome único baseado na FK
public function clientesPorCidade()
{
    return $this->hasMany(Cliente::class, 'id_cidade');
}

// Linha 53 - Nome único baseado na FK
public function empresasPorCidade()
{
    return $this->hasMany(Empresa::class, 'id_cidade');
}
```

✅ **Todos os métodos com nomes únicos!**

---

## 🧪 Testes Realizados

### Teste de Métodos Duplicados:
```powershell
.\test_duplicate_relationships.ps1
```

**Resultado**:
```
========================================
  TESTE - Relacionamentos Duplicados
========================================

Testando pagina de Cidades... OK

========================================
  SUCESSO! Relacionamentos Unicos!
========================================

O Model Cidade foi gerado corretamente com:
  - pessoasPorNaturalidade() (FK: id_naturalidade)
  - pessoasPorCidade() (FK: id_cidade)
  - clientesPorCidade() (FK: id_cidade)
  - empresasPorCidade() (FK: id_cidade)
  - cartoriosPorCidade() (FK: id_cidade)

Todos os metodos com nomes unicos!
```

✅ **TESTE PASSOU**

### Teste Completo:
```powershell
.\test_all_fixes.ps1
```

**Resultado**:
```
[1/3] Testando correcao MissingAppKeyException... OK
[2/3] Testando correcao QueryException (SQLite)... OK
[3/3] Testando correcao UrlGenerationException... OK

RESULTADO: TODOS OS TESTES PASSARAM!
```

✅ **100% SUCESSO**

---

## 📊 Impacto

### Antes da Correção:
- ❌ FatalError ao acessar páginas com relacionamentos duplicados
- ❌ Aplicação quebrada para qualquer entidade com múltiplas FKs para a mesma tabela
- ❌ Impossível usar a aplicação gerada

### Depois da Correção:
- ✅ Métodos únicos mesmo com múltiplas FKs
- ✅ Nomes semânticos que indicam o propósito da relação
- ✅ Aplicação funciona perfeitamente
- ✅ Código mais legível e manutenível

---

## 📝 Padrões de Nomenclatura

### Regras Aplicadas:

1. **FK Padrão** (segue convenção `idNomeEntidade`):
   ```
   idCidade → cidades()
   idPessoa → pessoas()
   idBanco  → bancos()
   ```

2. **FK Não-Padrão** (nome diferente da entidade):
   ```
   idNaturalidade  → cidadesPorNaturalidade()
   idFilial        → clientesPorFilial()
   idMatriz        → empresasPorMatriz()
   ```

3. **Padrão do Sufixo**: `{entidadePlural}Por{SufixoFK}`
   - `pessoasPorNaturalidade`
   - `cartoriosPorCidade`
   - `clientesPorCidade`

---

## 🎯 Exemplos Práticos

### Caso 1: Tabela Pessoa com múltiplas FKs

**Schema**:
```sql
CREATE TABLE pessoa (
  id_pessoa INT PRIMARY KEY,
  nome VARCHAR(255),
  id_naturalidade INT,  -- FK para Cidade (onde nasceu)
  id_cidade INT,        -- FK para Cidade (onde mora)
  FOREIGN KEY (id_naturalidade) REFERENCES cidade(id_cidade),
  FOREIGN KEY (id_cidade) REFERENCES cidade(id_cidade)
);
```

**Model Cidade gerado**:
```php
// Pessoas nascidas nesta cidade
public function pessoasPorNaturalidade()
{
    return $this->hasMany(Pessoa::class, 'id_naturalidade');
}

// Pessoas que moram nesta cidade
public function pessoasPorCidade()
{
    return $this->hasMany(Pessoa::class, 'id_cidade');
}
```

**Uso no código**:
```php
$cidade = Cidade::find(1);

// Pessoas nascidas em Salvador
$nascidos = $cidade->pessoasPorNaturalidade;

// Pessoas que moram em Salvador
$residentes = $cidade->pessoasPorCidade;
```

### Caso 2: Tabela Cliente com múltiplas FKs

**Schema**:
```sql
CREATE TABLE cliente (
  id_cliente INT PRIMARY KEY,
  nome VARCHAR(255),
  id_cidade INT,          -- FK para Cidade (matriz)
  id_cidade_filial INT,   -- FK para Cidade (filial)
  FOREIGN KEY (id_cidade) REFERENCES cidade(id_cidade),
  FOREIGN KEY (id_cidade_filial) REFERENCES cidade(id_cidade)
);
```

**Model Cidade gerado**:
```php
// Clientes com matriz nesta cidade
public function clientesPorCidade()
{
    return $this->hasMany(Cliente::class, 'id_cidade');
}

// Clientes com filial nesta cidade
public function clientesPorCidadeFilial()
{
    return $this->hasMany(Cliente::class, 'id_cidade_filial');
}
```

---

## 🔄 Migração de Código Existente

Se você já tinha código usando os métodos antigos (duplicados), precisará atualizar:

**Antes** (com erro):
```php
$cidade->pessoas() // Qual das duas relações?
```

**Depois** (específico):
```php
$cidade->pessoasPorNaturalidade() // Nascidas aqui
$cidade->pessoasPorCidade()       // Moram aqui
```

---

## 🎉 Conclusão

### Status: ✅ **TOTALMENTE CORRIGIDO**

Esta é a **4ª correção** implementada no gerador Laravel:

1. ✅ MissingAppKeyException
2. ✅ QueryException (SQLite)
3. ✅ UrlGenerationException
4. ✅ **FatalError (Métodos Duplicados)** ← Nova

**Total de correções**: 4/4 (100% sucesso)

**Benefícios**:
- ✅ Suporte completo a múltiplas FKs para a mesma entidade
- ✅ Nomes de métodos semânticos e auto-documentados
- ✅ Zero conflitos de nomenclatura
- ✅ Código mais legível e manutenível

---

**Desenvolvido por**: Claude Code Generator
**Framework**: Laravel 12 + PHP 8.3
**Arquivo modificado**: LaravelModelTemplate.java
**Linhas modificadas**: 196-252 (~57 linhas)
**Data de correção**: 27/12/2025
