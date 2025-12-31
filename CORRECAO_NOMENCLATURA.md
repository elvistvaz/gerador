# Plano de Correção: Padronização de Nomenclatura

## Problema Identificado

Diversos CSVs utilizam **snake_case** (exemplo: `nome_banco`) nas colunas, enquanto o JSON define os campos em **PascalCase** (exemplo: `NomeBanco`). Isso causa incompatibilidade no mapeamento automático.

---

## Opções de Solução

### Opção 1: Ajustar CSVs para PascalCase (RECOMENDADO)
**Vantagem:** Mantém consistência com o padrão SQL Server e definições JSON
**Desvantagem:** Requer edição de múltiplos CSVs

### Opção 2: Ajustar JSON para snake_case
**Vantagem:** Não precisa alterar dados de carga
**Desvantagem:** Inconsistente com padrão SQL Server

### Opção 3: Implementar mapeamento tolerante a case
**Vantagem:** Aceita ambos os formatos
**Desvantagem:** Pode ocultar erros reais de nomenclatura

---

## Entidades Afetadas por Nomenclatura

### 1. banco.csv

**Estrutura Atual:**
```csv
id_banco;nome_banco
001;Banco do Brasil
```

**Estrutura Corrigida:**
```csv
id_Banco;NomeBanco
001;Banco do Brasil
```

**Script PowerShell para Correção:**
```powershell
$file = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\banco.csv"
$content = Get-Content $file
$content[0] = $content[0] -replace 'id_banco', 'id_Banco' -replace 'nome_banco', 'NomeBanco'
Set-Content $file $content
```

---

### 2. bairro.csv

**Estrutura Atual:**
```csv
id_bairro;nome_bairro
1;Centro
```

**Estrutura Corrigida:**
```csv
id_Bairro;NomeBairro
1;Centro
```

**Script PowerShell:**
```powershell
$file = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\bairro.csv"
$content = Get-Content $file
$content[0] = $content[0] -replace 'id_bairro', 'id_Bairro' -replace 'nome_bairro', 'NomeBairro'
Set-Content $file $content
```

---

### 3. cidade.csv

**Estrutura Atual:**
```csv
id_cidade;nome_cidade;uf;ddd;iss
1;Salvador;BA;71;5.00
```

**Estrutura Corrigida:**
```csv
id_Cidade;NomeCidade;UF;DDD;ISS
1;Salvador;BA;71;5.00
```

**Script PowerShell:**
```powershell
$file = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\cidade.csv"
$content = Get-Content $file
$content[0] = $content[0] -replace 'id_cidade', 'id_Cidade' -replace 'nome_cidade', 'NomeCidade'
Set-Content $file $content
```

---

### 4. conselho.csv

**Estrutura Atual:**
```csv
id_conselho;nome_conselho;sigla
1;Conselho Regional de Medicina da Bahia;CREMEB
```

**Estrutura Corrigida:**
```csv
id_Conselho;NomeConselho;Sigla
1;Conselho Regional de Medicina da Bahia;CREMEB
```

**Script PowerShell:**
```powershell
$file = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\conselho.csv"
$content = Get-Content $file
$content[0] = $content[0] -replace 'id_conselho', 'id_Conselho' -replace 'nome_conselho', 'NomeConselho' -replace 'sigla', 'Sigla'
Set-Content $file $content
```

---

### 5. especialidade.csv

**Estrutura Atual:**
```csv
id_especialidade;nome_especialidade
```

**Estrutura Corrigida:**
```csv
id_Especialidade;NomeEspecialidade
```

**Script PowerShell:**
```powershell
$file = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\especialidade.csv"
$content = Get-Content $file
$content[0] = $content[0] -replace 'id_especialidade', 'id_Especialidade' -replace 'nome_especialidade', 'NomeEspecialidade'
Set-Content $file $content
```

---

### 6. operadora.csv

**Estrutura Atual:**
```csv
id_operadora;nome_operadora
```

**Estrutura Corrigida:**
```csv
id_Operadora;NomeOperadora
```

**Script PowerShell:**
```powershell
$file = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\operadora.csv"
$content = Get-Content $file
$content[0] = $content[0] -replace 'id_operadora', 'id_Operadora' -replace 'nome_operadora', 'NomeOperadora'
Set-Content $file $content
```

---

### 7. estado_civil.csv

**Estrutura Atual:**
```csv
id_estado_civil;nome_estado_civil
1;Solteiro(a)
```

**Estrutura Corrigida:**
```csv
id_EstadoCivil;NomeEstadoCivil
1;Solteiro(a)
```

**Script PowerShell:**
```powershell
$file = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\estado_civil.csv"
$content = Get-Content $file
$content[0] = $content[0] -replace 'id_estado_civil', 'id_EstadoCivil' -replace 'nome_estado_civil', 'NomeEstadoCivil'
Set-Content $file $content
```

---

### 8. usuario.csv

**Estrutura Atual:**
```csv
id_usuario;usuario_nome;usuario_login;usuario_senha;usuario_nivel_acesso;usuario_inativo
```

**Estrutura Corrigida (Opção A - Remover prefixo):**
```csv
id_Usuario;UsuarioNome;UsuarioLogin;UsuarioSenha;UsuarioNivelAcesso;UsuarioInativo
```

**OU Estrutura Corrigida (Opção B - Manter prefixo mas corrigir case):**
```csv
id_Usuario;Usuario_Nome;Usuario_Login;Usuario_Senha;Usuario_NivelAcesso;Usuario_Inativo
```

**RECOMENDAÇÃO:** Usar Opção A (sem prefixo)

**Script PowerShell:**
```powershell
$file = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\usuario.csv"
$content = Get-Content $file
$content[0] = $content[0] `
    -replace 'id_usuario', 'id_Usuario' `
    -replace 'usuario_nome', 'UsuarioNome' `
    -replace 'usuario_login', 'UsuarioLogin' `
    -replace 'usuario_senha', 'UsuarioSenha' `
    -replace 'usuario_nivel_acesso', 'UsuarioNivelAcesso' `
    -replace 'usuario_inativo', 'UsuarioInativo'
Set-Content $file $content
```

---

## Script PowerShell Unificado

Execute este script para corrigir todos os CSVs de uma vez:

```powershell
# Script de correção de nomenclatura em massa
# Executar de: c:\java\workspace\Gerador

$baseDir = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial"

# Função auxiliar
function Fix-CsvHeader {
    param(
        [string]$FilePath,
        [hashtable]$Replacements
    )

    if (-not (Test-Path $FilePath)) {
        Write-Warning "Arquivo não encontrado: $FilePath"
        return
    }

    $content = Get-Content $FilePath -Encoding UTF8
    if ($content.Length -eq 0) {
        Write-Warning "Arquivo vazio: $FilePath"
        return
    }

    $header = $content[0]
    foreach ($key in $Replacements.Keys) {
        $header = $header -replace $key, $Replacements[$key]
    }

    $content[0] = $header
    Set-Content $FilePath $content -Encoding UTF8
    Write-Host "✓ Corrigido: $(Split-Path $FilePath -Leaf)"
}

# Correções
Fix-CsvHeader "$baseDir\banco.csv" @{
    'id_banco' = 'id_Banco'
    'nome_banco' = 'NomeBanco'
}

Fix-CsvHeader "$baseDir\bairro.csv" @{
    'id_bairro' = 'id_Bairro'
    'nome_bairro' = 'NomeBairro'
}

Fix-CsvHeader "$baseDir\cidade.csv" @{
    'id_cidade' = 'id_Cidade'
    'nome_cidade' = 'NomeCidade'
}

Fix-CsvHeader "$baseDir\conselho.csv" @{
    'id_conselho' = 'id_Conselho'
    'nome_conselho' = 'NomeConselho'
}

Fix-CsvHeader "$baseDir\especialidade.csv" @{
    'id_especialidade' = 'id_Especialidade'
    'nome_especialidade' = 'NomeEspecialidade'
}

Fix-CsvHeader "$baseDir\operadora.csv" @{
    'id_operadora' = 'id_Operadora'
    'nome_operadora' = 'NomeOperadora'
}

Fix-CsvHeader "$baseDir\estado_civil.csv" @{
    'id_estado_civil' = 'id_EstadoCivil'
    'nome_estado_civil' = 'NomeEstadoCivil'
}

Fix-CsvHeader "$baseDir\usuario.csv" @{
    'id_usuario' = 'id_Usuario'
    'usuario_nome' = 'UsuarioNome'
    'usuario_login' = 'UsuarioLogin'
    'usuario_senha' = 'UsuarioSenha'
    'usuario_nivel_acesso' = 'UsuarioNivelAcesso'
    'usuario_inativo' = 'UsuarioInativo'
}

Write-Host "`nCorreção de nomenclatura concluída!" -ForegroundColor Green
Write-Host "Execute novamente a análise para verificar: node analyze_xandel_full.js"
```

---

## Salvando Backup Antes das Alterações

```powershell
# Criar backup dos CSVs antes de modificar
$backupDir = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial-backup-$(Get-Date -Format 'yyyyMMdd-HHmmss')"
$sourceDir = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial"

Copy-Item -Path $sourceDir -Destination $backupDir -Recurse
Write-Host "Backup criado em: $backupDir" -ForegroundColor Green
```

---

## Validação Pós-Correção

Após executar as correções, valide:

1. **Verificar cabeçalhos:**
```powershell
$csvDir = "c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial"
Get-ChildItem $csvDir -Filter *.csv | ForEach-Object {
    Write-Host "`n$($_.Name):"
    Get-Content $_.FullName -First 1
}
```

2. **Reexecutar análise:**
```bash
cd "c:\java\workspace\Gerador"
node analyze_xandel_full.js > relatorio_pos_nomenclatura.txt
```

3. **Comparar resultados:**
   - Antes: 13 entidades com incompatibilidades
   - Esperado depois: 6-7 entidades (apenas FKs faltantes)

---

## Resumo de Correções

| Arquivo | Colunas Afetadas | Status |
|---------|------------------|---------|
| banco.csv | id_banco → id_Banco, nome_banco → NomeBanco | Pendente |
| bairro.csv | id_bairro → id_Bairro, nome_bairro → NomeBairro | Pendente |
| cidade.csv | id_cidade → id_Cidade, nome_cidade → NomeCidade | Pendente |
| conselho.csv | id_conselho → id_Conselho, nome_conselho → NomeConselho | Pendente |
| especialidade.csv | id_especialidade → id_Especialidade, nome_especialidade → NomeEspecialidade | Pendente |
| operadora.csv | id_operadora → id_Operadora, nome_operadora → NomeOperadora | Pendente |
| estado_civil.csv | id_estado_civil → id_EstadoCivil, nome_estado_civil → NomeEstadoCivil | Pendente |
| usuario.csv | usuario_* → Usuario* (6 colunas) | Pendente |

**Total de CSVs a corrigir:** 8
**Total de colunas a renomear:** ~16

---

## Próximos Passos

1. ✅ Criar backup dos CSVs
2. ⬜ Executar script de correção
3. ⬜ Validar cabeçalhos corrigidos
4. ⬜ Reexecutar análise de compatibilidade
5. ⬜ Proceder com correção de FKs (ver ACAO_CORRETIVA_FKs.md)
