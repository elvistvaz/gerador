# Plano de Ação: Correção de Foreign Keys nos CSVs

## Objetivo
Adicionar as colunas de foreign key necessárias nos CSVs de carga inicial para estabelecer relacionamentos corretos entre entidades.

---

## 1. CSV: cartorio.csv

**Localização:** `c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\cartorio.csv`

### Estrutura Atual
```
id_Cartorio;NomeCartorio;Endereco;Telefone
```

### Estrutura Necessária
```
id_Cartorio;NomeCartorio;Endereco;id_Bairro;id_Cidade;Telefone
```

### Colunas a Adicionar
1. **id_Bairro** (smallint, nullable)
   - Referência: Bairro.id_Bairro
   - Exemplo: `1` (Centro), `2` (Pituba), etc.

2. **id_Cidade** (smallint, nullable)
   - Referência: Cidade.id_Cidade
   - Exemplo: `1` (Salvador), `2` (Feira de Santana), etc.

### Script SQL para Adicionar Colunas
```sql
ALTER TABLE Cartorio ADD id_Bairro SMALLINT NULL;
ALTER TABLE Cartorio ADD id_Cidade SMALLINT NULL;
ALTER TABLE Cartorio ADD CONSTRAINT FK_Cartorio_Bairro FOREIGN KEY (id_Bairro) REFERENCES Bairro(id_Bairro);
ALTER TABLE Cartorio ADD CONSTRAINT FK_Cartorio_Cidade FOREIGN KEY (id_Cidade) REFERENCES Cidade(id_Cidade);
CREATE INDEX IX_Cartorio_Bairro ON Cartorio(id_Bairro);
CREATE INDEX IX_Cartorio_Cidade ON Cartorio(id_Cidade);
```

---

## 2. CSV: pessoa.csv

**Localização:** `c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\pessoa.csv`

### Estrutura Atual (38 colunas)
```
id_Pessoa;Nome;CPF;RG;OrgaoEmissor;Nascimento;Pai;Mae;Nacionalidade;NumeroDependente;Endereco;CEP;Telefone;Celular;Email;NumeroConselho;DataFiliacao;InscricaoISS;InscricaoINSS;ClasseInss;Agencia;Conta;DvConta;ContaPoupanca;DataAdesao;Percentual;Taxaespecial;Consulta;Procedimento;NumeroParcelasCota;ValorDeCadaCota;DataIndicacao;DataDesligamento;ValorPagoNoDesligamento;DataInativo;ObservacaoPessoa;Atualizacao;DataAlteracao
```

### Colunas a Adicionar (9 Foreign Keys)

1. **id_EstadoCivil** (smallint, nullable)
   - Referência: EstadoCivil.id_EstadoCivil
   - Adicionar após: Nascimento
   - Valores: 1=Solteiro, 2=Casado, 3=Divorciado, 4=Viuvo

2. **id_Naturalidade** (smallint, nullable)
   - Referência: Cidade.id_Cidade
   - Adicionar após: Mae
   - Valores: IDs de cidades

3. **id_Bairro** (smallint, nullable)
   - Referência: Bairro.id_Bairro
   - Adicionar após: Endereco
   - Valores: IDs de bairros

4. **id_Cidade** (smallint, nullable)
   - Referência: Cidade.id_Cidade
   - Adicionar após: id_Bairro
   - Valores: IDs de cidades

5. **id_Conselho** (smallint, nullable)
   - Referência: Conselho.id_Conselho
   - Adicionar antes de: NumeroConselho
   - Valores: 1=CREMEB, 2=COREN, etc.

6. **id_Operadora** (smallint, nullable)
   - Referência: Operadora.id_Operadora
   - Adicionar após: ClasseInss
   - Valores: IDs de operadoras

7. **id_Banco** (char(3), nullable)
   - Referência: Banco.id_Banco
   - Adicionar antes de: Agencia
   - Valores: "001"=BB, "033"=Santander, etc.

8. **id_PlanoRetencao** (smallint, nullable)
   - Referência: PlanoRetencao.id_PlanoRetencao
   - Adicionar após: DataAdesao
   - Valores: IDs de planos

9. **id_Indicacao** (smallint, nullable)
   - Referência: Indicacao.id_Indicacao
   - Adicionar antes de: DataIndicacao
   - Valores: IDs de indicações

### Script SQL
```sql
ALTER TABLE Pessoa ADD id_EstadoCivil SMALLINT NULL;
ALTER TABLE Pessoa ADD id_Naturalidade SMALLINT NULL;
ALTER TABLE Pessoa ADD id_Bairro SMALLINT NULL;
ALTER TABLE Pessoa ADD id_Cidade SMALLINT NULL;
ALTER TABLE Pessoa ADD id_Conselho SMALLINT NULL;
ALTER TABLE Pessoa ADD id_Operadora SMALLINT NULL;
ALTER TABLE Pessoa ADD id_Banco CHAR(3) NULL;
ALTER TABLE Pessoa ADD id_PlanoRetencao SMALLINT NULL;
ALTER TABLE Pessoa ADD id_Indicacao SMALLINT NULL;

-- Foreign Keys
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_EstadoCivil FOREIGN KEY (id_EstadoCivil) REFERENCES EstadoCivil(id_EstadoCivil);
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_Naturalidade FOREIGN KEY (id_Naturalidade) REFERENCES Cidade(id_Cidade);
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_Bairro FOREIGN KEY (id_Bairro) REFERENCES Bairro(id_Bairro);
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_Cidade FOREIGN KEY (id_Cidade) REFERENCES Cidade(id_Cidade);
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_Conselho FOREIGN KEY (id_Conselho) REFERENCES Conselho(id_Conselho);
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_Operadora FOREIGN KEY (id_Operadora) REFERENCES Operadora(id_Operadora);
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_Banco FOREIGN KEY (id_Banco) REFERENCES Banco(id_Banco);
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_PlanoRetencao FOREIGN KEY (id_PlanoRetencao) REFERENCES PlanoRetencao(id_PlanoRetencao);
ALTER TABLE Pessoa ADD CONSTRAINT FK_Pessoa_Indicacao FOREIGN KEY (id_Indicacao) REFERENCES Indicacao(id_Indicacao);

-- Indexes
CREATE INDEX IX_Pessoa_EstadoCivil ON Pessoa(id_EstadoCivil);
CREATE INDEX IX_Pessoa_Bairro ON Pessoa(id_Bairro);
CREATE INDEX IX_Pessoa_Cidade ON Pessoa(id_Cidade);
CREATE INDEX IX_Pessoa_Conselho ON Pessoa(id_Conselho);
CREATE INDEX IX_Pessoa_Operadora ON Pessoa(id_Operadora);
CREATE INDEX IX_Pessoa_Banco ON Pessoa(id_Banco);
CREATE INDEX IX_Pessoa_PlanoRetencao ON Pessoa(id_PlanoRetencao);
```

---

## 3. CSV: cliente.csv

**Localização:** `c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\cliente.csv`

### Estrutura Atual
```
id_Cliente;NomeCliente;FantasiaCliente;CNPJ;IE;IM;DataContrato;TaxaADM;Endereco;CEP;Contato;Fone1;Fone2;email;ObservacaoCliente;ObsDaNota;PessoaJuridica
```

### Colunas a Adicionar

1. **id_Bairro** (smallint, nullable)
   - Referência: Bairro.id_Bairro
   - Adicionar após: Endereco

2. **id_Cidade** (smallint, nullable)
   - Referência: Cidade.id_Cidade
   - Adicionar após: id_Bairro

### Script SQL
```sql
ALTER TABLE Cliente ADD id_Bairro SMALLINT NULL;
ALTER TABLE Cliente ADD id_Cidade SMALLINT NULL;
ALTER TABLE Cliente ADD CONSTRAINT FK_Cliente_Bairro FOREIGN KEY (id_Bairro) REFERENCES Bairro(id_Bairro);
ALTER TABLE Cliente ADD CONSTRAINT FK_Cliente_Cidade FOREIGN KEY (id_Cidade) REFERENCES Cidade(id_Cidade);
CREATE INDEX IX_Cliente_Bairro ON Cliente(id_Bairro);
CREATE INDEX IX_Cliente_Cidade ON Cliente(id_Cidade);
```

---

## 4. CSV: lancamento.csv

**Localização:** `c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\lancamento.csv`

### Estrutura Atual
```
id_lancamento;Data;NF;ValorBruto;Despesas;Retencao;ValorLiquido;ValorTaxa;ValorRepasse;Taxa;MesAno;Baixa
```

### Colunas a Adicionar (5 Foreign Keys OBRIGATÓRIAS)

1. **id_Empresa** (int, NOT NULL)
   - Referência: Empresa.id_Empresa
   - Adicionar após: Data
   - **OBRIGATÓRIO**

2. **id_Cliente** (int, NOT NULL)
   - Referência: Cliente.id_Cliente
   - Adicionar após: id_Empresa
   - **OBRIGATÓRIO**

3. **id_Pessoa** (int, NOT NULL)
   - Referência: Pessoa.id_Pessoa
   - Adicionar após: id_Cliente
   - **OBRIGATÓRIO**

4. **id_TipoServico** (smallint, nullable)
   - Referência: TipoServico.id_TipoServico
   - Adicionar após: id_Pessoa

5. **id_ClienteFilial** (int, nullable)
   - Referência: ClienteFilial.id_ClienteFilial
   - Adicionar após: id_TipoServico

### Script SQL
```sql
ALTER TABLE Lancamento ADD id_Empresa INT NOT NULL;
ALTER TABLE Lancamento ADD id_Cliente INT NOT NULL;
ALTER TABLE Lancamento ADD id_Pessoa INT NOT NULL;
ALTER TABLE Lancamento ADD id_TipoServico SMALLINT NULL;
ALTER TABLE Lancamento ADD id_ClienteFilial INT NULL;

ALTER TABLE Lancamento ADD CONSTRAINT FK_Lancamento_Empresa FOREIGN KEY (id_Empresa) REFERENCES Empresa(id_Empresa);
ALTER TABLE Lancamento ADD CONSTRAINT FK_Lancamento_Cliente FOREIGN KEY (id_Cliente) REFERENCES Cliente(id_Cliente);
ALTER TABLE Lancamento ADD CONSTRAINT FK_Lancamento_Pessoa FOREIGN KEY (id_Pessoa) REFERENCES Pessoa(id_Pessoa);
ALTER TABLE Lancamento ADD CONSTRAINT FK_Lancamento_TipoServico FOREIGN KEY (id_TipoServico) REFERENCES TipoServico(id_TipoServico);
ALTER TABLE Lancamento ADD CONSTRAINT FK_Lancamento_ClienteFilial FOREIGN KEY (id_ClienteFilial) REFERENCES ClienteFilial(id_ClienteFilial);

CREATE INDEX IX_Lancamento_Empresa ON Lancamento(id_Empresa);
CREATE INDEX IX_Lancamento_Cliente ON Lancamento(id_Cliente);
CREATE INDEX IX_Lancamento_Pessoa ON Lancamento(id_Pessoa);
CREATE INDEX IX_Lancamento_TipoServico ON Lancamento(id_TipoServico);
CREATE INDEX IX_Lancamento_ClienteFilial ON Lancamento(id_ClienteFilial);
```

---

## 5. CSV: nf.csv

**Localização:** `c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\nf.csv`

### Estrutura Atual
```
id_Empresa;NF;Emissao;Vencimento;Total;IRRF;PIS;Cofins;CSLL;ISS;Baixa;ValorQuitado;Cancelada;Observacao;ObservacaoBaixa;OutrasDespesas
```

### Colunas a Adicionar

1. **id_Cliente** (int, NOT NULL)
   - Referência: Cliente.id_Cliente
   - Adicionar após: NF
   - **OBRIGATÓRIO**

### Nota sobre Estrutura
A entidade NF tem chave primária composta (id_Empresa, NF). Adicionar id_Cliente após NF.

### Script SQL
```sql
ALTER TABLE NF ADD id_Cliente INT NOT NULL;
ALTER TABLE NF ADD CONSTRAINT FK_NF_Cliente FOREIGN KEY (id_Cliente) REFERENCES Cliente(id_Cliente);
CREATE INDEX IX_NF_Cliente ON NF(id_Cliente);
```

---

## 6. CSV: adiantamento.csv

**Localização:** `c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial\adiantamento.csv`

### Estrutura Atual
```
id_Adiantamento;Data;NF;ValorBruto;Retencao;ValorLiquido;ValorTaxa;ValorRepasse;Pago
```

### Colunas a Adicionar (4 Foreign Keys OBRIGATÓRIAS)

1. **id_Empresa** (int, NOT NULL)
   - Referência: Empresa.id_Empresa
   - Adicionar após: Data
   - **OBRIGATÓRIO**

2. **id_Pessoa** (int, NOT NULL)
   - Referência: Pessoa.id_Pessoa
   - Adicionar após: id_Empresa
   - **OBRIGATÓRIO**

3. **id_Cliente** (int, NOT NULL)
   - Referência: Cliente.id_Cliente
   - Adicionar após: id_Pessoa
   - **OBRIGATÓRIO**

4. **id_Lancamento** (int, NOT NULL)
   - Referência: Lancamento.id_Lancamento
   - Adicionar após: ValorRepasse
   - **OBRIGATÓRIO**

### Script SQL
```sql
ALTER TABLE Adiantamento ADD id_Empresa INT NOT NULL;
ALTER TABLE Adiantamento ADD id_Pessoa INT NOT NULL;
ALTER TABLE Adiantamento ADD id_Cliente INT NOT NULL;
ALTER TABLE Adiantamento ADD id_Lancamento INT NOT NULL;

ALTER TABLE Adiantamento ADD CONSTRAINT FK_Adiantamento_Empresa FOREIGN KEY (id_Empresa) REFERENCES Empresa(id_Empresa);
ALTER TABLE Adiantamento ADD CONSTRAINT FK_Adiantamento_Pessoa FOREIGN KEY (id_Pessoa) REFERENCES Pessoa(id_Pessoa);
ALTER TABLE Adiantamento ADD CONSTRAINT FK_Adiantamento_Cliente FOREIGN KEY (id_Cliente) REFERENCES Cliente(id_Cliente);
ALTER TABLE Adiantamento ADD CONSTRAINT FK_Adiantamento_Lancamento FOREIGN KEY (id_Lancamento) REFERENCES Lancamento(id_Lancamento);

CREATE INDEX IX_Adiantamento_Empresa ON Adiantamento(id_Empresa);
CREATE INDEX IX_Adiantamento_Pessoa ON Adiantamento(id_Pessoa);
CREATE INDEX IX_Adiantamento_Cliente ON Adiantamento(id_Cliente);
CREATE INDEX IX_Adiantamento_Lancamento ON Adiantamento(id_Lancamento);
```

---

## Resumo de Ações

### Total de Foreign Keys a Adicionar: 24

| CSV | FKs a Adicionar | Obrigatórias | Opcionais |
|-----|-----------------|--------------|-----------|
| cartorio.csv | 2 | 0 | 2 |
| pessoa.csv | 9 | 0 | 9 |
| cliente.csv | 2 | 0 | 2 |
| lancamento.csv | 5 | 3 | 2 |
| nf.csv | 1 | 1 | 0 |
| adiantamento.csv | 4 | 4 | 0 |
| **TOTAL** | **23** | **8** | **15** |

---

## Ordem de Execução Recomendada

Devido a dependências entre tabelas, execute as alterações nesta ordem:

1. **Tabelas de Lookup** (se necessário criar):
   - EstadoCivil
   - Bairro
   - Cidade
   - Conselho
   - Operadora
   - Banco
   - PlanoRetencao
   - Indicacao
   - TipoServico

2. **Tabelas Principais**:
   - Cartorio
   - Pessoa
   - Cliente

3. **Tabelas Transacionais**:
   - Lancamento (depende de Empresa, Cliente, Pessoa)
   - NF (depende de Empresa, Cliente)
   - Adiantamento (depende de Empresa, Pessoa, Cliente, Lancamento)

---

## Considerações Importantes

### 1. Dados Existentes
Se já existem dados nos CSVs, será necessário:
- Adicionar valores padrão ou NULL para as novas colunas
- Executar UPDATE para popular as FKs com valores válidos
- Validar referências antes de criar constraints

### 2. Integridade Referencial
Antes de adicionar as constraints de FK, certifique-se que:
- As tabelas referenciadas existem
- Os valores nas colunas de FK são válidos
- Não há valores órfãos

### 3. Performance
Após adicionar as colunas e constraints:
- Criar índices nas colunas de FK (já incluídos nos scripts)
- Atualizar estatísticas do banco
- Considerar impacto em queries existentes

---

## Validação Pós-Correção

Após aplicar as correções, execute:

```sql
-- Verificar todas as FKs criadas
SELECT
    OBJECT_NAME(f.parent_object_id) AS TableName,
    COL_NAME(fc.parent_object_id, fc.parent_column_id) AS ColumnName,
    OBJECT_NAME(f.referenced_object_id) AS ReferencedTableName,
    COL_NAME(fc.referenced_object_id, fc.referenced_column_id) AS ReferencedColumnName
FROM sys.foreign_keys AS f
INNER JOIN sys.foreign_key_columns AS fc
    ON f.object_id = fc.constraint_object_id
WHERE OBJECT_NAME(f.parent_object_id) IN ('Cartorio', 'Pessoa', 'Cliente', 'Lancamento', 'NF', 'Adiantamento')
ORDER BY TableName, ColumnName;

-- Verificar registros órfãos
-- (executar para cada FK adicionada)
```

E reexecutar o script de análise:
```bash
node analyze_xandel_full.js > relatorio_pos_correcao.txt
```
