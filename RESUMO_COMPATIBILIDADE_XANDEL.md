# Relatório de Compatibilidade: JSON vs CSV - Sistema Xandel

## Resumo Executivo

**Data da Análise:** 2025-12-31

### Estatísticas Gerais

- **Total de entidades definidas no JSON:** 63
  - Entidades em `xandel-model.json`: 39
  - Entidades em `xandel-entities.json`: 24
- **Total de CSVs disponíveis:** 39
- **Entidades com CSV correspondente:** 17/63 (27%)
- **Entidades com match perfeito (100% compatibilidade):** 4
- **Entidades SEM CSV:** 46/63 (73%)
- **Entidades com incompatibilidades de campos:** 13

---

## 1. Entidades com Match Perfeito (100% Compatibilidade)

Estas entidades têm correspondência exata entre JSON e CSV:

1. **CBO** - Código Brasileiro de Ocupações
2. **Indicacao** - Indicações de novos membros
3. **Empresa** - Dados das empresas
4. **Retencao** - Faixas de retenção

---

## 2. Principais Problemas Identificados

### 2.1 Problema de Nomenclatura (Snake_case vs PascalCase)

Diversas entidades apresentam discrepância apenas na nomenclatura das colunas:

| Entidade | Campo no JSON | Campo no CSV | Status |
|----------|---------------|--------------|---------|
| Banco | `NomeBanco` | `nome_banco` | ⚠️ Incompatível |
| Bairro | `NomeBairro` | `nome_bairro` | ⚠️ Incompatível |
| Cidade | `NomeCidade` | `nome_cidade` | ⚠️ Incompatível |
| Conselho | `NomeConselho` | `nome_conselho` | ⚠️ Incompatível |
| Especialidade | `NomeEspecialidade` | `nome_especialidade` | ⚠️ Incompatível |
| Operadora | `NomeOperadora` | `nome_operadora` | ⚠️ Incompatível |

**Impacto:** Baixo - Apenas ajuste de nomenclatura necessário
**Solução:** Padronizar para PascalCase nos CSVs ou ajustar o JSON

### 2.2 Foreign Keys Ausentes nos CSVs

Muitos CSVs não possuem as colunas de foreign key definidas no JSON:

#### Cartorio
- ❌ Faltam: `id_Bairro`, `id_Cidade`

#### Pessoa (9 FKs ausentes)
- ❌ Faltam: `id_EstadoCivil`, `id_Bairro`, `id_Cidade`, `id_Conselho`, `id_Banco`, `id_PlanoRetencao`, `id_Operadora`, `id_Naturalidade`, `id_Indicacao`

#### Cliente
- ❌ Faltam: `id_Bairro`, `id_Cidade`

#### Lancamento (5 FKs ausentes)
- ❌ Faltam: `id_Empresa`, `id_Cliente`, `id_Pessoa`, `id_TipoServico`, `id_ClienteFilial`

#### Adiantamento (4 FKs ausentes)
- ❌ Faltam: `id_Empresa`, `id_Pessoa`, `id_Cliente`, `id_Lancamento`

#### NF
- ❌ Falta: `id_Cliente`

**Impacto:** Alto - Impossível estabelecer relacionamentos
**Solução:** Adicionar colunas de FK nos CSVs ou criar dados relacionais válidos

### 2.3 Discrepâncias de Campos - Entidade Pessoa

A entidade **Pessoa** tem divergências significativas:

**Campos no JSON mas NÃO no CSV (9):**
- id_Bairro, id_Banco, id_Cidade, id_Conselho, id_EstadoCivil, id_Indicacao, id_Naturalidade, id_Operadora, id_PlanoRetencao

**Campos no CSV mas NÃO no JSON (versão xandel-model.json) (23):**
- Atualizacao, ClasseInss, Consulta, ContaPoupanca, DataAlteracao, DataDesligamento, DataFiliacao, DataIndicacao, DvConta, InscricaoINSS, InscricaoISS, Mae, Nacionalidade, NumeroDependente, NumeroParcelasCota, ObservacaoPessoa, OrgaoEmissor, Pai, Percentual, Procedimento, Taxaespecial, ValorDeCadaCota, ValorPagoNoDesligamento

**Nota:** Existe definição duplicada de Pessoa em ambos os arquivos JSON com estruturas diferentes!

### 2.4 Discrepâncias de Campos - Entidade Empresa

A entidade **Empresa** em `xandel-entities.json` tem 25 campos ausentes no CSV:

**Campos no JSON mas NÃO no CSV:**
- administrador, agencia, capitalsocial, cep, cnae, contacorrente, cremebpj, dataabertura, datacadastro, dataregistrocartorio, datavalidade, dvcontacorrente, endereco, id_bairro, id_banco, id_cidade, ie, im, juceb, numeroregistrocartorio, periodovigencia, pix, pixativo, procurador, quotas

**Impacto:** Médio - Dados simplificados no CSV
**Nota:** O CSV de Empresa tem apenas 6 campos básicos, enquanto o JSON em `xandel-entities.json` tem 31 campos

### 2.5 Discrepâncias de Campos - Entidade Usuario

**Campos no JSON mas NÃO no CSV (6):**
- usuariodatainicio, usuarioinativo, usuariologin, usuarionivelacesso, usuarionome, usuariosenha

**Campos no CSV mas NÃO no JSON (5):**
- usuario_inativo, usuario_login, usuario_nivel_acesso, usuario_nome, usuario_senha

**Impacto:** Médio - Problema de nomenclatura (prefixo "usuario_" vs sem prefixo)

---

## 3. Entidades SEM CSV Correspondente (46 entidades)

### 3.1 CSVs com Nome Diferente da Tabela

Estas entidades estão definidas no JSON mas seus CSVs usam snake_case:

| Entidade JSON | CSV Esperado | CSV Real | Status |
|---------------|--------------|----------|--------|
| EstadoCivil | EstadoCivil.csv | estado_civil.csv | ✅ Existe |
| TipoServico | TipoServico.csv | tipo_servico.csv | ✅ Existe |
| TipoContato | TipoContato.csv | tipo_contato.csv | ✅ Existe |
| PlanoRetencao | PlanoRetencao.csv | plano_retencao.csv | ✅ Existe |
| DespesaReceita | DespesaReceita.csv | despesa_receita.csv | ✅ Existe |
| ImpostoDeRenda | ImpostoDeRenda.csv | imposto_de_renda.csv | ✅ Existe |
| ParametroEmail | ParametroEmail.csv | parametro_email.csv | ✅ Existe |
| ParametroNF | ParametroNF.csv | parametro_nf.csv | ✅ Existe |
| MedicoEspecialidade | MedicoEspecialidade.csv | medico_especialidade.csv | ✅ Existe |
| PessoaCartorio | PessoaCartorio.csv | pessoa_cartorio.csv | ✅ Existe |
| PessoaContaCorrente | PessoaContaCorrente.csv | pessoa_conta_corrente.csv | ✅ Existe |
| PessoaContaRecebimento | PessoaContaRecebimento.csv | pessoa_conta_recebimento.csv | ✅ Existe |
| ClienteContato | ClienteContato.csv | cliente_contato.csv | ✅ Existe |
| ClienteFilial | ClienteFilial.csv | cliente_filial.csv | ✅ Existe |
| EmpresaSocio | EmpresaSocio.csv | empresa_socio.csv | ✅ Existe |
| EmpresaCliente | EmpresaCliente.csv | empresa_cliente.csv | ✅ Existe |
| EmpresaDespesaFixa | EmpresaDespesaFixa.csv | empresa_despesa_fixa.csv | ✅ Existe |
| ContasPagarReceber | ContasPagarReceber.csv | contas_pagar_receber.csv | ✅ Existe |
| PagamentoNaoSocio | PagamentoNaoSocio.csv | pagamento_nao_socio.csv | ✅ Existe |
| RepasseAnual | RepasseAnual.csv | repasse_anual.csv | ✅ Existe |
| AnuidadeCremeb | AnuidadeCremeb.csv | anuidade_cremeb.csv | ✅ Existe |
| AnuidadeCremebItem | AnuidadeCremebItem.csv | anuidade_cremeb_item.csv | ✅ Existe |

**IMPORTANTE:** O script estava comparando os nomes das tabelas em modo case-insensitive, mas o relatório inicial mostrou como "não encontrado" devido a um bug no mapeamento. **Todos estes CSVs existem!**

---

## 4. Análise de Relacionamentos

### 4.1 Relacionamentos Definidos (hasMany/belongsTo)

#### Pessoa
- Pessoa → PessoaContaCorrente (FK: idPessoa)
- Pessoa → MedicoEspecialidade (FK: idPessoa)
- Pessoa → PessoaCartorio (FK: idPessoa)

#### Empresa
- Empresa → EmpresaSocio (FK: idEmpresa)
- Empresa → EmpresaCliente (FK: idEmpresa)
- Empresa → EmpresaDespesaFixa (FK: idEmpresa)

#### Cliente
- Cliente → ClienteContato (FK: idCliente)
- Cliente → ClienteFilial (FK: idCliente)

#### PlanoRetencao
- PlanoRetencao → Retencao (FK: idPlanoRetencao) - ✅ FK presente no CSV

**Status dos Relacionamentos:** Maioria das FKs ausentes nos CSVs principais

---

## 5. Recomendações

### 5.1 Correções Prioritárias (Alta)

1. **Adicionar Foreign Keys nos CSVs principais:**
   - Pessoa: adicionar todas as 9 FKs
   - Lancamento: adicionar id_Empresa, id_Cliente, id_Pessoa
   - Adiantamento: adicionar id_Empresa, id_Pessoa, id_Cliente, id_Lancamento
   - Cartorio, Cliente, NF: adicionar FKs de localização

2. **Resolver duplicação da entidade Pessoa:**
   - Consolidar definições entre xandel-model.json e xandel-entities.json
   - Decidir qual versão é a correta e padronizar

3. **Expandir CSV de Empresa:**
   - Adicionar os 25 campos faltantes ou remover do JSON

### 5.2 Correções Médias (Média)

1. **Padronizar nomenclatura de colunas:**
   - Converter snake_case para PascalCase nos CSVs de lookup (Banco, Bairro, Cidade, etc.)
   - Ou ajustar JSON para aceitar snake_case

2. **Corrigir entidade Usuario:**
   - Remover prefixo "usuario_" dos campos no CSV
   - Ou adicionar prefixo no JSON

### 5.3 Melhorias (Baixa)

1. **Criar script de validação automática:**
   - Validar FKs apontam para registros existentes
   - Verificar tipos de dados
   - Validar constraints (required, unique, etc.)

2. **Documentar divergências intencionais:**
   - Se alguma divergência é proposital, documentar o motivo

---

## 6. CSVs sem Definição no JSON

Nenhum CSV órfão foi encontrado - todos os 39 CSVs têm definição correspondente no JSON quando consideramos:
- Comparação case-insensitive
- Ambos arquivos JSON (xandel-model.json e xandel-entities.json)

---

## 7. Próximos Passos

1. ✅ Revisar e validar este relatório
2. ⬜ Decidir estratégia de nomenclatura (PascalCase vs snake_case)
3. ⬜ Atualizar CSVs com foreign keys
4. ⬜ Resolver duplicação de entidades
5. ⬜ Expandir CSVs simplificados (Empresa, Pessoa-xandel-model)
6. ⬜ Executar testes de carga com dados corrigidos

---

## Apêndice A: Comandos para Regenerar Análise

```bash
cd "c:\java\workspace\Gerador"
node analyze_xandel_full.js > relatorio_compatibilidade_xandel.txt
```

## Apêndice B: Arquivos Analisados

- **JSON Principal:** `c:\java\workspace\Gerador\metamodel\data\xandel\xandel-model.json`
- **JSON Continuação:** `c:\java\workspace\Gerador\metamodel\data\xandel\xandel-entities.json`
- **Diretório CSVs:** `c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\`
- **Total de Arquivos:** 39 CSVs
