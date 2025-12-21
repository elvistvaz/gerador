-- ============================================================
-- CARGA INICIAL - XANDEL
-- Gerado automaticamente a partir dos arquivos CSV
-- Para H2 Database (Spring Boot Dev)
-- ============================================================

-- Banco
-- Cadastro de bancos
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('001', 'Banco do Brasil');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('033', 'Santander');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('104', 'Caixa Economica Federal');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('237', 'Bradesco');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('341', 'Itau Unibanco');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('756', 'Sicoob');

-- Bairro
-- Cadastro de bairros
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (1, 'Centro');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (2, 'Pituba');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (3, 'Barra');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (4, 'Ondina');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (5, 'Itaigara');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (6, 'Caminho das Arvores');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (7, 'Paralela');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (8, 'Brotas');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (9, 'Federacao');
INSERT INTO dbo.bairro (id_bairro, nome_bairro) VALUES (10, 'Rio Vermelho');

-- Cidade
-- Cadastro de cidades
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (1, 'Salvador', 'BA', '71', 5.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (2, 'Feira de Santana', 'BA', '75', 3.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (3, 'Vitoria da Conquista', 'BA', '77', 3.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (4, 'Camacari', 'BA', '71', 3.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (5, 'Juazeiro', 'BA', '74', 3.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (6, 'Lauro de Freitas', 'BA', '71', 5.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (7, 'Ilheus', 'BA', '73', 3.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (8, 'Itabuna', 'BA', '73', 3.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (9, 'Teixeira de Freitas', 'BA', '73', 3.00);
INSERT INTO dbo.cidade (id_cidade, nome_cidade, uf, ddd, iss) VALUES (10, 'Barreiras', 'BA', '77', 3.00);

-- Conselho
-- Cadastro de conselhos profissionais
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (1, 'Conselho Regional de Medicina da Bahia', 'CREMEB');
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (2, 'Conselho Regional de Enfermagem', 'COREN');
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (3, 'Conselho Regional de Odontologia', 'CRO');
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (4, 'Conselho Regional de Psicologia', 'CRP');
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (5, 'Conselho Regional de Fisioterapia', 'CREFITO');

-- Especialidade
-- Cadastro de especialidades médicas
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (1, 'Clinica Medica');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (2, 'Cardiologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (3, 'Dermatologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (4, 'Ginecologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (5, 'Ortopedia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (6, 'Pediatria');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (7, 'Neurologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (8, 'Oftalmologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (9, 'Otorrinolaringologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (10, 'Urologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (11, 'Psiquiatria');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (12, 'Cirurgia Geral');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (13, 'Anestesiologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (14, 'Radiologia');
INSERT INTO dbo.especialidade (id_especialidade, nome_especialidade) VALUES (15, 'Endocrinologia');

-- EstadoCivil
-- Cadastro de estados civis
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (1, 'Solteiro(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (2, 'Casado(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (3, 'Divorciado(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (4, 'Viuvo(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (5, 'Separado(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (6, 'Uniao Estavel');

-- Operadora
-- Cadastro de operadoras
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (1, 'Unimed');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (2, 'Bradesco Saude');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (3, 'SulAmerica');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (4, 'Amil');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (5, 'Hapvida');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (6, 'NotreDame Intermedica');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (7, 'Cassi');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (8, 'Geap');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (9, 'Postal Saude');
INSERT INTO dbo.operadora (id_operadora, nome_operadora) VALUES (10, 'Petrobras');

-- TipoServico
-- Cadastro de tipos de serviço
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (1, 'Consulta');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (2, 'Exame');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (3, 'Procedimento');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (4, 'Cirurgia');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (5, 'Internacao');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (6, 'Urgencia');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (7, 'Emergencia');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (8, 'Homecare');

-- TipoContato
-- Cadastro de tipos de contato
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (1, 'Telefone Fixo');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (2, 'Celular');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (3, 'Email');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (4, 'WhatsApp');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (5, 'Fax');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (6, 'Recado');

-- CBO
-- Cadastro Brasileiro de Ocupações
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225125', 'Medico clinico');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225120', 'Medico cardiologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225106', 'Medico cirurgiao geral');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225110', 'Medico dermatologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225109', 'Medico gastroenterologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225130', 'Medico ginecologista e obstetra');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225133', 'Medico neurologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225170', 'Medico ortopedista e traumatologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225124', 'Medico pediatra');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225175', 'Medico psiquiatra');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225180', 'Medico urologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225103', 'Medico anestesiologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225185', 'Medico radiologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225112', 'Medico endocrinologista');
INSERT INTO dbo.cbo (id_cbo, nomecbo) VALUES ('225145', 'Medico oftalmologista');

-- Usuario
-- Cadastro de usuários do sistema
INSERT INTO dbo.usuario (id_usuario, usuario_nome, usuario_login, usuario_senha, usuario_nivel_acesso, usuario_inativo) VALUES (1, 'Administrador', 'admin', 'admin', 1, FALSE);
INSERT INTO dbo.usuario (id_usuario, usuario_nome, usuario_login, usuario_senha, usuario_nivel_acesso, usuario_inativo) VALUES (2, 'Usuario Padrao', 'user', 'user', 2, FALSE);

-- Empresa
-- Cadastro de empresas
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (1, 'Sociedade Medica Alpha Ltda', 'Alpha Med', '12345678000100', 10.00, FALSE);
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (2, 'Clinica Beta Servicos Medicos', 'Beta Clinica', '98765432000199', 8.50, FALSE);
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (3, 'Centro Medico Gamma S/S', 'Gamma Saude', '11222333000144', 12.00, FALSE);


-- ============================================================
-- RESET AUTO-INCREMENT SEQUENCES
-- ============================================================
ALTER TABLE dbo.bairro ALTER COLUMN id_bairro RESTART WITH 11;
ALTER TABLE dbo.cidade ALTER COLUMN id_cidade RESTART WITH 11;
ALTER TABLE dbo.conselho ALTER COLUMN id_conselho RESTART WITH 6;
ALTER TABLE dbo.especialidade ALTER COLUMN id_especialidade RESTART WITH 16;
ALTER TABLE dbo.operadora ALTER COLUMN id_operadora RESTART WITH 11;
ALTER TABLE dbo.usuario ALTER COLUMN id_usuario RESTART WITH 3;
ALTER TABLE dbo.empresa ALTER COLUMN id_empresa RESTART WITH 4;
