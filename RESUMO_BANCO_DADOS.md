# Resumo da Estrutura do Banco de Dados - Sociedade

**Data de Extração:** 11/12/2025
**Total de Tabelas:** 39
**Total de Colunas:** 348
**Total de Foreign Keys:** 22

---

## Tabelas Principais

### 1. **Lancamento** (19 colunas)
Tabela central de lançamentos financeiros.

**Colunas principais:**
- `id_lancamento` (int IDENTITY) - PK
- `Data` (datetime)
- `id_Empresa` (smallint)
- `id_Cliente` (smallint)
- `id_Pessoa` (int)
- `NF` (int)
- `ValorBruto`, `Despesas`, `Retencao` (money)
- `ValorLiquido`, `ValorTaxa`, `ValorRepasse` (money)
- `Baixa` (datetime)
- `MesAno` (char)
- `Taxa` (numeric)

**Relacionamentos:** Nenhum FK explícito (verificar ALTER TABLE no SQL)

---

### 2. **Pessoa** (48 colunas, 3 FKs)
Cadastro de pessoas/sócios.

**Colunas principais:**
- `id_Pessoa` (int) - PK
- `id_Conselho`, `NumeroConselho`
- `Nome`, `Nascimento`
- `RG`, `CPF`, `OrgaoEmissor`
- `InscricaoISS`, `InscricaoINSS`
- `Endereco`, `CEP`
- `id_Banco`, `Agencia`, `Conta`, `DvConta`
- `Email`, `Telefone`, `Celular`
- `DataAdesao`, `DataDesligamento`, `DataInativo`
- `Taxaespecial`, `Percentual`
- `id_PlanoRetencao`

**Foreign Keys:**
- `id_Bairro` → Bairro(id_Bairro)
- `id_Cidade` → Cidade(id_Cidade)
- `id_Cartorio` → Cartorio(id_Cartorio)

---

### 3. **Empresa** (33 colunas, 4 FKs)
Cadastro de empresas.

**Colunas principais:**
- `id_Empresa` (smallint) - PK
- `NomeEmpresa`, `FantasiaEmpresa`
- `CNPJ`, `IE`, `IM`
- `TaxaRetencao` (numeric)
- `DataAbertura`, `DataCadastro`
- `JUCEB`, `CNAE`, `CREMEBPJ`
- `CapitalSocial`, `Quotas`
- `Endereco`, `CEP`
- `id_Banco`, `Agencia`, `ContaCorrente`
- `PIXAtivo`, `PIX`
- `Inativa` (bit)

**Foreign Keys:**
- `id_Bairro` → Bairro(id_Bairro)
- `id_Banco` → Banco(id_Banco)
- `id_Cidade` → Cidade(id_Cidade)
- `id_Cliente` → Cliente(id_Cliente)

---

### 4. **Cliente** (22 colunas, 2 FKs)
Cadastro de clientes.

**Colunas principais:**
- `id_Cliente` (smallint) - PK
- `NomeCliente`, `FantasiaCliente`
- `CNPJ`, `IE`, `IM`
- `DataContrato`
- `TaxaADM` (numeric)
- `Endereco`, `CEP`
- `Contato`, `email`
- `Fone1`, `Fone2`, `Fax`
- `PessoaJuridica`
- `ObservacaoCliente`, `ObsDaNota`

**Foreign Keys:**
- `id_Bairro` → Bairro(id_Bairro)
- `id_Cidade` → Cidade(id_Cidade)

---

### 5. **NF (Nota Fiscal)** (20 colunas, 1 FK)
Notas fiscais emitidas.

**Colunas principais:**
- `id_Empresa`, `NF` - PK Composta
- `id_NotaFiscal` (int IDENTITY)
- `id_cliente` (smallint)
- `Emissao`, `Vencimento`, `Baixa` (datetime)
- `Total`, `ValorQuitado` (money)
- `IRRF`, `PIS`, `Cofins`, `CSLL`, `ISS` (money)
- `OutrasDespesas` (money)
- `Cancelada` (bit)
- `Observacao`, `ObservacaoBaixa`
- `NovaNota`, `NotaAntiga`

**Foreign Keys:**
- `id_Bairro` → Bairro(id_Bairro) *(parece incorreto)*

---

## Tabelas de Apoio

### 6. **Banco** (2 colunas)
- `id_Banco` (char) - PK
- `NomeBanco` (nvarchar)

### 7. **Bairro** (2 colunas)
- `id_Bairro` (smallint) - PK
- `NomeBairro` (nvarchar)

### 8. **Cidade** (5 colunas)
- `id_Cidade` (smallint) - PK
- `NomeCidade` (nvarchar)
- `UF`, `DDD` (char)
- `ISS` (numeric)

### 9. **Conselho** (3 colunas)
Conselhos profissionais (ex: CRM, CRO, etc).
- `id_Conselho` (smallint) - PK
- `NomeConselho`, `Sigla`

### 10. **Especialidade** (2 colunas)
- `id_Especialidade` (smallint) - PK
- `NomeEspecialidade` (nvarchar)

### 11. **EstadoCivil** (2 colunas)
- Código e descrição

### 12. **TipoServico** (2 colunas)
- Tipos de serviços prestados

### 13. **Operadora** (2 colunas)
- `id_Operadora`, `NomeOperadora`

---

## Tabelas de Relacionamento

### 14. **EmpresaSocio** (10 colunas, 4 FKs)
Relaciona empresas com sócios.

**Foreign Keys:**
- `id_Bairro` → Bairro(id_Bairro)
- `id_Empresa` → Empresa(id_Empresa)
- `id_Pessoa` → Pessoa(id_Pessoa)
- `id_Especialidade` → Especialidade(id_Especialidade)

### 15. **EmpresaCliente** (9 colunas, 2 FKs)
Relaciona empresas com clientes e suas taxas específicas.

**Colunas:**
- `id_Empresa`, `id_Cliente` (PK composta)
- `Taxa`, `TaxaISS`, `TaxaCOFINS`, `TaxaPIS`, `TaxaCSLL`, `TaxaIRRF`
- `Processo`

**Foreign Keys:**
- `id_Cliente` → Cliente(id_Cliente)
- `id_Empresa` → Empresa(id_Empresa)

### 16. **MedicoEspecialidade** (2 colunas, 3 FKs)
Relaciona pessoas com especialidades.

**Foreign Keys:**
- `id_Especialidade` → Especialidade(id_Especialidade)
- `id_Pessoa` → Pessoa(id_Pessoa)
- `id_Bairro` → Bairro(id_Bairro) *(parece incorreto)*

### 17. **PessoaCartorio** (2 colunas, 2 FKs)
Relaciona pessoas com cartórios.

**Foreign Keys:**
- `id_Cartorio` → Cartorio(id_Cartorio)
- `id_Pessoa` → Pessoa(id_Pessoa)

---

## Tabelas Financeiras

### 18. **ContasPagarReceber** (14 colunas)
Controle de contas a pagar e receber.

**Colunas principais:**
- `id_ContasPagarReceber` (int IDENTITY) - PK
- `DataLancamento`, `DataVencimento`, `DataBaixa`
- `ValorOriginal`, `ValorParcela`, `ValorBaixado`
- `id_Empresa`, `id_Pessoa`, `id_DespesaReceita`
- `MesAnoReferencia`, `Historico`
- `id_NotaFiscal`, `id_Lancamento`

### 19. **DespesaReceita** (10 colunas)
Tipos de despesas e receitas.

**Colunas principais:**
- `id_DespesaReceita` (smallint) - PK
- `SiglaDespesaReceita`, `NomeDespesaReceita`
- `Despesa`, `TemRateio`, `Inativa`
- `Valor`, `Parcelas`, `Periodicidade`, `ParcelaUnica`

### 20. **EmpresaDespesaFixa** (6 colunas)
Despesas fixas por empresa.

**Colunas:**
- `id_Empresa`, `id_DespesaReceita` (PK composta)
- `DataLancamento`, `Parcelas`, `ValorEmpresa`, `Inativa`

### 21. **Adiantamento** (13 colunas)
Adiantamentos financeiros.

### 22. **ImpostoDeRenda** (6 colunas)
Tabela de faixas de IR.

**Colunas:**
- `Data`, `de`, `ate` (datetime/money)
- `aliquota`, `valordeduzir`, `DeducaoDependente`

### 23. **PagamentoNaoSocio** (10 colunas)
Pagamentos para não sócios.

---

## Tabelas de Retenção e Repasse

### 24. **PlanoRetencao** (4 colunas)
Planos de retenção.

**Colunas:**
- `id_PlanoRetencao` (smallint) - PK
- `NomePlanoRetencao`
- `Inativo`, `LiberaDespesas`

### 25. **Retencao** (3 colunas)
Faixas de retenção por plano.

**Colunas:**
- `id_PlanoRetencao`, `Ate`, `Reter`

### 26. **RepasseAnual** (27 colunas)
Repasses mensais por ano.

**Colunas:**
- `Ano`, `id_Empresa`, `id_Pessoa` (PK composta)
- `JanBruto`, `JanTaxa`, `FevBruto`, `FevTaxa`, ... (12 meses)

---

## Tabelas de Controle

### 27. **Usuario** (7 colunas)
Usuários do sistema.

**Colunas:**
- `id_Usuario`, `UsuarioNome`, `UsuarioLogin`, `UsuarioSenha`
- `UsuarioNivelAcesso`, `UsuarioInativo`, `UsuarioDataInicio`

### 28. **ParametroEmail** (5 colunas)
Configurações de email.

**Colunas:**
- `id_Parametro`, `AssuntoEmail`, `SMTP`, `ContaEmail`, `SenhaEmail`

### 29. **ParametroNF** (6 colunas)
Parâmetros de nota fiscal.

---

## Outras Tabelas

### 30. **Cartorio** (6 colunas)
Cartórios registrados.

### 31. **CBO** (2 colunas)
Código Brasileiro de Ocupação.

### 32. **ClienteFilial** (3 colunas)
Filiais de clientes.

### 33. **ClienteContato** (4 colunas)
Contatos de clientes.

### 34. **TipoContato** (2 colunas)
Tipos de contato.

### 35. **AnuidadeCremeb** (6 colunas)
Anuidades do CREMEB.

### 36. **AnuidadeCremebItem** (7 colunas)
Itens de anuidades.

### 37. **Indicacao** (8 colunas)
Sistema de indicações.

### 38. **PessoaContaCorrente** (11 colunas, 1 FK)
Contas correntes de pessoas.

**Foreign Keys:**
- `id_Bairro` → Bairro(id_Bairro)

### 39. **PessoaContaRecebimento** (3 colunas)
Contas de recebimento por pessoa.

---

## Diagrama de Relacionamentos (Principais)

```
Pessoa ←──── Lancamento ────→ Empresa
   ↓                              ↓
Bairro                         Bairro
Cidade                         Cidade
Cartorio                       Banco
   ↓                              ↓
PessoaCartorio              EmpresaSocio
                                  ↓
Cliente ←──── NF ────→ Empresa
   ↓
Bairro
Cidade
   ↓
EmpresaCliente → Empresa

MedicoEspecialidade → Pessoa
                    → Especialidade
```

---

## Observações

1. **Foreign Keys Incompletas**: Algumas tabelas têm relacionamentos lógicos sem FKs explícitas (ex: Lancamento não tem FKs para Empresa, Cliente, Pessoa).

2. **FKs Incorretas**: Algumas FKs parecem estar incorretas (ex: NF.id_Bairro → Bairro).

3. **Campos de Auditoria**: Muitas tabelas têm campos de data (DataCadastro, DataAlteracao, DataInativo, etc).

4. **Sistema de Retenção**: O sistema possui um mecanismo complexo de retenção com PlanoRetencao e faixas progressivas.

5. **Multi-empresa**: O sistema suporta múltiplas empresas e clientes com taxas específicas por relacionamento.

---

## Arquivos Gerados

- **c:\java\workspace\Gerador\tables_extracted.txt** - Listagem completa formatada
- **c:\java\workspace\Gerador\tables_structure.json** - Estrutura em JSON
- **c:\java\workspace\Gerador\RESUMO_BANCO_DADOS.md** - Este resumo
