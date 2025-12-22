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

-- ParametroEmail
-- Parâmetros de configuração de e-mail
INSERT INTO dbo.parametro_email (id_parametro, assunto_email, smtp, conta_email, senha_email) VALUES (1, 'user1@example.com', 'Valor 1', 'user1@example.com', 'user1@example.com');

-- ImpostoDeRenda
-- Tabela de Imposto de Renda
INSERT INTO dbo.imposto_de_renda (data, de, ate, aliquota, valordeduzir, deducao_dependente) VALUES ('2025-01-01', '0.00', '2259.20', 0.00, '0.00', '189.59');
INSERT INTO dbo.imposto_de_renda (data, de, ate, aliquota, valordeduzir, deducao_dependente) VALUES ('2025-01-01', '2259.21', '2826.65', 7.50, '169.44', '189.59');
INSERT INTO dbo.imposto_de_renda (data, de, ate, aliquota, valordeduzir, deducao_dependente) VALUES ('2025-01-01', '2826.66', '3751.05', 15.00, '381.44', '189.59');
INSERT INTO dbo.imposto_de_renda (data, de, ate, aliquota, valordeduzir, deducao_dependente) VALUES ('2025-01-01', '3751.06', '4664.68', 22.50, '662.77', '189.59');
INSERT INTO dbo.imposto_de_renda (data, de, ate, aliquota, valordeduzir, deducao_dependente) VALUES ('2025-01-01', '4664.69', '999999.99', 27.50, '896.00', '189.59');

-- ParametroNF
-- Parâmetros de Nota Fiscal
INSERT INTO dbo.parametronf (cofins, pis, csll, irrf, teto_imposto, base_pis_cofins_csll) VALUES (465.29, 798.76, 404.24, 795.89, 756.26, NULL);

-- Indicacao
-- Programas de indicação
INSERT INTO dbo.indicacao (id_indicacao, de, ate, indicacao_minima, indicacao_maxima, indice, teto_indice, grupo_indicados) VALUES (1, '2025-01-01', '2025-12-31', 1, 5, 5.00, 1000.00, 10);
INSERT INTO dbo.indicacao (id_indicacao, de, ate, indicacao_minima, indicacao_maxima, indice, teto_indice, grupo_indicados) VALUES (2, '2025-01-01', '2025-12-31', 6, 10, 7.50, 2000.00, 20);
INSERT INTO dbo.indicacao (id_indicacao, de, ate, indicacao_minima, indicacao_maxima, indice, teto_indice, grupo_indicados) VALUES (3, '2025-01-01', '2025-12-31', 11, 20, 10.00, 3000.00, 30);

-- Empresa
-- Cadastro de empresas
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (1, 'Sociedade Medica Alpha Ltda', 'Alpha Med', '12345678000100', 10.00, FALSE);
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (2, 'Clinica Beta Servicos Medicos', 'Beta Clinica', '98765432000199', 8.50, FALSE);
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (3, 'Centro Medico Gamma S/S', 'Gamma Saude', '11222333000144', 12.00, FALSE);

-- PlanoRetencao
-- Cadastro de planos de retenção
INSERT INTO dbo.plano_retencao (id_plano_retencao, nome_plano_retencao, inativo, libera_despesas) VALUES (1, 'Item 1', TRUE, TRUE);
INSERT INTO dbo.plano_retencao (id_plano_retencao, nome_plano_retencao, inativo, libera_despesas) VALUES (2, 'Registro 2', FALSE, TRUE);
INSERT INTO dbo.plano_retencao (id_plano_retencao, nome_plano_retencao, inativo, libera_despesas) VALUES (3, 'Valor 3', FALSE, FALSE);
INSERT INTO dbo.plano_retencao (id_plano_retencao, nome_plano_retencao, inativo, libera_despesas) VALUES (4, 'Valor 4', FALSE, FALSE);
INSERT INTO dbo.plano_retencao (id_plano_retencao, nome_plano_retencao, inativo, libera_despesas) VALUES (5, 'Valor 5', FALSE, FALSE);

-- DespesaReceita
-- Cadastro de despesas e receitas
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa, valor, parcelas, periodicidade, parcela_unica) VALUES (1, 'GCL', 'Item 1', 1, 1, 1, NULL, 1, 1, 1);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa, valor, parcelas, periodicidade, parcela_unica) VALUES (2, 'UKL', 'Descrição 2', 2, 2, 2, NULL, 2, 2, 2);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa, valor, parcelas, periodicidade, parcela_unica) VALUES (3, 'KCD', 'Registro 3', 3, 3, 3, NULL, 3, 3, 3);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa, valor, parcelas, periodicidade, parcela_unica) VALUES (4, 'WKN', 'Descrição 4', 4, 4, 4, NULL, 4, 4, 4);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa, valor, parcelas, periodicidade, parcela_unica) VALUES (5, 'ILF', 'Valor 5', 5, 5, 5, NULL, 5, 5, 5);

-- Cartorio
-- Cadastro de cartórios
INSERT INTO dbo.cartorio (id_cartorio, nome_cartorio, endereco, telefone) VALUES (1, 'Descrição 1', 'Rua B, 236', '778338978');
INSERT INTO dbo.cartorio (id_cartorio, nome_cartorio, endereco, telefone) VALUES (2, 'Descrição 2', 'Rua A, 5', '620258145');
INSERT INTO dbo.cartorio (id_cartorio, nome_cartorio, endereco, telefone) VALUES (3, 'Item 3', 'Avenida D, 763', '934095675');
INSERT INTO dbo.cartorio (id_cartorio, nome_cartorio, endereco, telefone) VALUES (4, 'Item 4', 'Avenida D, 229', '674530151');
INSERT INTO dbo.cartorio (id_cartorio, nome_cartorio, endereco, telefone) VALUES (5, 'Valor 5', 'Rua B, 640', '857032803');

-- Pessoa
-- Cadastro de pessoas (sócios/médicos)
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, rg, orgao_emissor, nascimento, pai, mae, nacionalidade, numero_dependente, endereco, cep, telefone, celular, email, numero_conselho, data_filiacao, inscricaoiss, inscricaoinss, classe_inss, agencia, conta, dv_conta, conta_poupanca, data_adesao, percentual, taxaespecial, consulta, procedimento, numero_parcelas_cota, valor_de_cada_cota, data_indicacao, data_desligamento, valor_pago_no_desligamento, data_inativo, observacao_pessoa, atualizacao, data_alteracao) VALUES (1, 'Descrição 1', '44577342095', 'Valor 1', 'Valor 1', '2025-05-24', 'Valor 1', 'Valor 1', 'Valor 1', 1, 'Rua E, 625', 'Valor 1', '123033468', 'Valor 1', 'user1@example.com', 1, '2025-06-25', 'Valor 1', 'Valor 1', 'Valor 1', 'Valor 1', 'Valor 1', 'Valor 1', 'false', '2025-12-19', '249,02', '856,93', '513,83', '818,78', 1, NULL, '2025-11-26 07:52:56', '2025-04-17', NULL, '2025-09-09 07:52:56', NULL, '2025-03-27', '2025-02-18 07:52:56');
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, rg, orgao_emissor, nascimento, pai, mae, nacionalidade, numero_dependente, endereco, cep, telefone, celular, email, numero_conselho, data_filiacao, inscricaoiss, inscricaoinss, classe_inss, agencia, conta, dv_conta, conta_poupanca, data_adesao, percentual, taxaespecial, consulta, procedimento, numero_parcelas_cota, valor_de_cada_cota, data_indicacao, data_desligamento, valor_pago_no_desligamento, data_inativo, observacao_pessoa, atualizacao, data_alteracao) VALUES (2, 'Item 2', '41214681680', 'Valor 2', 'Valor 2', '2025-10-12', 'Valor 2', 'Valor 2', 'Valor 2', 2, 'Rua B, 139', 'Valor 2', '140353401', 'Valor 2', 'user2@example.com', 2, '2025-06-20', 'Valor 2', 'Valor 2', 'Valor 2', 'Valor 2', 'Valor 2', 'Valor 2', 'false', '2025-08-29', '258,29', '210,02', '941,85', '911,59', 2, NULL, '2025-04-01 07:52:56', '2025-08-14', NULL, '2025-09-19 07:52:56', NULL, '2025-11-03', '2025-03-26 07:52:56');
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, rg, orgao_emissor, nascimento, pai, mae, nacionalidade, numero_dependente, endereco, cep, telefone, celular, email, numero_conselho, data_filiacao, inscricaoiss, inscricaoinss, classe_inss, agencia, conta, dv_conta, conta_poupanca, data_adesao, percentual, taxaespecial, consulta, procedimento, numero_parcelas_cota, valor_de_cada_cota, data_indicacao, data_desligamento, valor_pago_no_desligamento, data_inativo, observacao_pessoa, atualizacao, data_alteracao) VALUES (3, 'Item 3', '39447430891', 'Valor 3', 'Valor 3', '2025-05-10', 'Valor 3', 'Valor 3', 'Valor 3', 3, 'Rua B, 664', 'Valor 3', '387735117', 'Valor 3', 'user3@example.com', 3, '2025-11-12', 'Valor 3', 'Valor 3', 'Valor 3', 'Valor 3', 'Valor 3', 'Valor 3', 'false', '2025-10-10', '615,97', '519,66', '542,99', '140,76', 3, NULL, '2025-11-28 07:52:56', '2025-11-04', NULL, '2025-11-11 07:52:56', NULL, '2025-08-23', '2025-05-27 07:52:56');
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, rg, orgao_emissor, nascimento, pai, mae, nacionalidade, numero_dependente, endereco, cep, telefone, celular, email, numero_conselho, data_filiacao, inscricaoiss, inscricaoinss, classe_inss, agencia, conta, dv_conta, conta_poupanca, data_adesao, percentual, taxaespecial, consulta, procedimento, numero_parcelas_cota, valor_de_cada_cota, data_indicacao, data_desligamento, valor_pago_no_desligamento, data_inativo, observacao_pessoa, atualizacao, data_alteracao) VALUES (4, 'Descrição 4', '21095174132', 'Valor 4', 'Valor 4', '2025-09-29', 'Valor 4', 'Valor 4', 'Valor 4', 4, 'Rua E, 870', 'Valor 4', '942779814', 'Valor 4', 'user4@example.com', 4, '2025-09-07', 'Valor 4', 'Valor 4', 'Valor 4', 'Valor 4', 'Valor 4', 'Valor 4', 'false', '2025-07-06', '839,35', '688,88', '804,67', '653,43', 4, NULL, '2025-04-23 07:52:56', '2025-10-25', NULL, '2025-08-08 07:52:56', NULL, '2025-07-10', '2025-07-29 07:52:56');
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, rg, orgao_emissor, nascimento, pai, mae, nacionalidade, numero_dependente, endereco, cep, telefone, celular, email, numero_conselho, data_filiacao, inscricaoiss, inscricaoinss, classe_inss, agencia, conta, dv_conta, conta_poupanca, data_adesao, percentual, taxaespecial, consulta, procedimento, numero_parcelas_cota, valor_de_cada_cota, data_indicacao, data_desligamento, valor_pago_no_desligamento, data_inativo, observacao_pessoa, atualizacao, data_alteracao) VALUES (5, 'Registro 5', '96973673884', 'Valor 5', 'Valor 5', '2025-01-06', 'Valor 5', 'Valor 5', 'Valor 5', 5, 'Rua E, 625', 'Valor 5', '017749787', 'Valor 5', 'user5@example.com', 5, '2025-12-04', 'Valor 5', 'Valor 5', 'Valor 5', 'Valor 5', 'Valor 5', 'Valor 5', 1, '2025-02-26', '397,25', '941,39', '94,03', '855,54', 5, NULL, '2025-11-11 07:52:56', '2025-07-12', NULL, '2025-10-31 07:52:56', NULL, '2025-10-08', '2025-03-21 07:52:56');

-- Cliente
-- Cadastro de clientes
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, ie, im, data_contrato, taxaadm, endereco, cep, contato, fone1, fone2, email, observacao_cliente, obs_da_nota, pessoa_juridica) VALUES (1, 'Descrição 1', 'Valor 1', '57974966975974', 'Valor 1', 'Valor 1', '2025-07-12 07:52:56', 126.33, 'Rua A, 288', 'Valor 1', 'Valor 1', 'Valor 1', 'Valor 1', 'user1@example.com', NULL, NULL, 1);
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, ie, im, data_contrato, taxaadm, endereco, cep, contato, fone1, fone2, email, observacao_cliente, obs_da_nota, pessoa_juridica) VALUES (2, 'Valor 2', 'Valor 2', '37557949137351', 'Valor 2', 'Valor 2', '2025-07-12 07:52:56', 778.40, 'Rua B, 982', 'Valor 2', 'Valor 2', 'Valor 2', 'Valor 2', 'user2@example.com', NULL, NULL, 2);
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, ie, im, data_contrato, taxaadm, endereco, cep, contato, fone1, fone2, email, observacao_cliente, obs_da_nota, pessoa_juridica) VALUES (3, 'Descrição 3', 'Valor 3', '31669611510948', 'Valor 3', 'Valor 3', '2025-08-27 07:52:56', 264.64, 'Rua B, 894', 'Valor 3', 'Valor 3', 'Valor 3', 'Valor 3', 'user3@example.com', NULL, NULL, 3);
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, ie, im, data_contrato, taxaadm, endereco, cep, contato, fone1, fone2, email, observacao_cliente, obs_da_nota, pessoa_juridica) VALUES (4, 'Registro 4', 'Valor 4', '66697371481209', 'Valor 4', 'Valor 4', '2025-07-12 07:52:56', 790.31, 'Avenida D, 415', 'Valor 4', 'Valor 4', 'Valor 4', 'Valor 4', 'user4@example.com', NULL, NULL, 4);
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, ie, im, data_contrato, taxaadm, endereco, cep, contato, fone1, fone2, email, observacao_cliente, obs_da_nota, pessoa_juridica) VALUES (5, 'Item 5', 'Valor 5', '70599101774053', 'Valor 5', 'Valor 5', '2025-03-26 07:52:56', 25.57, 'Rua A, 233', 'Valor 5', 'Valor 5', 'Valor 5', 'Valor 5', 'user5@example.com', NULL, NULL, 5);

-- Lancamento
-- Lançamentos financeiros
INSERT INTO dbo.lancamento (id_lancamento, data, nf, valor_bruto, despesas, retencao, valor_liquido, valor_taxa, valor_repasse, taxa, mes_ano, baixa) VALUES (1, '2025-02-24 07:52:56', 1, NULL, NULL, NULL, NULL, NULL, NULL, 517.93, 'Valor 1', '2025-04-08 07:52:56');
INSERT INTO dbo.lancamento (id_lancamento, data, nf, valor_bruto, despesas, retencao, valor_liquido, valor_taxa, valor_repasse, taxa, mes_ano, baixa) VALUES (2, '2025-05-28 07:52:56', 2, NULL, NULL, NULL, NULL, NULL, NULL, 673.38, 'Valor 2', '2025-05-26 07:52:56');
INSERT INTO dbo.lancamento (id_lancamento, data, nf, valor_bruto, despesas, retencao, valor_liquido, valor_taxa, valor_repasse, taxa, mes_ano, baixa) VALUES (3, '2025-10-30 07:52:56', 3, NULL, NULL, NULL, NULL, NULL, NULL, 243.73, 'Valor 3', '2025-04-30 07:52:56');
INSERT INTO dbo.lancamento (id_lancamento, data, nf, valor_bruto, despesas, retencao, valor_liquido, valor_taxa, valor_repasse, taxa, mes_ano, baixa) VALUES (4, '2025-06-09 07:52:56', 4, NULL, NULL, NULL, NULL, NULL, NULL, 673.75, 'Valor 4', '2025-09-19 07:52:56');
INSERT INTO dbo.lancamento (id_lancamento, data, nf, valor_bruto, despesas, retencao, valor_liquido, valor_taxa, valor_repasse, taxa, mes_ano, baixa) VALUES (5, '2025-10-02 07:52:56', 5, NULL, NULL, NULL, NULL, NULL, NULL, 422.58, 'Valor 5', '2025-05-20 07:52:56');

-- NF
-- Notas fiscais
INSERT INTO dbo.nf (id_empresa, nf, emissao, vencimento, total, irrf, pis, cofins, csll, iss, baixa, valor_quitado, cancelada, observacao, observacao_baixa, outras_despesas) VALUES (1, 1, '2025-10-12 07:52:56', '2025-04-25 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-07-16 07:52:56', NULL, FALSE, 'Valor 1', 'Valor 1', NULL);
INSERT INTO dbo.nf (id_empresa, nf, emissao, vencimento, total, irrf, pis, cofins, csll, iss, baixa, valor_quitado, cancelada, observacao, observacao_baixa, outras_despesas) VALUES (1, 2, '2025-10-30 07:52:56', '2025-10-29 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-07-15 07:52:56', NULL, FALSE, 'Valor 2', 'Valor 2', NULL);
INSERT INTO dbo.nf (id_empresa, nf, emissao, vencimento, total, irrf, pis, cofins, csll, iss, baixa, valor_quitado, cancelada, observacao, observacao_baixa, outras_despesas) VALUES (1, 3, '2025-06-04 07:52:56', '2025-03-30 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-02-19 07:52:56', NULL, TRUE, 'Valor 3', 'Valor 3', NULL);
INSERT INTO dbo.nf (id_empresa, nf, emissao, vencimento, total, irrf, pis, cofins, csll, iss, baixa, valor_quitado, cancelada, observacao, observacao_baixa, outras_despesas) VALUES (1, 4, '2025-08-26 07:52:56', '2025-02-15 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-06-02 07:52:56', NULL, TRUE, 'Valor 4', 'Valor 4', NULL);
INSERT INTO dbo.nf (id_empresa, nf, emissao, vencimento, total, irrf, pis, cofins, csll, iss, baixa, valor_quitado, cancelada, observacao, observacao_baixa, outras_despesas) VALUES (1, 5, '2025-11-13 07:52:56', '2025-04-20 07:52:56', NULL, NULL, NULL, NULL, NULL, NULL, '2025-08-06 07:52:56', NULL, FALSE, 'Valor 5', 'Valor 5', NULL);

-- ContasPagarReceber
-- Contas a pagar e receber
INSERT INTO dbo.contas_pagar_receber (id_contas_pagar_receber, data_lancamento, valor_original, valor_parcela, data_vencimento, data_baixa, valor_baixado, mes_ano_referencia, historico) VALUES (1, '2025-07-30 07:52:56', NULL, NULL, '2025-01-20 07:52:56', '2025-06-24 07:52:56', NULL, 'Valor 1', 'Valor 1');
INSERT INTO dbo.contas_pagar_receber (id_contas_pagar_receber, data_lancamento, valor_original, valor_parcela, data_vencimento, data_baixa, valor_baixado, mes_ano_referencia, historico) VALUES (2, '2025-09-07 07:52:56', NULL, NULL, '2025-11-18 07:52:56', '2025-08-26 07:52:56', NULL, 'Valor 2', 'Valor 2');
INSERT INTO dbo.contas_pagar_receber (id_contas_pagar_receber, data_lancamento, valor_original, valor_parcela, data_vencimento, data_baixa, valor_baixado, mes_ano_referencia, historico) VALUES (3, '2025-06-17 07:52:56', NULL, NULL, '2024-12-22 07:52:56', '2025-08-15 07:52:56', NULL, 'Valor 3', 'Valor 3');
INSERT INTO dbo.contas_pagar_receber (id_contas_pagar_receber, data_lancamento, valor_original, valor_parcela, data_vencimento, data_baixa, valor_baixado, mes_ano_referencia, historico) VALUES (4, '2025-10-13 07:52:56', NULL, NULL, '2025-12-16 07:52:56', '2025-12-14 07:52:56', NULL, 'Valor 4', 'Valor 4');
INSERT INTO dbo.contas_pagar_receber (id_contas_pagar_receber, data_lancamento, valor_original, valor_parcela, data_vencimento, data_baixa, valor_baixado, mes_ano_referencia, historico) VALUES (5, '2025-09-01 07:52:56', NULL, NULL, '2025-03-18 07:52:56', '2025-08-01 07:52:56', NULL, 'Valor 5', 'Valor 5');

-- PagamentoNaoSocio
-- Pagamentos para não sócios
INSERT INTO dbo.pagamento_nao_socio (data, id_empresa, id_cliente, id_pessoa_nao_socio, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse) VALUES ('2025-05-07 07:52:56', 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.pagamento_nao_socio (data, id_empresa, id_cliente, id_pessoa_nao_socio, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse) VALUES ('2025-09-22 07:52:56', 1, 2, 2, 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.pagamento_nao_socio (data, id_empresa, id_cliente, id_pessoa_nao_socio, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse) VALUES ('2025-11-30 07:52:56', 1, 3, 3, 3, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.pagamento_nao_socio (data, id_empresa, id_cliente, id_pessoa_nao_socio, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse) VALUES ('2025-04-17 07:52:56', 1, 4, 4, 4, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.pagamento_nao_socio (data, id_empresa, id_cliente, id_pessoa_nao_socio, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse) VALUES ('2025-09-08 07:52:56', 1, 5, 5, 5, NULL, NULL, NULL, NULL, NULL);

-- RepasseAnual
-- Repasse anual consolidado
INSERT INTO dbo.repasse_anual (ano, id_empresa, id_pessoa, jan_bruto, jan_taxa, fev_bruto, fev_taxa, mar_bruto, mar_taxa, abr_bruto, abr_taxa, mai_bruto, mai_taxa, jun_bruto, jun_taxa, jul_bruto, jul_taxa, ago_bruto, ago_taxa, set_bruto, set_taxa, out_bruto, out_taxa, nov_bruto, nov_taxa, dez_bruto, dez_taxa) VALUES ('Valor 1', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.repasse_anual (ano, id_empresa, id_pessoa, jan_bruto, jan_taxa, fev_bruto, fev_taxa, mar_bruto, mar_taxa, abr_bruto, abr_taxa, mai_bruto, mai_taxa, jun_bruto, jun_taxa, jul_bruto, jul_taxa, ago_bruto, ago_taxa, set_bruto, set_taxa, out_bruto, out_taxa, nov_bruto, nov_taxa, dez_bruto, dez_taxa) VALUES ('Valor 2', 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.repasse_anual (ano, id_empresa, id_pessoa, jan_bruto, jan_taxa, fev_bruto, fev_taxa, mar_bruto, mar_taxa, abr_bruto, abr_taxa, mai_bruto, mai_taxa, jun_bruto, jun_taxa, jul_bruto, jul_taxa, ago_bruto, ago_taxa, set_bruto, set_taxa, out_bruto, out_taxa, nov_bruto, nov_taxa, dez_bruto, dez_taxa) VALUES ('Valor 3', 1, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.repasse_anual (ano, id_empresa, id_pessoa, jan_bruto, jan_taxa, fev_bruto, fev_taxa, mar_bruto, mar_taxa, abr_bruto, abr_taxa, mai_bruto, mai_taxa, jun_bruto, jun_taxa, jul_bruto, jul_taxa, ago_bruto, ago_taxa, set_bruto, set_taxa, out_bruto, out_taxa, nov_bruto, nov_taxa, dez_bruto, dez_taxa) VALUES ('Valor 4', 1, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO dbo.repasse_anual (ano, id_empresa, id_pessoa, jan_bruto, jan_taxa, fev_bruto, fev_taxa, mar_bruto, mar_taxa, abr_bruto, abr_taxa, mai_bruto, mai_taxa, jun_bruto, jun_taxa, jul_bruto, jul_taxa, ago_bruto, ago_taxa, set_bruto, set_taxa, out_bruto, out_taxa, nov_bruto, nov_taxa, dez_bruto, dez_taxa) VALUES ('Valor 5', 1, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- AnuidadeCremeb
-- Anuidades do CREMEB
INSERT INTO dbo.anuidade_cremeb (id_anuidade_cremeb, ano_exercicio, data_inicio, data_fim, valor_total) VALUES (1, 'Valor 1', '2025-02-15 07:52:56', '2025-09-01 07:52:56', NULL);
INSERT INTO dbo.anuidade_cremeb (id_anuidade_cremeb, ano_exercicio, data_inicio, data_fim, valor_total) VALUES (2, 'Valor 2', '2025-08-05 07:52:56', '2025-08-02 07:52:56', NULL);
INSERT INTO dbo.anuidade_cremeb (id_anuidade_cremeb, ano_exercicio, data_inicio, data_fim, valor_total) VALUES (3, 'Valor 3', '2025-12-19 07:52:56', '2025-04-28 07:52:56', NULL);
INSERT INTO dbo.anuidade_cremeb (id_anuidade_cremeb, ano_exercicio, data_inicio, data_fim, valor_total) VALUES (4, 'Valor 4', '2025-07-31 07:52:56', '2025-12-12 07:52:56', NULL);
INSERT INTO dbo.anuidade_cremeb (id_anuidade_cremeb, ano_exercicio, data_inicio, data_fim, valor_total) VALUES (5, 'Valor 5', '2025-06-23 07:52:56', '2025-06-15 07:52:56', NULL);

-- MedicoEspecialidade
-- Relacionamento médico-especialidade
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (1, 1);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (2, 2);

-- EmpresaDespesaFixa
-- Despesas fixas por empresa
INSERT INTO dbo.empresa_despesa_fixa (id_empresa, id_despesa_receita, data_lancamento, parcelas, valor_empresa, inativa) VALUES (1, 1, '2025-03-31 07:52:56', 1, NULL, 1);
INSERT INTO dbo.empresa_despesa_fixa (id_empresa, id_despesa_receita, data_lancamento, parcelas, valor_empresa, inativa) VALUES (1, 2, '2025-11-12 07:52:56', 2, NULL, 2);
INSERT INTO dbo.empresa_despesa_fixa (id_empresa, id_despesa_receita, data_lancamento, parcelas, valor_empresa, inativa) VALUES (1, 3, '2025-03-03 07:52:56', 3, NULL, 3);

-- EmpresaCliente
-- Relacionamento empresa-cliente com taxas
INSERT INTO dbo.empresa_cliente (id_empresa, id_cliente, taxa, processo, taxaiss, taxacofins, taxapis, taxacsll, taxairrf) VALUES (1, 1, 456.73, 'Valor 1', 364.17, 931.64, 819.49, 605.04, 301.41);
INSERT INTO dbo.empresa_cliente (id_empresa, id_cliente, taxa, processo, taxaiss, taxacofins, taxapis, taxacsll, taxairrf) VALUES (1, 2, 14.84, 'Valor 2', 684.50, 261.22, 926.22, 376.15, 583.15);
INSERT INTO dbo.empresa_cliente (id_empresa, id_cliente, taxa, processo, taxaiss, taxacofins, taxapis, taxacsll, taxairrf) VALUES (1, 3, 405.50, 'Valor 3', 910.40, 889.60, 22.55, 754.25, 221.00);

-- Retencao
-- Faixas de retenção por plano
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (1, 687.91, 617.93);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (2, 346.72, 34.52);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (3, 268.90, 812.02);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (4, 350.37, 898.18);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (5, 698.08, 738.55);

-- PessoaCartorio
-- Relacionamento pessoa-cartório
INSERT INTO dbo.pessoa_cartorio (id_pessoa, id_cartorio) VALUES (1, 1);
INSERT INTO dbo.pessoa_cartorio (id_pessoa, id_cartorio) VALUES (2, 2);

-- PessoaContaRecebimento
-- Conta de recebimento por pessoa e cliente
INSERT INTO dbo.pessoa_conta_recebimento (id_pessoa, id_cliente, id_conta_corrente) VALUES (1, 1, 1);
INSERT INTO dbo.pessoa_conta_recebimento (id_pessoa, id_cliente, id_conta_corrente) VALUES (2, 2, 2);

-- PessoaContaCorrente
-- Contas correntes da pessoa
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, nome_dependente, pix) VALUES (1, 'Valor 1', 'Valor 1', 'Valor 1', FALSE, TRUE, TRUE, 'Descrição 1', 'Valor 1');
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, nome_dependente, pix) VALUES (2, 'Valor 2', 'Valor 2', 'Valor 2', TRUE, TRUE, TRUE, 'Descrição 2', 'Valor 2');

-- ClienteContato
-- Contatos do cliente
INSERT INTO dbo.cliente_contato (id_cliente_contato, descricao) VALUES (1, 'Descrição do registro 1');
INSERT INTO dbo.cliente_contato (id_cliente_contato, descricao) VALUES (2, 'Descrição do registro 2');

-- AnuidadeCremebItem
-- Itens individuais da anuidade CREMEB
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, data_lancamento, valor_individual, data_pagamento) VALUES (1, '2024-12-21 07:52:56', NULL, 'Valor 1');
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, data_lancamento, valor_individual, data_pagamento) VALUES (2, '2025-01-21 07:52:56', NULL, 'Valor 2');

-- Adiantamento
-- Adiantamentos de pagamentos
INSERT INTO dbo.adiantamento (id_adiantamento, data, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse, pago) VALUES (1, '2025-08-04 07:52:56', 1, NULL, NULL, NULL, NULL, NULL, FALSE);
INSERT INTO dbo.adiantamento (id_adiantamento, data, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse, pago) VALUES (2, '2025-09-20 07:52:56', 2, NULL, NULL, NULL, NULL, NULL, FALSE);
INSERT INTO dbo.adiantamento (id_adiantamento, data, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse, pago) VALUES (3, '2025-06-02 07:52:56', 3, NULL, NULL, NULL, NULL, NULL, TRUE);
INSERT INTO dbo.adiantamento (id_adiantamento, data, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse, pago) VALUES (4, '2025-04-20 07:52:56', 4, NULL, NULL, NULL, NULL, NULL, FALSE);
INSERT INTO dbo.adiantamento (id_adiantamento, data, nf, valor_bruto, retencao, valor_liquido, valor_taxa, valor_repasse, pago) VALUES (5, '2025-02-11 07:52:56', 5, NULL, NULL, NULL, NULL, NULL, TRUE);

-- EmpresaSocio
-- Sócios da empresa
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, data_saida, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 1, '2025-04-15 07:52:56', 1, NULL, '2025-05-06 07:52:56', NULL, TRUE, 627.96, 684.62);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, data_saida, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 2, '2025-07-25 07:52:56', 2, NULL, '2025-03-23 07:52:56', NULL, FALSE, 896.15, 568.80);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, data_saida, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 3, '2025-02-03 07:52:56', 3, NULL, '2025-02-06 07:52:56', NULL, TRUE, 177.36, 685.31);

-- ClienteFilial
-- Filiais do cliente
INSERT INTO dbo.cliente_filial (id_cliente_filial, nome_filial) VALUES (1, 'Descrição 1');
INSERT INTO dbo.cliente_filial (id_cliente_filial, nome_filial) VALUES (2, 'Descrição 2');


-- ============================================================
-- RESET AUTO-INCREMENT SEQUENCES
-- ============================================================
ALTER TABLE dbo.bairro ALTER COLUMN id_bairro RESTART WITH 11;
ALTER TABLE dbo.cidade ALTER COLUMN id_cidade RESTART WITH 11;
ALTER TABLE dbo.conselho ALTER COLUMN id_conselho RESTART WITH 6;
ALTER TABLE dbo.especialidade ALTER COLUMN id_especialidade RESTART WITH 16;
ALTER TABLE dbo.operadora ALTER COLUMN id_operadora RESTART WITH 11;
ALTER TABLE dbo.usuario ALTER COLUMN id_usuario RESTART WITH 3;
ALTER TABLE dbo.parametro_email ALTER COLUMN id_parametro RESTART WITH 2;
ALTER TABLE dbo.indicacao ALTER COLUMN id_indicacao RESTART WITH 4;
ALTER TABLE dbo.empresa ALTER COLUMN id_empresa RESTART WITH 4;
ALTER TABLE dbo.plano_retencao ALTER COLUMN id_plano_retencao RESTART WITH 6;
ALTER TABLE dbo.despesa_receita ALTER COLUMN id_despesa_receita RESTART WITH 6;
ALTER TABLE dbo.cartorio ALTER COLUMN id_cartorio RESTART WITH 6;
ALTER TABLE dbo.pessoa ALTER COLUMN id_pessoa RESTART WITH 6;
ALTER TABLE dbo.cliente ALTER COLUMN id_cliente RESTART WITH 6;
ALTER TABLE dbo.lancamento ALTER COLUMN id_lancamento RESTART WITH 6;
ALTER TABLE dbo.nf ALTER COLUMN id_empresa RESTART WITH 2;
ALTER TABLE dbo.contas_pagar_receber ALTER COLUMN id_contas_pagar_receber RESTART WITH 6;
ALTER TABLE dbo.anuidade_cremeb ALTER COLUMN id_anuidade_cremeb RESTART WITH 6;
ALTER TABLE dbo.medico_especialidade ALTER COLUMN id_pessoa RESTART WITH 3;
ALTER TABLE dbo.empresa_despesa_fixa ALTER COLUMN id_empresa RESTART WITH 2;
ALTER TABLE dbo.empresa_cliente ALTER COLUMN id_empresa RESTART WITH 2;
ALTER TABLE dbo.retencao ALTER COLUMN id_plano_retencao RESTART WITH 6;
ALTER TABLE dbo.pessoa_cartorio ALTER COLUMN id_pessoa RESTART WITH 3;
ALTER TABLE dbo.pessoa_conta_recebimento ALTER COLUMN id_pessoa RESTART WITH 3;
ALTER TABLE dbo.pessoa_conta_corrente ALTER COLUMN id_conta_corrente RESTART WITH 3;
ALTER TABLE dbo.cliente_contato ALTER COLUMN id_cliente_contato RESTART WITH 3;
ALTER TABLE dbo.anuidade_cremeb_item ALTER COLUMN id_anuidade_cremeb_item RESTART WITH 3;
ALTER TABLE dbo.adiantamento ALTER COLUMN id_adiantamento RESTART WITH 6;
ALTER TABLE dbo.empresa_socio ALTER COLUMN id_empresa RESTART WITH 2;
ALTER TABLE dbo.cliente_filial ALTER COLUMN id_cliente_filial RESTART WITH 3;
