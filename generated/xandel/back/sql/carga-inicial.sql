-- ============================================================
-- CARGA INICIAL - XANDEL
-- Gerado automaticamente a partir dos arquivos CSV
-- Para SQL Server
-- ============================================================

SET IDENTITY_INSERT dbo.Banco ON;
-- Banco
-- Cadastro de bancos
INSERT INTO dbo.Banco (id_banco, nome_banco) VALUES (N'001', N'Banco do Brasil');
INSERT INTO dbo.Banco (id_banco, nome_banco) VALUES (N'033', N'Santander');
INSERT INTO dbo.Banco (id_banco, nome_banco) VALUES (N'104', N'Caixa Economica Federal');
INSERT INTO dbo.Banco (id_banco, nome_banco) VALUES (N'237', N'Bradesco');
INSERT INTO dbo.Banco (id_banco, nome_banco) VALUES (N'341', N'Itau Unibanco');
INSERT INTO dbo.Banco (id_banco, nome_banco) VALUES (N'756', N'Sicoob');

SET IDENTITY_INSERT dbo.Banco OFF;

SET IDENTITY_INSERT dbo.Bairro ON;
-- Bairro
-- Cadastro de bairros
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (1, N'Centro');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (2, N'Pituba');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (3, N'Barra');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (4, N'Ondina');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (5, N'Itaigara');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (6, N'Caminho das Arvores');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (7, N'Paralela');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (8, N'Brotas');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (9, N'Federacao');
INSERT INTO dbo.Bairro (id_bairro, nome_bairro) VALUES (10, N'Rio Vermelho');

SET IDENTITY_INSERT dbo.Bairro OFF;

SET IDENTITY_INSERT dbo.Cidade ON;
-- Cidade
-- Cadastro de cidades
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (1, N'Salvador', N'BA', N'71', 5.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (2, N'Feira de Santana', N'BA', N'75', 3.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (3, N'Vitoria da Conquista', N'BA', N'77', 3.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (4, N'Camacari', N'BA', N'71', 3.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (5, N'Juazeiro', N'BA', N'74', 3.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (6, N'Lauro de Freitas', N'BA', N'71', 5.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (7, N'Ilheus', N'BA', N'73', 3.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (8, N'Itabuna', N'BA', N'73', 3.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (9, N'Teixeira de Freitas', N'BA', N'73', 3.00);
INSERT INTO dbo.Cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (10, N'Barreiras', N'BA', N'77', 3.00);

SET IDENTITY_INSERT dbo.Cidade OFF;

SET IDENTITY_INSERT dbo.Conselho ON;
-- Conselho
-- Cadastro de conselhos profissionais
INSERT INTO dbo.Conselho (id_conselho, nome_conselho, sigla) VALUES (1, N'Conselho Regional de Medicina da Bahia', N'CREMEB');
INSERT INTO dbo.Conselho (id_conselho, nome_conselho, sigla) VALUES (2, N'Conselho Regional de Enfermagem', N'COREN');
INSERT INTO dbo.Conselho (id_conselho, nome_conselho, sigla) VALUES (3, N'Conselho Regional de Odontologia', N'CRO');
INSERT INTO dbo.Conselho (id_conselho, nome_conselho, sigla) VALUES (4, N'Conselho Regional de Psicologia', N'CRP');
INSERT INTO dbo.Conselho (id_conselho, nome_conselho, sigla) VALUES (5, N'Conselho Regional de Fisioterapia', N'CREFITO');

SET IDENTITY_INSERT dbo.Conselho OFF;

SET IDENTITY_INSERT dbo.Especialidade ON;
-- Especialidade
-- Cadastro de especialidades médicas
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (1, N'Clinica Medica');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (2, N'Cardiologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (3, N'Dermatologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (4, N'Ginecologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (5, N'Ortopedia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (6, N'Pediatria');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (7, N'Neurologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (8, N'Oftalmologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (9, N'Otorrinolaringologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (10, N'Urologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (11, N'Psiquiatria');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (12, N'Cirurgia Geral');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (13, N'Anestesiologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (14, N'Radiologia');
INSERT INTO dbo.Especialidade (id_especialidade, nome_especialidade) VALUES (15, N'Endocrinologia');

SET IDENTITY_INSERT dbo.Especialidade OFF;

SET IDENTITY_INSERT dbo.EstadoCivil ON;
-- EstadoCivil
-- Cadastro de estados civis
INSERT INTO dbo.EstadoCivil (id_estado_civil, nome_estado_civil) VALUES (1, N'Solteiro(a)');
INSERT INTO dbo.EstadoCivil (id_estado_civil, nome_estado_civil) VALUES (2, N'Casado(a)');
INSERT INTO dbo.EstadoCivil (id_estado_civil, nome_estado_civil) VALUES (3, N'Divorciado(a)');
INSERT INTO dbo.EstadoCivil (id_estado_civil, nome_estado_civil) VALUES (4, N'Viuvo(a)');
INSERT INTO dbo.EstadoCivil (id_estado_civil, nome_estado_civil) VALUES (5, N'Separado(a)');
INSERT INTO dbo.EstadoCivil (id_estado_civil, nome_estado_civil) VALUES (6, N'Uniao Estavel');

SET IDENTITY_INSERT dbo.EstadoCivil OFF;

SET IDENTITY_INSERT dbo.Operadora ON;
-- Operadora
-- Cadastro de operadoras
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (1, N'Unimed');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (2, N'Bradesco Saude');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (3, N'SulAmerica');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (4, N'Amil');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (5, N'Hapvida');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (6, N'NotreDame Intermedica');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (7, N'Cassi');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (8, N'Geap');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (9, N'Postal Saude');
INSERT INTO dbo.Operadora (id_operadora, nome_operadora) VALUES (10, N'Petrobras');

SET IDENTITY_INSERT dbo.Operadora OFF;

SET IDENTITY_INSERT dbo.TipoServico ON;
-- TipoServico
-- Cadastro de tipos de serviço
INSERT INTO dbo.TipoServico (id_tipo_servico, nome_tipo_servico) VALUES (1, N'Consulta');
INSERT INTO dbo.TipoServico (id_tipo_servico, nome_tipo_servico) VALUES (2, N'Exame');
INSERT INTO dbo.TipoServico (id_tipo_servico, nome_tipo_servico) VALUES (3, N'Procedimento');
INSERT INTO dbo.TipoServico (id_tipo_servico, nome_tipo_servico) VALUES (4, N'Cirurgia');
INSERT INTO dbo.TipoServico (id_tipo_servico, nome_tipo_servico) VALUES (5, N'Internacao');
INSERT INTO dbo.TipoServico (id_tipo_servico, nome_tipo_servico) VALUES (6, N'Urgencia');
INSERT INTO dbo.TipoServico (id_tipo_servico, nome_tipo_servico) VALUES (7, N'Emergencia');
INSERT INTO dbo.TipoServico (id_tipo_servico, nome_tipo_servico) VALUES (8, N'Homecare');

SET IDENTITY_INSERT dbo.TipoServico OFF;

SET IDENTITY_INSERT dbo.TipoContato ON;
-- TipoContato
-- Cadastro de tipos de contato
INSERT INTO dbo.TipoContato (id_tipo_contato, nome_tipo_contato) VALUES (1, N'Telefone Fixo');
INSERT INTO dbo.TipoContato (id_tipo_contato, nome_tipo_contato) VALUES (2, N'Celular');
INSERT INTO dbo.TipoContato (id_tipo_contato, nome_tipo_contato) VALUES (3, N'Email');
INSERT INTO dbo.TipoContato (id_tipo_contato, nome_tipo_contato) VALUES (4, N'WhatsApp');
INSERT INTO dbo.TipoContato (id_tipo_contato, nome_tipo_contato) VALUES (5, N'Fax');
INSERT INTO dbo.TipoContato (id_tipo_contato, nome_tipo_contato) VALUES (6, N'Recado');

SET IDENTITY_INSERT dbo.TipoContato OFF;

SET IDENTITY_INSERT dbo.CBO ON;
-- CBO
-- Cadastro Brasileiro de Ocupações
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225125', N'Medico clinico');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225120', N'Medico cardiologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225106', N'Medico cirurgiao geral');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225110', N'Medico dermatologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225109', N'Medico gastroenterologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225130', N'Medico ginecologista e obstetra');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225133', N'Medico neurologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225170', N'Medico ortopedista e traumatologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225124', N'Medico pediatra');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225175', N'Medico psiquiatra');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225180', N'Medico urologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225103', N'Medico anestesiologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225185', N'Medico radiologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225112', N'Medico endocrinologista');
INSERT INTO dbo.CBO (id_CBO, NomeCBO) VALUES (N'225145', N'Medico oftalmologista');

SET IDENTITY_INSERT dbo.CBO OFF;

SET IDENTITY_INSERT dbo.Usuario ON;
-- Usuario
-- Cadastro de usuários do sistema
INSERT INTO dbo.Usuario (id_usuario, usuario_nome, usuario_login, usuario_senha, usuario_nivel_acesso, usuario_inativo) VALUES (1, N'Administrador', N'admin', N'admin', 1, 0);
INSERT INTO dbo.Usuario (id_usuario, usuario_nome, usuario_login, usuario_senha, usuario_nivel_acesso, usuario_inativo) VALUES (2, N'Usuario Padrao', N'user', N'user', 2, 0);

SET IDENTITY_INSERT dbo.Usuario OFF;

SET IDENTITY_INSERT dbo.ParametroEmail ON;
-- ParametroEmail
-- Parâmetros de configuração de e-mail
INSERT INTO dbo.ParametroEmail (id_Parametro, AssuntoEmail, SMTP, ContaEmail, SenhaEmail) VALUES (1, N'user1@example.com', N'Valor 1', N'user1@example.com', N'user1@example.com');

SET IDENTITY_INSERT dbo.ParametroEmail OFF;

SET IDENTITY_INSERT dbo.ImpostoDeRenda ON;
-- ImpostoDeRenda
-- Tabela de Imposto de Renda
INSERT INTO dbo.ImpostoDeRenda (Data, de, ate, aliquota, valordeduzir, DeducaoDependente) VALUES ('2025-01-01', N'0.00', N'2259.20', 0.00, N'0.00', N'189.59');
INSERT INTO dbo.ImpostoDeRenda (Data, de, ate, aliquota, valordeduzir, DeducaoDependente) VALUES ('2025-01-01', N'2259.21', N'2826.65', 7.50, N'169.44', N'189.59');
INSERT INTO dbo.ImpostoDeRenda (Data, de, ate, aliquota, valordeduzir, DeducaoDependente) VALUES ('2025-01-01', N'2826.66', N'3751.05', 15.00, N'381.44', N'189.59');
INSERT INTO dbo.ImpostoDeRenda (Data, de, ate, aliquota, valordeduzir, DeducaoDependente) VALUES ('2025-01-01', N'3751.06', N'4664.68', 22.50, N'662.77', N'189.59');
INSERT INTO dbo.ImpostoDeRenda (Data, de, ate, aliquota, valordeduzir, DeducaoDependente) VALUES ('2025-01-01', N'4664.69', N'999999.99', 27.50, N'896.00', N'189.59');

SET IDENTITY_INSERT dbo.ImpostoDeRenda OFF;

SET IDENTITY_INSERT dbo.ParametroNF ON;
-- ParametroNF
-- Parâmetros de Nota Fiscal
INSERT INTO dbo.ParametroNF (Cofins, Pis, CSLL, IRRF, TetoImposto, BasePisCofinsCsll) VALUES (465.29, 798.76, 404.24, 795.89, 756.26, NULL);

SET IDENTITY_INSERT dbo.ParametroNF OFF;

SET IDENTITY_INSERT dbo.Indicacao ON;
-- Indicacao
-- Programas de indicação
INSERT INTO dbo.Indicacao (id_Indicacao, de, ate, IndicacaoMinima, IndicacaoMaxima, Indice, TetoIndice, GrupoIndicados) VALUES (1, '2025-01-01', '2025-12-31', 1, 5, 5.00, 1000.00, 10);
INSERT INTO dbo.Indicacao (id_Indicacao, de, ate, IndicacaoMinima, IndicacaoMaxima, Indice, TetoIndice, GrupoIndicados) VALUES (2, '2025-01-01', '2025-12-31', 6, 10, 7.50, 2000.00, 20);
INSERT INTO dbo.Indicacao (id_Indicacao, de, ate, IndicacaoMinima, IndicacaoMaxima, Indice, TetoIndice, GrupoIndicados) VALUES (3, '2025-01-01', '2025-12-31', 11, 20, 10.00, 3000.00, 30);

SET IDENTITY_INSERT dbo.Indicacao OFF;

SET IDENTITY_INSERT dbo.Empresa ON;
-- Empresa
-- Cadastro de empresas
INSERT INTO dbo.Empresa (id_Empresa, NomeEmpresa, FantasiaEmpresa, CNPJ, TaxaRetencao, Inativa) VALUES (1, N'Sociedade Medica Alpha Ltda', N'Alpha Med', N'12345678000100', 10.00, 0);
INSERT INTO dbo.Empresa (id_Empresa, NomeEmpresa, FantasiaEmpresa, CNPJ, TaxaRetencao, Inativa) VALUES (2, N'Clinica Beta Servicos Medicos', N'Beta Clinica', N'98765432000199', 8.50, 0);
INSERT INTO dbo.Empresa (id_Empresa, NomeEmpresa, FantasiaEmpresa, CNPJ, TaxaRetencao, Inativa) VALUES (3, N'Centro Medico Gamma S/S', N'Gamma Saude', N'11222333000144', 12.00, 0);

SET IDENTITY_INSERT dbo.Empresa OFF;

SET IDENTITY_INSERT dbo.PlanoRetencao ON;
-- PlanoRetencao
-- Cadastro de planos de retenção
INSERT INTO dbo.PlanoRetencao (id_PlanoRetencao, NomePlanoRetencao, Inativo, LiberaDespesas) VALUES (1, N'Item 1', 1, 1);
INSERT INTO dbo.PlanoRetencao (id_PlanoRetencao, NomePlanoRetencao, Inativo, LiberaDespesas) VALUES (2, N'Registro 2', 0, 1);
INSERT INTO dbo.PlanoRetencao (id_PlanoRetencao, NomePlanoRetencao, Inativo, LiberaDespesas) VALUES (3, N'Valor 3', 0, 0);
INSERT INTO dbo.PlanoRetencao (id_PlanoRetencao, NomePlanoRetencao, Inativo, LiberaDespesas) VALUES (4, N'Valor 4', 0, 0);
INSERT INTO dbo.PlanoRetencao (id_PlanoRetencao, NomePlanoRetencao, Inativo, LiberaDespesas) VALUES (5, N'Valor 5', 0, 0);

SET IDENTITY_INSERT dbo.PlanoRetencao OFF;

SET IDENTITY_INSERT dbo.DespesaReceita ON;
-- DespesaReceita
-- Cadastro de despesas e receitas
INSERT INTO dbo.DespesaReceita (id_DespesaReceita, SiglaDespesaReceita, NomeDespesaReceita, Despesa, TemRateio, Inativa, Valor, Parcelas, Periodicidade, ParcelaUnica) VALUES (1, N'GCL', N'Item 1', 1, 1, 1, NULL, 1, 1, 1);
INSERT INTO dbo.DespesaReceita (id_DespesaReceita, SiglaDespesaReceita, NomeDespesaReceita, Despesa, TemRateio, Inativa, Valor, Parcelas, Periodicidade, ParcelaUnica) VALUES (2, N'UKL', N'Descrição 2', 2, 2, 2, NULL, 2, 2, 2);
INSERT INTO dbo.DespesaReceita (id_DespesaReceita, SiglaDespesaReceita, NomeDespesaReceita, Despesa, TemRateio, Inativa, Valor, Parcelas, Periodicidade, ParcelaUnica) VALUES (3, N'KCD', N'Registro 3', 3, 3, 3, NULL, 3, 3, 3);
INSERT INTO dbo.DespesaReceita (id_DespesaReceita, SiglaDespesaReceita, NomeDespesaReceita, Despesa, TemRateio, Inativa, Valor, Parcelas, Periodicidade, ParcelaUnica) VALUES (4, N'WKN', N'Descrição 4', 4, 4, 4, NULL, 4, 4, 4);
INSERT INTO dbo.DespesaReceita (id_DespesaReceita, SiglaDespesaReceita, NomeDespesaReceita, Despesa, TemRateio, Inativa, Valor, Parcelas, Periodicidade, ParcelaUnica) VALUES (5, N'ILF', N'Valor 5', 5, 5, 5, NULL, 5, 5, 5);

SET IDENTITY_INSERT dbo.DespesaReceita OFF;

SET IDENTITY_INSERT dbo.Cartorio ON;
-- Cartorio
-- Cadastro de cartórios
INSERT INTO dbo.Cartorio (id_Cartorio, NomeCartorio, Endereco, Telefone) VALUES (1, N'Descrição 1', N'Rua B, 236', N'778338978');
INSERT INTO dbo.Cartorio (id_Cartorio, NomeCartorio, Endereco, Telefone) VALUES (2, N'Descrição 2', N'Rua A, 5', N'620258145');
INSERT INTO dbo.Cartorio (id_Cartorio, NomeCartorio, Endereco, Telefone) VALUES (3, N'Item 3', N'Avenida D, 763', N'934095675');
INSERT INTO dbo.Cartorio (id_Cartorio, NomeCartorio, Endereco, Telefone) VALUES (4, N'Item 4', N'Avenida D, 229', N'674530151');
INSERT INTO dbo.Cartorio (id_Cartorio, NomeCartorio, Endereco, Telefone) VALUES (5, N'Valor 5', N'Rua B, 640', N'857032803');

SET IDENTITY_INSERT dbo.Cartorio OFF;

SET IDENTITY_INSERT dbo.Pessoa ON;
-- Pessoa
-- Cadastro de pessoas (sócios/médicos)
INSERT INTO dbo.Pessoa (id_Pessoa, Nome, CPF, RG, OrgaoEmissor, Nascimento, Pai, Mae, Nacionalidade, NumeroDependente, Endereco, CEP, Telefone, Celular, Email, NumeroConselho, DataFiliacao, InscricaoISS, InscricaoINSS, ClasseInss, Agencia, Conta, DvConta, ContaPoupanca, DataAdesao, Percentual, Taxaespecial, Consulta, Procedimento, NumeroParcelasCota, ValorDeCadaCota, DataIndicacao, DataDesligamento, ValorPagoNoDesligamento, DataInativo, ObservacaoPessoa, Atualizacao, DataAlteracao) VALUES (1, N'Descrição 1', N'44577342095', N'Valor 1', N'Valor 1', '2025-05-24', N'Valor 1', N'Valor 1', N'Valor 1', 1, N'Rua E, 625', N'Valor 1', N'123033468', N'Valor 1', N'user1@example.com', 1, N'2025-06-25', N'Valor 1', N'Valor 1', N'Valor 1', N'Valor 1', N'Valor 1', N'Valor 1', N'false', '2025-12-19', N'249,02', N'856,93', N'513,83', N'818,78', 1, NULL, N'2025-11-26 07:52:56', N'2025-04-17', NULL, '2025-09-09 07:52:56', NULL, N'2025-03-27', N'2025-02-18 07:52:56');
INSERT INTO dbo.Pessoa (id_Pessoa, Nome, CPF, RG, OrgaoEmissor, Nascimento, Pai, Mae, Nacionalidade, NumeroDependente, Endereco, CEP, Telefone, Celular, Email, NumeroConselho, DataFiliacao, InscricaoISS, InscricaoINSS, ClasseInss, Agencia, Conta, DvConta, ContaPoupanca, DataAdesao, Percentual, Taxaespecial, Consulta, Procedimento, NumeroParcelasCota, ValorDeCadaCota, DataIndicacao, DataDesligamento, ValorPagoNoDesligamento, DataInativo, ObservacaoPessoa, Atualizacao, DataAlteracao) VALUES (2, N'Item 2', N'41214681680', N'Valor 2', N'Valor 2', '2025-10-12', N'Valor 2', N'Valor 2', N'Valor 2', 2, N'Rua B, 139', N'Valor 2', N'140353401', N'Valor 2', N'user2@example.com', 2, N'2025-06-20', N'Valor 2', N'Valor 2', N'Valor 2', N'Valor 2', N'Valor 2', N'Valor 2', N'false', '2025-08-29', N'258,29', N'210,02', N'941,85', N'911,59', 2, NULL, N'2025-04-01 07:52:56', N'2025-08-14', NULL, '2025-09-19 07:52:56', NULL, N'2025-11-03', N'2025-03-26 07:52:56');
INSERT INTO dbo.Pessoa (id_Pessoa, Nome, CPF, RG, OrgaoEmissor, Nascimento, Pai, Mae, Nacionalidade, NumeroDependente, Endereco, CEP, Telefone, Celular, Email, NumeroConselho, DataFiliacao, InscricaoISS, InscricaoINSS, ClasseInss, Agencia, Conta, DvConta, ContaPoupanca, DataAdesao, Percentual, Taxaespecial, Consulta, Procedimento, NumeroParcelasCota, ValorDeCadaCota, DataIndicacao, DataDesligamento, ValorPagoNoDesligamento, DataInativo, ObservacaoPessoa, Atualizacao, DataAlteracao) VALUES (3, N'Item 3', N'39447430891', N'Valor 3', N'Valor 3', '2025-05-10', N'Valor 3', N'Valor 3', N'Valor 3', 3, N'Rua B, 664', N'Valor 3', N'387735117', N'Valor 3', N'user3@example.com', 3, N'2025-11-12', N'Valor 3', N'Valor 3', N'Valor 3', N'Valor 3', N'Valor 3', N'Valor 3', N'false', '2025-10-10', N'615,97', N'519,66', N'542,99', N'140,76', 3, NULL, N'2025-11-28 07:52:56', N'2025-11-04', NULL, '2025-11-11 07:52:56', NULL, N'2025-08-23', N'2025-05-27 07:52:56');
INSERT INTO dbo.Pessoa (id_Pessoa, Nome, CPF, RG, OrgaoEmissor, Nascimento, Pai, Mae, Nacionalidade, NumeroDependente, Endereco, CEP, Telefone, Celular, Email, NumeroConselho, DataFiliacao, InscricaoISS, InscricaoINSS, ClasseInss, Agencia, Conta, DvConta, ContaPoupanca, DataAdesao, Percentual, Taxaespecial, Consulta, Procedimento, NumeroParcelasCota, ValorDeCadaCota, DataIndicacao, DataDesligamento, ValorPagoNoDesligamento, DataInativo, ObservacaoPessoa, Atualizacao, DataAlteracao) VALUES (4, N'Descrição 4', N'21095174132', N'Valor 4', N'Valor 4', '2025-09-29', N'Valor 4', N'Valor 4', N'Valor 4', 4, N'Rua E, 870', N'Valor 4', N'942779814', N'Valor 4', N'user4@example.com', 4, N'2025-09-07', N'Valor 4', N'Valor 4', N'Valor 4', N'Valor 4', N'Valor 4', N'Valor 4', N'false', '2025-07-06', N'839,35', N'688,88', N'804,67', N'653,43', 4, NULL, N'2025-04-23 07:52:56', N'2025-10-25', NULL, '2025-08-08 07:52:56', NULL, N'2025-07-10', N'2025-07-29 07:52:56');
INSERT INTO dbo.Pessoa (id_Pessoa, Nome, CPF, RG, OrgaoEmissor, Nascimento, Pai, Mae, Nacionalidade, NumeroDependente, Endereco, CEP, Telefone, Celular, Email, NumeroConselho, DataFiliacao, InscricaoISS, InscricaoINSS, ClasseInss, Agencia, Conta, DvConta, ContaPoupanca, DataAdesao, Percentual, Taxaespecial, Consulta, Procedimento, NumeroParcelasCota, ValorDeCadaCota, DataIndicacao, DataDesligamento, ValorPagoNoDesligamento, DataInativo, ObservacaoPessoa, Atualizacao, DataAlteracao) VALUES (5, N'Registro 5', N'96973673884', N'Valor 5', N'Valor 5', '2025-01-06', N'Valor 5', N'Valor 5', N'Valor 5', 5, N'Rua E, 625', N'Valor 5', N'017749787', N'Valor 5', N'user5@example.com', 5, N'2025-12-04', N'Valor 5', N'Valor 5', N'Valor 5', N'Valor 5', N'Valor 5', N'Valor 5', 1, '2025-02-26', N'397,25', N'941,39', N'94,03', N'855,54', 5, NULL, N'2025-11-11 07:52:56', N'2025-07-12', NULL, '2025-10-31 07:52:56', NULL, N'2025-10-08', N'2025-03-21 07:52:56');

SET IDENTITY_INSERT dbo.Pessoa OFF;

SET IDENTITY_INSERT dbo.Cliente ON;
-- Cliente
-- Cadastro de clientes
INSERT INTO dbo.Cliente (id_Cliente, NomeCliente, FantasiaCliente, CNPJ, IE, IM, DataContrato, TaxaADM, Endereco, CEP, Contato, Fone1, Fone2, email, ObservacaoCliente, ObsDaNota, PessoaJuridica) VALUES (1, N'Descrição 1', N'Valor 1', N'57974966975974', N'Valor 1', N'Valor 1', '2025-07-12 07:52:56', 126.33, N'Rua A, 288', N'Valor 1', N'Valor 1', N'Valor 1', N'Valor 1', N'user1@example.com', NULL, NULL, 1);
INSERT INTO dbo.Cliente (id_Cliente, NomeCliente, FantasiaCliente, CNPJ, IE, IM, DataContrato, TaxaADM, Endereco, CEP, Contato, Fone1, Fone2, email, ObservacaoCliente, ObsDaNota, PessoaJuridica) VALUES (2, N'Valor 2', N'Valor 2', N'37557949137351', N'Valor 2', N'Valor 2', '2025-07-12 07:52:56', 778.40, N'Rua B, 982', N'Valor 2', N'Valor 2', N'Valor 2', N'Valor 2', N'user2@example.com', NULL, NULL, 2);
INSERT INTO dbo.Cliente (id_Cliente, NomeCliente, FantasiaCliente, CNPJ, IE, IM, DataContrato, TaxaADM, Endereco, CEP, Contato, Fone1, Fone2, email, ObservacaoCliente, ObsDaNota, PessoaJuridica) VALUES (3, N'Descrição 3', N'Valor 3', N'31669611510948', N'Valor 3', N'Valor 3', '2025-08-27 07:52:56', 264.64, N'Rua B, 894', N'Valor 3', N'Valor 3', N'Valor 3', N'Valor 3', N'user3@example.com', NULL, NULL, 3);
INSERT INTO dbo.Cliente (id_Cliente, NomeCliente, FantasiaCliente, CNPJ, IE, IM, DataContrato, TaxaADM, Endereco, CEP, Contato, Fone1, Fone2, email, ObservacaoCliente, ObsDaNota, PessoaJuridica) VALUES (4, N'Registro 4', N'Valor 4', N'66697371481209', N'Valor 4', N'Valor 4', '2025-07-12 07:52:56', 790.31, N'Avenida D, 415', N'Valor 4', N'Valor 4', N'Valor 4', N'Valor 4', N'user4@example.com', NULL, NULL, 4);
INSERT INTO dbo.Cliente (id_Cliente, NomeCliente, FantasiaCliente, CNPJ, IE, IM, DataContrato, TaxaADM, Endereco, CEP, Contato, Fone1, Fone2, email, ObservacaoCliente, ObsDaNota, PessoaJuridica) VALUES (5, N'Item 5', N'Valor 5', N'70599101774053', N'Valor 5', N'Valor 5', '2025-03-26 07:52:56', 25.57, N'Rua A, 233', N'Valor 5', N'Valor 5', N'Valor 5', N'Valor 5', N'user5@example.com', NULL, NULL, 5);

SET IDENTITY_INSERT dbo.Cliente OFF;

SET IDENTITY_INSERT dbo.Lancamento ON;
-- Lancamento
-- Lançamentos financeiros
INSERT INTO dbo.Lancamento (id_lancamento, Data, NF, ValorBruto, Despesas, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Taxa, MesAno, Baixa) VALUES (1, '2025-02-24 07:52:56', 1, NULL, NULL, NULL, NULL, NULL, NULL, 517.93, N'Valor 1', '2025-04-08 07:52:56');
INSERT INTO dbo.Lancamento (id_lancamento, Data, NF, ValorBruto, Despesas, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Taxa, MesAno, Baixa) VALUES (2, '2025-05-28 07:52:56', 2, NULL, NULL, NULL, NULL, NULL, NULL, 673.38, N'Valor 2', '2025-05-26 07:52:56');
INSERT INTO dbo.Lancamento (id_lancamento, Data, NF, ValorBruto, Despesas, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Taxa, MesAno, Baixa) VALUES (3, '2025-10-30 07:52:56', 3, NULL, NULL, NULL, NULL, NULL, NULL, 243.73, N'Valor 3', '2025-04-30 07:52:56');
INSERT INTO dbo.Lancamento (id_lancamento, Data, NF, ValorBruto, Despesas, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Taxa, MesAno, Baixa) VALUES (4, '2025-06-09 07:52:56', 4, NULL, NULL, NULL, NULL, NULL, NULL, 673.75, N'Valor 4', '2025-09-19 07:52:56');
INSERT INTO dbo.Lancamento (id_lancamento, Data, NF, ValorBruto, Despesas, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Taxa, MesAno, Baixa) VALUES (5, '2025-10-02 07:52:56', 5, NULL, NULL, NULL, NULL, NULL, NULL, 422.58, N'Valor 5', '2025-05-20 07:52:56');

SET IDENTITY_INSERT dbo.Lancamento OFF;

SET IDENTITY_INSERT dbo.NF ON;
-- NF
-- Notas fiscais
INSERT INTO dbo.NF (id_Empresa, NF, Emissao, Vencimento, Total, IRRF, PIS, Cofins, CSLL, ISS, Baixa, ValorQuitado, Cancelada, Observacao, ObservacaoBaixa, OutrasDespesas) VALUES (1, 1, '2025-10-12 07:52:56', '2025-04-25 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-07-16 07:52:56', NULL, 0, N'Valor 1', N'Valor 1', NULL);
INSERT INTO dbo.NF (id_Empresa, NF, Emissao, Vencimento, Total, IRRF, PIS, Cofins, CSLL, ISS, Baixa, ValorQuitado, Cancelada, Observacao, ObservacaoBaixa, OutrasDespesas) VALUES (1, 2, '2025-10-30 07:52:56', '2025-10-29 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-07-15 07:52:56', NULL, 0, N'Valor 2', N'Valor 2', NULL);
INSERT INTO dbo.NF (id_Empresa, NF, Emissao, Vencimento, Total, IRRF, PIS, Cofins, CSLL, ISS, Baixa, ValorQuitado, Cancelada, Observacao, ObservacaoBaixa, OutrasDespesas) VALUES (1, 3, '2025-06-04 07:52:56', '2025-03-30 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-02-19 07:52:56', NULL, 1, N'Valor 3', N'Valor 3', NULL);
INSERT INTO dbo.NF (id_Empresa, NF, Emissao, Vencimento, Total, IRRF, PIS, Cofins, CSLL, ISS, Baixa, ValorQuitado, Cancelada, Observacao, ObservacaoBaixa, OutrasDespesas) VALUES (1, 4, '2025-08-26 07:52:56', '2025-02-15 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-06-02 07:52:56', NULL, 1, N'Valor 4', N'Valor 4', NULL);
INSERT INTO dbo.NF (id_Empresa, NF, Emissao, Vencimento, Total, IRRF, PIS, Cofins, CSLL, ISS, Baixa, ValorQuitado, Cancelada, Observacao, ObservacaoBaixa, OutrasDespesas) VALUES (1, 5, '2025-11-13 07:52:56', '2025-04-20 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-08-06 07:52:56', NULL, 0, N'Valor 5', N'Valor 5', NULL);

SET IDENTITY_INSERT dbo.NF OFF;

SET IDENTITY_INSERT dbo.ContasPagarReceber ON;
-- ContasPagarReceber
-- Contas a pagar e receber
INSERT INTO dbo.ContasPagarReceber (id_ContasPagarReceber, DataLancamento, ValorOriginal, ValorParcela, DataVencimento, DataBaixa, ValorBaixado, MesAnoReferencia, Historico) VALUES (1, '2025-07-30 07:52:56', NULL, NULL, '2025-01-20 07:52:56', '2025-06-24 07:52:56', NULL, N'Valor 1', N'Valor 1');
INSERT INTO dbo.ContasPagarReceber (id_ContasPagarReceber, DataLancamento, ValorOriginal, ValorParcela, DataVencimento, DataBaixa, ValorBaixado, MesAnoReferencia, Historico) VALUES (2, '2025-09-07 07:52:56', NULL, NULL, '2025-11-18 07:52:56', '2025-08-26 07:52:56', NULL, N'Valor 2', N'Valor 2');
INSERT INTO dbo.ContasPagarReceber (id_ContasPagarReceber, DataLancamento, ValorOriginal, ValorParcela, DataVencimento, DataBaixa, ValorBaixado, MesAnoReferencia, Historico) VALUES (3, '2025-06-17 07:52:56', NULL, NULL, '2024-12-22 07:52:56', '2025-08-15 07:52:56', NULL, N'Valor 3', N'Valor 3');
INSERT INTO dbo.ContasPagarReceber (id_ContasPagarReceber, DataLancamento, ValorOriginal, ValorParcela, DataVencimento, DataBaixa, ValorBaixado, MesAnoReferencia, Historico) VALUES (4, '2025-10-13 07:52:56', NULL, NULL, '2025-12-16 07:52:56', '2025-12-14 07:52:56', NULL, N'Valor 4', N'Valor 4');
INSERT INTO dbo.ContasPagarReceber (id_ContasPagarReceber, DataLancamento, ValorOriginal, ValorParcela, DataVencimento, DataBaixa, ValorBaixado, MesAnoReferencia, Historico) VALUES (5, '2025-09-01 07:52:56', NULL, NULL, '2025-03-18 07:52:56', '2025-08-01 07:52:56', NULL, N'Valor 5', N'Valor 5');

SET IDENTITY_INSERT dbo.ContasPagarReceber OFF;

SET IDENTITY_INSERT dbo.PagamentoNaoSocio ON;
-- PagamentoNaoSocio
-- Pagamentos para não sócios
INSERT INTO dbo.PagamentoNaoSocio (Data, id_Empresa, id_Cliente, id_PessoaNaoSocio, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse) VALUES ('2025-05-07 07:52:56', 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.PagamentoNaoSocio (Data, id_Empresa, id_Cliente, id_PessoaNaoSocio, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse) VALUES ('2025-09-22 07:52:56', 1, 2, 2, 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.PagamentoNaoSocio (Data, id_Empresa, id_Cliente, id_PessoaNaoSocio, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse) VALUES ('2025-11-30 07:52:56', 1, 3, 3, 3, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.PagamentoNaoSocio (Data, id_Empresa, id_Cliente, id_PessoaNaoSocio, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse) VALUES ('2025-04-17 07:52:56', 1, 4, 4, 4, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.PagamentoNaoSocio (Data, id_Empresa, id_Cliente, id_PessoaNaoSocio, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse) VALUES ('2025-09-08 07:52:56', 1, 5, 5, 5, NULL, NULL, NULL, NULL, NULL);

SET IDENTITY_INSERT dbo.PagamentoNaoSocio OFF;

SET IDENTITY_INSERT dbo.RepasseAnual ON;
-- RepasseAnual
-- Repasse anual consolidado
INSERT INTO dbo.RepasseAnual (Ano, id_Empresa, id_Pessoa, JanBruto, JanTaxa, FevBruto, FevTaxa, MarBruto, MarTaxa, AbrBruto, AbrTaxa, MaiBruto, MaiTaxa, JunBruto, JunTaxa, JulBruto, JulTaxa, AgoBruto, AgoTaxa, SetBruto, SetTaxa, OutBruto, OutTaxa, NovBruto, NovTaxa, DezBruto, DezTaxa) VALUES (N'Valor 1', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.RepasseAnual (Ano, id_Empresa, id_Pessoa, JanBruto, JanTaxa, FevBruto, FevTaxa, MarBruto, MarTaxa, AbrBruto, AbrTaxa, MaiBruto, MaiTaxa, JunBruto, JunTaxa, JulBruto, JulTaxa, AgoBruto, AgoTaxa, SetBruto, SetTaxa, OutBruto, OutTaxa, NovBruto, NovTaxa, DezBruto, DezTaxa) VALUES (N'Valor 2', 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.RepasseAnual (Ano, id_Empresa, id_Pessoa, JanBruto, JanTaxa, FevBruto, FevTaxa, MarBruto, MarTaxa, AbrBruto, AbrTaxa, MaiBruto, MaiTaxa, JunBruto, JunTaxa, JulBruto, JulTaxa, AgoBruto, AgoTaxa, SetBruto, SetTaxa, OutBruto, OutTaxa, NovBruto, NovTaxa, DezBruto, DezTaxa) VALUES (N'Valor 3', 1, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.RepasseAnual (Ano, id_Empresa, id_Pessoa, JanBruto, JanTaxa, FevBruto, FevTaxa, MarBruto, MarTaxa, AbrBruto, AbrTaxa, MaiBruto, MaiTaxa, JunBruto, JunTaxa, JulBruto, JulTaxa, AgoBruto, AgoTaxa, SetBruto, SetTaxa, OutBruto, OutTaxa, NovBruto, NovTaxa, DezBruto, DezTaxa) VALUES (N'Valor 4', 1, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.RepasseAnual (Ano, id_Empresa, id_Pessoa, JanBruto, JanTaxa, FevBruto, FevTaxa, MarBruto, MarTaxa, AbrBruto, AbrTaxa, MaiBruto, MaiTaxa, JunBruto, JunTaxa, JulBruto, JulTaxa, AgoBruto, AgoTaxa, SetBruto, SetTaxa, OutBruto, OutTaxa, NovBruto, NovTaxa, DezBruto, DezTaxa) VALUES (N'Valor 5', 1, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

SET IDENTITY_INSERT dbo.RepasseAnual OFF;

SET IDENTITY_INSERT dbo.AnuidadeCremeb ON;
-- AnuidadeCremeb
-- Anuidades do CREMEB
INSERT INTO dbo.AnuidadeCremeb (id_AnuidadeCremeb, AnoExercicio, DataInicio, DataFim, ValorTotal) VALUES (1, N'Valor 1', '2025-02-15 07:52:56', '2025-09-01 07:52:56', NULL);
INSERT INTO dbo.AnuidadeCremeb (id_AnuidadeCremeb, AnoExercicio, DataInicio, DataFim, ValorTotal) VALUES (2, N'Valor 2', '2025-08-05 07:52:56', '2025-08-02 07:52:56', NULL);
INSERT INTO dbo.AnuidadeCremeb (id_AnuidadeCremeb, AnoExercicio, DataInicio, DataFim, ValorTotal) VALUES (3, N'Valor 3', '2025-12-19 07:52:56', '2025-04-28 07:52:56', NULL);
INSERT INTO dbo.AnuidadeCremeb (id_AnuidadeCremeb, AnoExercicio, DataInicio, DataFim, ValorTotal) VALUES (4, N'Valor 4', '2025-07-31 07:52:56', '2025-12-12 07:52:56', NULL);
INSERT INTO dbo.AnuidadeCremeb (id_AnuidadeCremeb, AnoExercicio, DataInicio, DataFim, ValorTotal) VALUES (5, N'Valor 5', '2025-06-23 07:52:56', '2025-06-15 07:52:56', NULL);

SET IDENTITY_INSERT dbo.AnuidadeCremeb OFF;

SET IDENTITY_INSERT dbo.MedicoEspecialidade ON;
-- MedicoEspecialidade
-- Relacionamento médico-especialidade
INSERT INTO dbo.MedicoEspecialidade (id_Pessoa, id_Especialidade) VALUES (1, 1);
INSERT INTO dbo.MedicoEspecialidade (id_Pessoa, id_Especialidade) VALUES (2, 2);

SET IDENTITY_INSERT dbo.MedicoEspecialidade OFF;

SET IDENTITY_INSERT dbo.EmpresaDespesaFixa ON;
-- EmpresaDespesaFixa
-- Despesas fixas por empresa
INSERT INTO dbo.EmpresaDespesaFixa (id_Empresa, id_DespesaReceita, DataLancamento, Parcelas, ValorEmpresa, Inativa) VALUES (1, 1, '2025-03-31 07:52:56', 1, NULL, 1);
INSERT INTO dbo.EmpresaDespesaFixa (id_Empresa, id_DespesaReceita, DataLancamento, Parcelas, ValorEmpresa, Inativa) VALUES (1, 2, '2025-11-12 07:52:56', 2, NULL, 2);
INSERT INTO dbo.EmpresaDespesaFixa (id_Empresa, id_DespesaReceita, DataLancamento, Parcelas, ValorEmpresa, Inativa) VALUES (1, 3, '2025-03-03 07:52:56', 3, NULL, 3);

SET IDENTITY_INSERT dbo.EmpresaDespesaFixa OFF;

SET IDENTITY_INSERT dbo.EmpresaCliente ON;
-- EmpresaCliente
-- Relacionamento empresa-cliente com taxas
INSERT INTO dbo.EmpresaCliente (id_Empresa, id_Cliente, Taxa, Processo, TaxaISS, TaxaCOFINS, TaxaPIS, TaxaCSLL, TaxaIRRF) VALUES (1, 1, 456.73, N'Valor 1', 364.17, 931.64, 819.49, 605.04, 301.41);
INSERT INTO dbo.EmpresaCliente (id_Empresa, id_Cliente, Taxa, Processo, TaxaISS, TaxaCOFINS, TaxaPIS, TaxaCSLL, TaxaIRRF) VALUES (1, 2, 14.84, N'Valor 2', 684.50, 261.22, 926.22, 376.15, 583.15);
INSERT INTO dbo.EmpresaCliente (id_Empresa, id_Cliente, Taxa, Processo, TaxaISS, TaxaCOFINS, TaxaPIS, TaxaCSLL, TaxaIRRF) VALUES (1, 3, 405.50, N'Valor 3', 910.40, 889.60, 22.55, 754.25, 221.00);

SET IDENTITY_INSERT dbo.EmpresaCliente OFF;

SET IDENTITY_INSERT dbo.Retencao ON;
-- Retencao
-- Faixas de retenção por plano
INSERT INTO dbo.Retencao (id_PlanoRetencao, Ate, Reter) VALUES (1, 687.91, 617.93);
INSERT INTO dbo.Retencao (id_PlanoRetencao, Ate, Reter) VALUES (2, 346.72, 34.52);
INSERT INTO dbo.Retencao (id_PlanoRetencao, Ate, Reter) VALUES (3, 268.90, 812.02);
INSERT INTO dbo.Retencao (id_PlanoRetencao, Ate, Reter) VALUES (4, 350.37, 898.18);
INSERT INTO dbo.Retencao (id_PlanoRetencao, Ate, Reter) VALUES (5, 698.08, 738.55);

SET IDENTITY_INSERT dbo.Retencao OFF;

SET IDENTITY_INSERT dbo.PessoaCartorio ON;
-- PessoaCartorio
-- Relacionamento pessoa-cartório
INSERT INTO dbo.PessoaCartorio (id_Pessoa, id_Cartorio) VALUES (1, 1);
INSERT INTO dbo.PessoaCartorio (id_Pessoa, id_Cartorio) VALUES (2, 2);

SET IDENTITY_INSERT dbo.PessoaCartorio OFF;

SET IDENTITY_INSERT dbo.PessoaContaRecebimento ON;
-- PessoaContaRecebimento
-- Conta de recebimento por pessoa e cliente
INSERT INTO dbo.PessoaContaRecebimento (id_Pessoa, id_Cliente, id_ContaCorrente) VALUES (1, 1, 1);
INSERT INTO dbo.PessoaContaRecebimento (id_Pessoa, id_Cliente, id_ContaCorrente) VALUES (2, 2, 2);

SET IDENTITY_INSERT dbo.PessoaContaRecebimento OFF;

SET IDENTITY_INSERT dbo.PessoaContaCorrente ON;
-- PessoaContaCorrente
-- Contas correntes da pessoa
INSERT INTO dbo.PessoaContaCorrente (id_ContaCorrente, Agencia, ContaCorrente, dvContaCorrente, Ativa, ContaPoupanca, ContaPadrao, NomeDependente, PIX) VALUES (1, N'Valor 1', N'Valor 1', N'Valor 1', 0, 1, 1, N'Descrição 1', N'Valor 1');
INSERT INTO dbo.PessoaContaCorrente (id_ContaCorrente, Agencia, ContaCorrente, dvContaCorrente, Ativa, ContaPoupanca, ContaPadrao, NomeDependente, PIX) VALUES (2, N'Valor 2', N'Valor 2', N'Valor 2', 1, 1, 1, N'Descrição 2', N'Valor 2');

SET IDENTITY_INSERT dbo.PessoaContaCorrente OFF;

SET IDENTITY_INSERT dbo.ClienteContato ON;
-- ClienteContato
-- Contatos do cliente
INSERT INTO dbo.ClienteContato (id_ClienteContato, Descricao) VALUES (1, N'Descrição do registro 1');
INSERT INTO dbo.ClienteContato (id_ClienteContato, Descricao) VALUES (2, N'Descrição do registro 2');

SET IDENTITY_INSERT dbo.ClienteContato OFF;

SET IDENTITY_INSERT dbo.AnuidadeCremebItem ON;
-- AnuidadeCremebItem
-- Itens individuais da anuidade CREMEB
INSERT INTO dbo.AnuidadeCremebItem (id_AnuidadeCremebItem, DataLancamento, ValorIndividual, DataPagamento) VALUES (1, '2024-12-21 07:52:56', NULL, N'Valor 1');
INSERT INTO dbo.AnuidadeCremebItem (id_AnuidadeCremebItem, DataLancamento, ValorIndividual, DataPagamento) VALUES (2, '2025-01-21 07:52:56', NULL, N'Valor 2');

SET IDENTITY_INSERT dbo.AnuidadeCremebItem OFF;

SET IDENTITY_INSERT dbo.Adiantamento ON;
-- Adiantamento
-- Adiantamentos de pagamentos
INSERT INTO dbo.Adiantamento (id_Adiantamento, Data, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Pago) VALUES (1, '2025-08-04 07:52:56', 1, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO dbo.Adiantamento (id_Adiantamento, Data, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Pago) VALUES (2, '2025-09-20 07:52:56', 2, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO dbo.Adiantamento (id_Adiantamento, Data, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Pago) VALUES (3, '2025-06-02 07:52:56', 3, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO dbo.Adiantamento (id_Adiantamento, Data, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Pago) VALUES (4, '2025-04-20 07:52:56', 4, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO dbo.Adiantamento (id_Adiantamento, Data, NF, ValorBruto, Retencao, ValorLiquido, ValorTaxa, ValorRepasse, Pago) VALUES (5, '2025-02-11 07:52:56', 5, NULL, NULL, NULL, NULL, NULL, 1);

SET IDENTITY_INSERT dbo.Adiantamento OFF;

SET IDENTITY_INSERT dbo.EmpresaSocio ON;
-- EmpresaSocio
-- Sócios da empresa
INSERT INTO dbo.EmpresaSocio (id_Empresa, id_Pessoa, DataAdesao, NumeroQuotas, ValorQuota, DataSaida, ValorCremeb, NovoModelo, TaxaConsulta, TaxaProcedimento) VALUES (1, 1, '2025-04-15 07:52:56', 1, NULL, '2025-05-06 07:52:56', NULL, 1, 627.96, 684.62);
INSERT INTO dbo.EmpresaSocio (id_Empresa, id_Pessoa, DataAdesao, NumeroQuotas, ValorQuota, DataSaida, ValorCremeb, NovoModelo, TaxaConsulta, TaxaProcedimento) VALUES (1, 2, '2025-07-25 07:52:56', 2, NULL, '2025-03-23 07:52:56', NULL, 0, 896.15, 568.80);
INSERT INTO dbo.EmpresaSocio (id_Empresa, id_Pessoa, DataAdesao, NumeroQuotas, ValorQuota, DataSaida, ValorCremeb, NovoModelo, TaxaConsulta, TaxaProcedimento) VALUES (1, 3, '2025-02-03 07:52:56', 3, NULL, '2025-02-06 07:52:56', NULL, 1, 177.36, 685.31);

SET IDENTITY_INSERT dbo.EmpresaSocio OFF;

SET IDENTITY_INSERT dbo.ClienteFilial ON;
-- ClienteFilial
-- Filiais do cliente
INSERT INTO dbo.ClienteFilial (id_ClienteFilial, NomeFilial) VALUES (1, N'Descrição 1');
INSERT INTO dbo.ClienteFilial (id_ClienteFilial, NomeFilial) VALUES (2, N'Descrição 2');

SET IDENTITY_INSERT dbo.ClienteFilial OFF;

