-- ============================================================
-- CARGA INICIAL - XANDEL
-- Gerado automaticamente a partir do metamodelo
-- Para H2 Database (Spring Boot Dev)
-- ============================================================

-- ============================================================
-- TABELAS DE LOOKUP (Cadastros Auxiliares)
-- ============================================================

-- Banco
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('001', 'Banco do Brasil');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('033', 'Santander');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('104', 'Caixa Economica Federal');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('237', 'Bradesco');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('341', 'Itau Unibanco');
INSERT INTO dbo.banco (id_banco, nome_banco) VALUES ('756', 'Sicoob');

-- Bairro
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
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (1, 'Conselho Regional de Medicina da Bahia', 'CREMEB');
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (2, 'Conselho Regional de Enfermagem', 'COREN');
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (3, 'Conselho Regional de Odontologia', 'CRO');
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (4, 'Conselho Regional de Psicologia', 'CRP');
INSERT INTO dbo.conselho (id_conselho, nome_conselho, sigla) VALUES (5, 'Conselho Regional de Fisioterapia', 'CREFITO');

-- Especialidade
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
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (1, 'Solteiro(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (2, 'Casado(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (3, 'Divorciado(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (4, 'Viuvo(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (5, 'Separado(a)');
INSERT INTO dbo.estado_civil (id_estado_civil, nome_estado_civil) VALUES (6, 'Uniao Estavel');

-- Operadora
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
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (1, 'Consulta');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (2, 'Exame');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (3, 'Procedimento');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (4, 'Cirurgia');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (5, 'Internacao');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (6, 'Urgencia');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (7, 'Emergencia');
INSERT INTO dbo.tipo_servico (id_tipo_servico, nome_tipo_servico) VALUES (8, 'Homecare');

-- TipoContato
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (1, 'Telefone Fixo');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (2, 'Celular');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (3, 'Email');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (4, 'WhatsApp');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (5, 'Fax');
INSERT INTO dbo.tipo_contato (id_tipo_contato, nome_tipo_contato) VALUES (6, 'Recado');

-- CBO
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

-- ============================================================
-- TABELAS DE CONFIGURACAO
-- ============================================================

-- Usuario
INSERT INTO dbo.usuario (id_usuario, usuario_nome, usuario_login, usuario_senha, usuario_nivel_acesso, usuario_inativo) VALUES (1, 'Administrador', 'admin', 'admin', 1, FALSE);
INSERT INTO dbo.usuario (id_usuario, usuario_nome, usuario_login, usuario_senha, usuario_nivel_acesso, usuario_inativo) VALUES (2, 'Usuario Padrao', 'user', 'user', 2, FALSE);

-- PlanoRetencao
INSERT INTO dbo.plano_retencao (id_plano_retencao, nome_plano_retencao, inativo, libera_despesas) VALUES (1, 'Plano Padrao', FALSE, FALSE);
INSERT INTO dbo.plano_retencao (id_plano_retencao, nome_plano_retencao, inativo, libera_despesas) VALUES (2, 'Plano Senior', FALSE, TRUE);
INSERT INTO dbo.plano_retencao (id_plano_retencao, nome_plano_retencao, inativo, libera_despesas) VALUES (3, 'Plano Junior', FALSE, FALSE);

-- Retencao (Faixas de retencao por plano)
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (1, 5000.00, 10.00);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (1, 10000.00, 8.00);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (1, 999999.99, 5.00);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (2, 10000.00, 8.00);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (2, 999999.99, 5.00);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (3, 3000.00, 12.00);
INSERT INTO dbo.retencao (id_plano_retencao, ate, reter) VALUES (3, 999999.99, 10.00);

-- DespesaReceita
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (1, 'ALG', 'Aluguel', 1, 1, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (2, 'LUZ', 'Energia Eletrica', 1, 1, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (3, 'TEL', 'Telefone', 1, 1, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (4, 'INT', 'Internet', 1, 1, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (5, 'MAT', 'Material de Escritorio', 1, 0, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (6, 'LIMP', 'Servico de Limpeza', 1, 1, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (7, 'CONT', 'Contabilidade', 1, 1, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (8, 'SEG', 'Seguro', 1, 1, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (9, 'HON', 'Honorarios Medicos', 0, 0, 0);
INSERT INTO dbo.despesa_receita (id_despesa_receita, sigla_despesa_receita, nome_despesa_receita, despesa, tem_rateio, inativa) VALUES (10, 'TAXA', 'Taxa Administrativa', 0, 0, 0);

-- ImpostoDeRenda (Tabela IR 2025)
INSERT INTO dbo.imposto_de_renda (id_imposto_de_renda, ate, aliquota, parcela_deduzir) VALUES (1, 2259.20, 0.00, 0.00);
INSERT INTO dbo.imposto_de_renda (id_imposto_de_renda, ate, aliquota, parcela_deduzir) VALUES (2, 2826.65, 7.50, 169.44);
INSERT INTO dbo.imposto_de_renda (id_imposto_de_renda, ate, aliquota, parcela_deduzir) VALUES (3, 3751.05, 15.00, 381.44);
INSERT INTO dbo.imposto_de_renda (id_imposto_de_renda, ate, aliquota, parcela_deduzir) VALUES (4, 4664.68, 22.50, 662.77);
INSERT INTO dbo.imposto_de_renda (id_imposto_de_renda, ate, aliquota, parcela_deduzir) VALUES (5, 999999.99, 27.50, 896.00);

-- Indicacao
INSERT INTO dbo.indicacao (id_indicacao, nome_indicacao) VALUES (1, 'Indicacao de Socio');
INSERT INTO dbo.indicacao (id_indicacao, nome_indicacao) VALUES (2, 'Site');
INSERT INTO dbo.indicacao (id_indicacao, nome_indicacao) VALUES (3, 'Redes Sociais');
INSERT INTO dbo.indicacao (id_indicacao, nome_indicacao) VALUES (4, 'Google');
INSERT INTO dbo.indicacao (id_indicacao, nome_indicacao) VALUES (5, 'Outros');

-- Cartorio
INSERT INTO dbo.cartorio (id_cartorio, nome_cartorio, endereco, id_bairro, id_cidade, telefone) VALUES (1, '1o Cartorio de Notas de Salvador', 'Av. Sete de Setembro, 100', 1, 1, '32450000');
INSERT INTO dbo.cartorio (id_cartorio, nome_cartorio, endereco, id_bairro, id_cidade, telefone) VALUES (2, '2o Cartorio de Notas de Salvador', 'Rua Chile, 50', 1, 1, '32451111');
INSERT INTO dbo.cartorio (id_cartorio, nome_cartorio, endereco, id_bairro, id_cidade, telefone) VALUES (3, 'Cartorio da Pituba', 'Av. Paulo VI, 200', 2, 1, '33410000');

-- ============================================================
-- TABELAS PRINCIPAIS - EMPRESA ALPHA (id=1)
-- ============================================================

-- Empresa
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (1, 'Sociedade Medica Alpha Ltda', 'Alpha Med', '12345678000100', 10.00, FALSE);
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (2, 'Clinica Beta Servicos Medicos', 'Beta Clinica', '98765432000199', 8.50, FALSE);
INSERT INTO dbo.empresa (id_empresa, nome_empresa, fantasia_empresa, cnpj, taxa_retencao, inativa) VALUES (3, 'Centro Medico Gamma S/S', 'Gamma Saude', '11222333000144', 12.00, FALSE);

-- EmpresaDespesaFixa (Despesas fixas da Alpha)
INSERT INTO dbo.empresa_despesa_fixa (id_empresa, id_despesa_receita, data_lancamento, parcelas, valor_empresa, inativa) VALUES (1, 1, '2025-01-01', 12, 5000.00, 0);
INSERT INTO dbo.empresa_despesa_fixa (id_empresa, id_despesa_receita, data_lancamento, parcelas, valor_empresa, inativa) VALUES (1, 2, '2025-01-01', 12, 800.00, 0);
INSERT INTO dbo.empresa_despesa_fixa (id_empresa, id_despesa_receita, data_lancamento, parcelas, valor_empresa, inativa) VALUES (1, 3, '2025-01-01', 12, 300.00, 0);
INSERT INTO dbo.empresa_despesa_fixa (id_empresa, id_despesa_receita, data_lancamento, parcelas, valor_empresa, inativa) VALUES (1, 4, '2025-01-01', 12, 250.00, 0);
INSERT INTO dbo.empresa_despesa_fixa (id_empresa, id_despesa_receita, data_lancamento, parcelas, valor_empresa, inativa) VALUES (1, 7, '2025-01-01', 12, 1500.00, 0);

-- Cliente (Clientes/Hospitais)
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, endereco, id_bairro, id_cidade, cep, telefone, inativo, id_indicacao) VALUES (1, 'Hospital Sao Rafael', 'HSR', '11111111000111', 'Av. Sao Rafael, 2152', 7, 1, '41253190', '321010000', FALSE, 1);
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, endereco, id_bairro, id_cidade, cep, telefone, inativo, id_indicacao) VALUES (2, 'Hospital da Bahia', 'HBA', '22222222000122', 'Av. Prof. Magalhaes Neto, 1541', 2, 1, '41810012', '321020000', FALSE, 1);
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, endereco, id_bairro, id_cidade, cep, telefone, inativo, id_indicacao) VALUES (3, 'Hospital Alianca', 'HAL', '33333333000133', 'Av. Juracy Magalhaes Jr, 2096', 10, 1, '41940060', '321030000', FALSE, 2);
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, endereco, id_bairro, id_cidade, cep, telefone, inativo, id_indicacao) VALUES (4, 'Hospital Portugues', 'HPO', '44444444000144', 'Av. Princesa Isabel, 914', 3, 1, '40130000', '321040000', FALSE, 1);
INSERT INTO dbo.cliente (id_cliente, nome_cliente, fantasia_cliente, cnpj, endereco, id_bairro, id_cidade, cep, telefone, inativo, id_indicacao) VALUES (5, 'Hospital Santa Izabel', 'HSI', '55555555000155', 'Praca Conselheiro Almeida Couto, 500', 1, 1, '40050410', '321050000', FALSE, 1);

-- ClienteFilial (Filiais dos clientes)
INSERT INTO dbo.cliente_filial (id_cliente_filial, id_cliente, nome_filial) VALUES (1, 1, 'HSR - Unidade Paralela');
INSERT INTO dbo.cliente_filial (id_cliente_filial, id_cliente, nome_filial) VALUES (2, 1, 'HSR - Unidade Ondina');
INSERT INTO dbo.cliente_filial (id_cliente_filial, id_cliente, nome_filial) VALUES (3, 2, 'HBA - Ambulatorio');
INSERT INTO dbo.cliente_filial (id_cliente_filial, id_cliente, nome_filial) VALUES (4, 3, 'HAL - Centro Medico');

-- ClienteContato (Contatos dos clientes)
INSERT INTO dbo.cliente_contato (id_cliente_contato, id_cliente, id_tipo_contato, descricao) VALUES (1, 1, 1, '71 3281-6000');
INSERT INTO dbo.cliente_contato (id_cliente_contato, id_cliente, id_tipo_contato, descricao) VALUES (2, 1, 3, 'contato@hsr.com.br');
INSERT INTO dbo.cliente_contato (id_cliente_contato, id_cliente, id_tipo_contato, descricao) VALUES (3, 2, 1, '71 3276-8000');
INSERT INTO dbo.cliente_contato (id_cliente_contato, id_cliente, id_tipo_contato, descricao) VALUES (4, 2, 3, 'atendimento@hba.com.br');
INSERT INTO dbo.cliente_contato (id_cliente_contato, id_cliente, id_tipo_contato, descricao) VALUES (5, 3, 1, '71 2108-5600');
INSERT INTO dbo.cliente_contato (id_cliente_contato, id_cliente, id_tipo_contato, descricao) VALUES (6, 3, 4, '71 99999-1111');
INSERT INTO dbo.cliente_contato (id_cliente_contato, id_cliente, id_tipo_contato, descricao) VALUES (7, 4, 1, '71 3203-5555');
INSERT INTO dbo.cliente_contato (id_cliente_contato, id_cliente, id_tipo_contato, descricao) VALUES (8, 5, 1, '71 2203-8100');

-- EmpresaCliente (Relacionamento Empresa Alpha com Clientes)
INSERT INTO dbo.empresa_cliente (id_empresa, id_cliente, taxa, processo, taxa_iss, taxa_cofins, taxa_pis, taxa_csll, taxa_irrf) VALUES (1, 1, 5.00, 'PROC-2024-001', 5.00, 3.00, 0.65, 1.00, 1.50);
INSERT INTO dbo.empresa_cliente (id_empresa, id_cliente, taxa, processo, taxa_iss, taxa_cofins, taxa_pis, taxa_csll, taxa_irrf) VALUES (1, 2, 5.50, 'PROC-2024-002', 5.00, 3.00, 0.65, 1.00, 1.50);
INSERT INTO dbo.empresa_cliente (id_empresa, id_cliente, taxa, processo, taxa_iss, taxa_cofins, taxa_pis, taxa_csll, taxa_irrf) VALUES (1, 3, 6.00, 'PROC-2024-003', 5.00, 3.00, 0.65, 1.00, 1.50);
INSERT INTO dbo.empresa_cliente (id_empresa, id_cliente, taxa, processo, taxa_iss, taxa_cofins, taxa_pis, taxa_csll, taxa_irrf) VALUES (1, 4, 5.00, 'PROC-2024-004', 5.00, 3.00, 0.65, 1.00, 1.50);
INSERT INTO dbo.empresa_cliente (id_empresa, id_cliente, taxa, processo, taxa_iss, taxa_cofins, taxa_pis, taxa_csll, taxa_irrf) VALUES (1, 5, 4.50, 'PROC-2024-005', 5.00, 3.00, 0.65, 1.00, 1.50);

-- Pessoa (Medicos/Socios da Alpha)
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, id_conselho, numero_conselho, nascimento, rg, email, telefone, celular, endereco, id_bairro, id_cidade, cep, id_estado_civil, data_adesao, id_banco, agencia, conta, id_plano_retencao, id_operadora) VALUES (1, 'Dr. Carlos Alberto Silva', '11111111111', 1, 12345, '1970-05-15', '1234567890', 'carlos.silva@email.com', '32451111', '999111111', 'Rua das Flores, 100', 2, 1, '41810000', 2, '2015-01-01', '001', '1234', '123456', 1, 1);
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, id_conselho, numero_conselho, nascimento, rg, email, telefone, celular, endereco, id_bairro, id_cidade, cep, id_estado_civil, data_adesao, id_banco, agencia, conta, id_plano_retencao, id_operadora) VALUES (2, 'Dra. Maria Fernanda Santos', '22222222222', 1, 23456, '1975-08-20', '2345678901', 'maria.santos@email.com', '32452222', '999222222', 'Av. Tancredo Neves, 200', 6, 1, '41820000', 2, '2016-03-15', '237', '5678', '234567', 1, 2);
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, id_conselho, numero_conselho, nascimento, rg, email, telefone, celular, endereco, id_bairro, id_cidade, cep, id_estado_civil, data_adesao, id_banco, agencia, conta, id_plano_retencao, id_operadora) VALUES (3, 'Dr. Jose Roberto Oliveira', '33333333333', 1, 34567, '1968-12-10', '3456789012', 'jose.oliveira@email.com', '32453333', '999333333', 'Rua Minas Gerais, 300', 2, 1, '41830000', 3, '2014-06-01', '341', '9012', '345678', 2, 1);
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, id_conselho, numero_conselho, nascimento, rg, email, telefone, celular, endereco, id_bairro, id_cidade, cep, id_estado_civil, data_adesao, id_banco, agencia, conta, id_plano_retencao, id_operadora) VALUES (4, 'Dra. Ana Paula Costa', '44444444444', 1, 45678, '1980-03-25', '4567890123', 'ana.costa@email.com', '32454444', '999444444', 'Av. Paulo VI, 400', 2, 1, '41840000', 1, '2018-09-01', '033', '1111', '456789', 1, 3);
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, id_conselho, numero_conselho, nascimento, rg, email, telefone, celular, endereco, id_bairro, id_cidade, cep, id_estado_civil, data_adesao, id_banco, agencia, conta, id_plano_retencao, id_operadora) VALUES (5, 'Dr. Pedro Henrique Lima', '55555555555', 1, 56789, '1972-07-08', '5678901234', 'pedro.lima@email.com', '32455555', '999555555', 'Rua Amazonas, 500', 5, 1, '41850000', 2, '2017-02-01', '104', '2222', '567890', 2, 1);
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, id_conselho, numero_conselho, nascimento, rg, email, telefone, celular, endereco, id_bairro, id_cidade, cep, id_estado_civil, data_adesao, id_banco, agencia, conta, id_plano_retencao, id_operadora) VALUES (6, 'Dra. Juliana Almeida', '66666666666', 1, 67890, '1985-11-30', '6789012345', 'juliana.almeida@email.com', '32456666', '999666666', 'Av. ACM, 600', 5, 1, '41860000', 1, '2019-05-01', '756', '3333', '678901', 3, 4);
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, id_conselho, numero_conselho, nascimento, rg, email, telefone, celular, endereco, id_bairro, id_cidade, cep, id_estado_civil, data_adesao, id_banco, agencia, conta, id_plano_retencao, id_operadora) VALUES (7, 'Dr. Ricardo Souza', '77777777777', 1, 78901, '1965-04-12', '7890123456', 'ricardo.souza@email.com', '32457777', '999777777', 'Rua Rio de Janeiro, 700', 8, 1, '41870000', 2, '2013-08-01', '001', '4444', '789012', 1, 5);
INSERT INTO dbo.pessoa (id_pessoa, nome, cpf, id_conselho, numero_conselho, nascimento, rg, email, telefone, celular, endereco, id_bairro, id_cidade, cep, id_estado_civil, data_adesao, id_banco, agencia, conta, id_plano_retencao, id_operadora) VALUES (8, 'Dra. Fernanda Ribeiro', '88888888888', 1, 89012, '1978-09-18', '8901234567', 'fernanda.ribeiro@email.com', '32458888', '999888888', 'Av. Garibaldi, 800', 9, 1, '41880000', 6, '2020-01-15', '237', '5555', '890123', 2, 1);

-- MedicoEspecialidade (Especialidades dos medicos)
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (1, 1);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (1, 2);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (2, 4);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (3, 12);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (3, 5);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (4, 6);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (5, 7);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (5, 11);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (6, 3);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (7, 8);
INSERT INTO dbo.medico_especialidade (id_pessoa, id_especialidade) VALUES (8, 15);

-- PessoaCartorio (Cartorios vinculados aos medicos)
INSERT INTO dbo.pessoa_cartorio (id_pessoa, id_cartorio) VALUES (1, 1);
INSERT INTO dbo.pessoa_cartorio (id_pessoa, id_cartorio) VALUES (2, 1);
INSERT INTO dbo.pessoa_cartorio (id_pessoa, id_cartorio) VALUES (3, 2);
INSERT INTO dbo.pessoa_cartorio (id_pessoa, id_cartorio) VALUES (4, 1);
INSERT INTO dbo.pessoa_cartorio (id_pessoa, id_cartorio) VALUES (5, 3);

-- PessoaContaCorrente (Contas correntes dos medicos)
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (1, 1, '001', '1234-X', '123456', '7', TRUE, FALSE, TRUE, '11111111111');
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (2, 2, '237', '5678-0', '234567', '8', TRUE, FALSE, TRUE, 'maria.santos@email.com');
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (3, 3, '341', '9012-1', '345678', '9', TRUE, FALSE, TRUE, '33333333333');
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (4, 4, '033', '1111-2', '456789', '0', TRUE, FALSE, TRUE, '71999444444');
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (5, 5, '104', '2222-3', '567890', '1', TRUE, FALSE, TRUE, 'pedro.lima@email.com');
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (6, 6, '756', '3333-4', '678901', '2', TRUE, FALSE, TRUE, '66666666666');
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (7, 7, '001', '4444-5', '789012', '3', TRUE, FALSE, TRUE, '77777777777');
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (8, 8, '237', '5555-6', '890123', '4', TRUE, FALSE, TRUE, 'fernanda.ribeiro@email.com');
-- Conta secundaria para Dr. Carlos
INSERT INTO dbo.pessoa_conta_corrente (id_conta_corrente, id_pessoa, id_banco, agencia, conta_corrente, dv_conta_corrente, ativa, conta_poupanca, conta_padrao, pix) VALUES (9, 1, '237', '9999-0', '999999', '9', TRUE, TRUE, FALSE, NULL);

-- EmpresaSocio (Socios da empresa Alpha)
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 1, '2015-01-01', 100, 100.00, 850.00, TRUE, 5.00, 8.00);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 2, '2016-03-15', 80, 100.00, 850.00, TRUE, 5.00, 8.00);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 3, '2014-06-01', 120, 100.00, 850.00, FALSE, 6.00, 10.00);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 4, '2018-09-01', 50, 100.00, 850.00, TRUE, 5.00, 8.00);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 5, '2017-02-01', 90, 100.00, 850.00, TRUE, 5.00, 8.00);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 6, '2019-05-01', 40, 100.00, 850.00, TRUE, 5.00, 8.00);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 7, '2013-08-01', 150, 100.00, 850.00, FALSE, 6.00, 10.00);
INSERT INTO dbo.empresa_socio (id_empresa, id_pessoa, data_adesao, numero_quotas, valor_quota, valor_cremeb, novo_modelo, taxa_consulta, taxa_procedimento) VALUES (1, 8, '2020-01-15', 30, 100.00, 850.00, TRUE, 5.00, 8.00);

-- ============================================================
-- LANCAMENTOS FINANCEIROS (Exemplo)
-- ============================================================

-- Lancamento
INSERT INTO dbo.lancamento (id_lancamento, data_lancamento, id_empresa, id_cliente, competencia_lancamento, valor_bruto_lancamento, id_pessoa, id_tipo_servico, quitado_lancamento) VALUES (1, '2025-01-15', 1, 1, '01/2025', 15000.00, 1, 1, TRUE);
INSERT INTO dbo.lancamento (id_lancamento, data_lancamento, id_empresa, id_cliente, competencia_lancamento, valor_bruto_lancamento, id_pessoa, id_tipo_servico, quitado_lancamento) VALUES (2, '2025-01-15', 1, 1, '01/2025', 8500.00, 2, 1, TRUE);
INSERT INTO dbo.lancamento (id_lancamento, data_lancamento, id_empresa, id_cliente, competencia_lancamento, valor_bruto_lancamento, id_pessoa, id_tipo_servico, quitado_lancamento) VALUES (3, '2025-01-20', 1, 2, '01/2025', 25000.00, 3, 4, TRUE);
INSERT INTO dbo.lancamento (id_lancamento, data_lancamento, id_empresa, id_cliente, competencia_lancamento, valor_bruto_lancamento, id_pessoa, id_tipo_servico, quitado_lancamento) VALUES (4, '2025-01-22', 1, 3, '01/2025', 5500.00, 4, 1, FALSE);
INSERT INTO dbo.lancamento (id_lancamento, data_lancamento, id_empresa, id_cliente, competencia_lancamento, valor_bruto_lancamento, id_pessoa, id_tipo_servico, quitado_lancamento) VALUES (5, '2025-01-25', 1, 1, '01/2025', 12000.00, 5, 3, FALSE);

-- ============================================================
-- ANUIDADE CREMEB (Exemplo)
-- ============================================================

-- AnuidadeCremeb
INSERT INTO dbo.anuidade_cremeb (id_anuidade_cremeb, ano_exercicio, data_inicio, data_fim, valor_total, id_empresa) VALUES (1, '2025', '2025-01-01', '2025-12-31', 6800.00, 1);

-- AnuidadeCremebItem
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, id_anuidade_cremeb, id_pessoa, data_lancamento, valor_individual) VALUES (1, 1, 1, '2025-01-15', 850.00);
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, id_anuidade_cremeb, id_pessoa, data_lancamento, valor_individual) VALUES (2, 1, 2, '2025-01-15', 850.00);
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, id_anuidade_cremeb, id_pessoa, data_lancamento, valor_individual) VALUES (3, 1, 3, '2025-01-15', 850.00);
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, id_anuidade_cremeb, id_pessoa, data_lancamento, valor_individual) VALUES (4, 1, 4, '2025-01-15', 850.00);
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, id_anuidade_cremeb, id_pessoa, data_lancamento, valor_individual) VALUES (5, 1, 5, '2025-01-15', 850.00);
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, id_anuidade_cremeb, id_pessoa, data_lancamento, valor_individual) VALUES (6, 1, 6, '2025-01-15', 850.00);
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, id_anuidade_cremeb, id_pessoa, data_lancamento, valor_individual) VALUES (7, 1, 7, '2025-01-15', 850.00);
INSERT INTO dbo.anuidade_cremeb_item (id_anuidade_cremeb_item, id_anuidade_cremeb, id_pessoa, data_lancamento, valor_individual) VALUES (8, 1, 8, '2025-01-15', 850.00);

-- ============================================================
-- RESET AUTO-INCREMENT SEQUENCES (H2)
-- ============================================================
ALTER TABLE dbo.bairro ALTER COLUMN id_bairro RESTART WITH 11;
ALTER TABLE dbo.cidade ALTER COLUMN id_cidade RESTART WITH 11;
ALTER TABLE dbo.conselho ALTER COLUMN id_conselho RESTART WITH 6;
ALTER TABLE dbo.especialidade ALTER COLUMN id_especialidade RESTART WITH 16;
ALTER TABLE dbo.operadora ALTER COLUMN id_operadora RESTART WITH 11;
ALTER TABLE dbo.tipo_servico ALTER COLUMN id_tipo_servico RESTART WITH 9;
ALTER TABLE dbo.tipo_contato ALTER COLUMN id_tipo_contato RESTART WITH 7;
ALTER TABLE dbo.usuario ALTER COLUMN id_usuario RESTART WITH 3;
ALTER TABLE dbo.plano_retencao ALTER COLUMN id_plano_retencao RESTART WITH 4;
ALTER TABLE dbo.despesa_receita ALTER COLUMN id_despesa_receita RESTART WITH 11;
ALTER TABLE dbo.imposto_de_renda ALTER COLUMN id_imposto_de_renda RESTART WITH 6;
ALTER TABLE dbo.indicacao ALTER COLUMN id_indicacao RESTART WITH 6;
ALTER TABLE dbo.cartorio ALTER COLUMN id_cartorio RESTART WITH 4;
ALTER TABLE dbo.empresa ALTER COLUMN id_empresa RESTART WITH 4;
ALTER TABLE dbo.cliente ALTER COLUMN id_cliente RESTART WITH 6;
ALTER TABLE dbo.cliente_filial ALTER COLUMN id_cliente_filial RESTART WITH 5;
ALTER TABLE dbo.cliente_contato ALTER COLUMN id_cliente_contato RESTART WITH 9;
ALTER TABLE dbo.pessoa ALTER COLUMN id_pessoa RESTART WITH 9;
ALTER TABLE dbo.pessoa_conta_corrente ALTER COLUMN id_conta_corrente RESTART WITH 10;
ALTER TABLE dbo.lancamento ALTER COLUMN id_lancamento RESTART WITH 6;
ALTER TABLE dbo.anuidade_cremeb ALTER COLUMN id_anuidade_cremeb RESTART WITH 2;
ALTER TABLE dbo.anuidade_cremeb_item ALTER COLUMN id_anuidade_cremeb_item RESTART WITH 9;