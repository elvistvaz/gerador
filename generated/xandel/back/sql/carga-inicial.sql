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

SET IDENTITY_INSERT dbo.Empresa ON;
-- Empresa
-- Cadastro de empresas
INSERT INTO dbo.Empresa (id_Empresa, NomeEmpresa, FantasiaEmpresa, CNPJ, TaxaRetencao, Inativa) VALUES (1, N'Sociedade Medica Alpha Ltda', N'Alpha Med', N'12345678000100', 10.00, 0);
INSERT INTO dbo.Empresa (id_Empresa, NomeEmpresa, FantasiaEmpresa, CNPJ, TaxaRetencao, Inativa) VALUES (2, N'Clinica Beta Servicos Medicos', N'Beta Clinica', N'98765432000199', 8.50, 0);
INSERT INTO dbo.Empresa (id_Empresa, NomeEmpresa, FantasiaEmpresa, CNPJ, TaxaRetencao, Inativa) VALUES (3, N'Centro Medico Gamma S/S', N'Gamma Saude', N'11222333000144', 12.00, 0);

SET IDENTITY_INSERT dbo.Empresa OFF;

