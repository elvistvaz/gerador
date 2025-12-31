#!/usr/bin/env node

/**
 * Script para corrigir CSVs do diretório carga-inicial
 * para ficarem compatíveis com os JSONs xandel-entities.json e xandel-model.json
 *
 * Este script:
 * 1. Lê os JSONs para obter a estrutura correta de cada entidade
 * 2. Corrige cada CSV: adiciona colunas faltantes, renomeia, remove extras
 * 3. Gera relatório detalhado das correções
 * 4. Cria backup dos originais (.csv.bak)
 */

const fs = require('fs');
const path = require('path');
const readline = require('readline');

// Configurações
const CONFIG = {
  jsonEntitiesPath: './metamodel/data/xandel/xandel-entities.json',
  jsonModelPath: './metamodel/data/xandel/xandel-model.json',
  csvDir: './metamodel/data/xandel/carga-inicial',
  csvDelimiter: ';',
  encoding: 'utf8',
  backupExtension: '.bak'
};

// Objeto para armazenar o relatório
const report = {
  totalCsvs: 0,
  processedCsvs: 0,
  skippedCsvs: 0,
  errors: [],
  changes: {}
};

/**
 * Lê e parseia os arquivos JSON
 */
async function loadJsonSchemas() {
  console.log('🔍 Carregando schemas JSON...');

  try {
    const entitiesData = JSON.parse(fs.readFileSync(CONFIG.jsonEntitiesPath, CONFIG.encoding));
    const modelData = JSON.parse(fs.readFileSync(CONFIG.jsonModelPath, CONFIG.encoding));

    // Mesclar as entidades de ambos os arquivos
    const allEntities = {};

    // Processar xandel-model.json (entities)
    if (modelData.entities && Array.isArray(modelData.entities)) {
      modelData.entities.forEach(entity => {
        if (entity.tableName) {
          allEntities[entity.tableName.toLowerCase()] = entity;
        }
      });
    }

    // Processar xandel-entities.json (entities_continuation)
    if (entitiesData.entities_continuation && Array.isArray(entitiesData.entities_continuation)) {
      entitiesData.entities_continuation.forEach(entity => {
        if (entity.tableName) {
          allEntities[entity.tableName.toLowerCase()] = entity;
        }
      });
    }

    console.log(`✅ ${Object.keys(allEntities).length} entidades carregadas`);
    return allEntities;
  } catch (error) {
    console.error('❌ Erro ao carregar JSONs:', error.message);
    process.exit(1);
  }
}

/**
 * Extrai a lista de colunas corretas de uma entidade JSON
 */
function getCorrectColumns(entity) {
  if (!entity.fields || !Array.isArray(entity.fields)) {
    return [];
  }

  return entity.fields.map(field => ({
    columnName: field.columnName,
    name: field.name,
    id: field.id,
    dataType: field.dataType,
    required: field.required || false,
    primaryKey: field.primaryKey || false
  }));
}

/**
 * Lê um arquivo CSV e retorna headers e linhas
 */
async function readCsv(filePath) {
  return new Promise((resolve, reject) => {
    const lines = [];
    let headers = null;

    const fileStream = fs.createReadStream(filePath, { encoding: CONFIG.encoding });
    const rl = readline.createInterface({
      input: fileStream,
      crlfDelay: Infinity
    });

    rl.on('line', (line) => {
      line = line.trim();
      if (!line) return; // Ignora linhas vazias

      if (!headers) {
        headers = line.split(CONFIG.csvDelimiter).map(h => h.trim());
      } else {
        lines.push(line);
      }
    });

    rl.on('close', () => {
      resolve({ headers, lines });
    });

    rl.on('error', reject);
  });
}

/**
 * Escreve um arquivo CSV com os dados corrigidos
 */
function writeCsv(filePath, headers, lines) {
  const headerLine = headers.join(CONFIG.csvDelimiter);
  const content = [headerLine, ...lines, ''].join('\n'); // Adiciona linha vazia no final
  fs.writeFileSync(filePath, content, CONFIG.encoding);
}

/**
 * Cria backup de um arquivo
 */
function createBackup(filePath) {
  const backupPath = filePath + CONFIG.backupExtension;
  if (!fs.existsSync(backupPath)) {
    fs.copyFileSync(filePath, backupPath);
    console.log(`   📦 Backup criado: ${path.basename(backupPath)}`);
  }
}

/**
 * Normaliza um nome de coluna para comparação case-insensitive
 */
function normalizeColumnName(name) {
  return name.toLowerCase().replace(/[_\s-]/g, '');
}

/**
 * Encontra correspondência entre coluna do CSV e coluna do JSON
 */
function findColumnMatch(csvColumn, correctColumns) {
  const normalized = normalizeColumnName(csvColumn);

  // Busca exata (case-insensitive)
  for (const col of correctColumns) {
    if (normalizeColumnName(col.columnName) === normalized) {
      return col;
    }
  }

  return null;
}

/**
 * Analisa as diferenças entre CSV e JSON
 */
function analyzeColumns(csvHeaders, correctColumns) {
  const analysis = {
    toAdd: [],        // Colunas que faltam no CSV
    toRename: [],     // Colunas que precisam ser renomeadas
    toRemove: [],     // Colunas que não existem no JSON
    unchanged: []     // Colunas que já estão corretas
  };

  // Mapear colunas corretas por nome normalizado
  const correctColsMap = new Map();
  correctColumns.forEach(col => {
    correctColsMap.set(normalizeColumnName(col.columnName), col);
  });

  // Analisar colunas do CSV
  const foundColumns = new Set();

  csvHeaders.forEach((csvHeader, index) => {
    const match = findColumnMatch(csvHeader, correctColumns);

    if (match) {
      foundColumns.add(normalizeColumnName(match.columnName));

      if (csvHeader === match.columnName) {
        analysis.unchanged.push({ csv: csvHeader, correct: match.columnName, index });
      } else {
        analysis.toRename.push({
          from: csvHeader,
          to: match.columnName,
          index
        });
      }
    } else {
      analysis.toRemove.push({ name: csvHeader, index });
    }
  });

  // Encontrar colunas faltantes
  correctColumns.forEach((col, correctIndex) => {
    const normalized = normalizeColumnName(col.columnName);
    if (!foundColumns.has(normalized)) {
      analysis.toAdd.push({
        name: col.columnName,
        correctIndex,
        required: col.required
      });
    }
  });

  return analysis;
}

/**
 * Aplica as correções em uma linha de dados do CSV
 */
function correctCsvLine(line, oldHeaders, newHeaders, columnMapping) {
  const oldValues = line.split(CONFIG.csvDelimiter).map(v => v.trim());
  const newValues = new Array(newHeaders.length).fill('');

  newHeaders.forEach((newHeader, newIndex) => {
    const oldIndex = columnMapping[newHeader];
    if (oldIndex !== undefined && oldIndex < oldValues.length) {
      newValues[newIndex] = oldValues[oldIndex];
    }
  });

  return newValues.join(CONFIG.csvDelimiter);
}

/**
 * Converte snake_case para PascalCase
 */
function snakeCaseToPascalCase(str) {
  return str
    .split('_')
    .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
    .join('');
}

/**
 * Encontra a entidade correspondente ao arquivo CSV
 */
function findEntityForCsv(csvFileName, entities) {
  const baseName = path.basename(csvFileName, '.csv');

  // Tentar match direto (case-insensitive)
  const directMatch = entities[baseName.toLowerCase()];
  if (directMatch) return directMatch;

  // Tentar converter snake_case para PascalCase
  const pascalCase = snakeCaseToPascalCase(baseName);
  const pascalMatch = entities[pascalCase.toLowerCase()];
  if (pascalMatch) return pascalMatch;

  return null;
}

/**
 * Processa um arquivo CSV
 */
async function processCsv(csvFile, entities) {
  const csvPath = path.join(CONFIG.csvDir, csvFile);

  console.log(`\n📄 Processando: ${csvFile}`);

  // Encontrar entidade correspondente no JSON
  const entity = findEntityForCsv(csvFile, entities);

  if (!entity) {
    console.log(`   ⚠️  Entidade não encontrada no JSON, pulando...`);
    report.skippedCsvs++;
    report.changes[csvFile] = { status: 'skipped', reason: 'Entidade não encontrada no JSON' };
    return;
  }

  // Obter colunas corretas do JSON
  const correctColumns = getCorrectColumns(entity);
  if (correctColumns.length === 0) {
    console.log(`   ⚠️  Nenhum campo definido na entidade ${entity.name}, pulando...`);
    report.skippedCsvs++;
    report.changes[csvFile] = { status: 'skipped', reason: 'Nenhum campo definido na entidade' };
    return;
  }

  // Ler CSV atual
  const { headers: csvHeaders, lines: csvLines } = await readCsv(csvPath);

  if (!csvHeaders || csvHeaders.length === 0) {
    console.log(`   ⚠️  CSV vazio ou sem headers, pulando...`);
    report.skippedCsvs++;
    report.changes[csvFile] = { status: 'skipped', reason: 'CSV vazio ou sem headers' };
    return;
  }

  // Analisar diferenças
  const analysis = analyzeColumns(csvHeaders, correctColumns);

  // Verificar se há mudanças necessárias
  const hasChanges = analysis.toAdd.length > 0 ||
                     analysis.toRename.length > 0 ||
                     analysis.toRemove.length > 0;

  if (!hasChanges) {
    console.log(`   ✅ CSV já está 100% compatível! Nenhuma alteração necessária.`);
    report.processedCsvs++;
    report.changes[csvFile] = {
      status: 'perfect',
      columnsCount: csvHeaders.length
    };
    return;
  }

  // Criar backup antes de modificar
  createBackup(csvPath);

  // Construir novos headers na ordem correta do JSON
  const newHeaders = correctColumns.map(col => col.columnName);

  // Criar mapeamento de colunas antigas para novas
  const columnMapping = {};

  newHeaders.forEach((newHeader, newIndex) => {
    const normalized = normalizeColumnName(newHeader);

    // Procurar o índice da coluna antiga
    for (let oldIndex = 0; oldIndex < csvHeaders.length; oldIndex++) {
      const oldHeader = csvHeaders[oldIndex];
      if (normalizeColumnName(oldHeader) === normalized) {
        columnMapping[newHeader] = oldIndex;
        break;
      }
    }
  });

  // Corrigir cada linha de dados
  const newLines = csvLines.map(line => correctCsvLine(line, csvHeaders, newHeaders, columnMapping));

  // Escrever CSV corrigido
  writeCsv(csvPath, newHeaders, newLines);

  // Registrar mudanças no relatório
  report.processedCsvs++;
  report.changes[csvFile] = {
    status: 'corrected',
    entity: entity.name,
    tableName: entity.tableName,
    added: analysis.toAdd.map(a => a.name),
    renamed: analysis.toRename.map(r => `${r.from} → ${r.to}`),
    removed: analysis.toRemove.map(r => r.name),
    totalColumns: newHeaders.length,
    dataLines: csvLines.length
  };

  // Exibir resumo das mudanças
  console.log(`   📊 Entidade: ${entity.name} (${entity.tableName})`);

  if (analysis.toAdd.length > 0) {
    console.log(`   ➕ Colunas adicionadas (${analysis.toAdd.length}):`);
    analysis.toAdd.forEach(col => {
      const required = col.required ? ' [OBRIGATÓRIA]' : '';
      console.log(`      - ${col.name}${required}`);
    });
  }

  if (analysis.toRename.length > 0) {
    console.log(`   🔄 Colunas renomeadas (${analysis.toRename.length}):`);
    analysis.toRename.forEach(col => {
      console.log(`      - ${col.from} → ${col.to}`);
    });
  }

  if (analysis.toRemove.length > 0) {
    console.log(`   ❌ Colunas removidas (${analysis.toRemove.length}):`);
    analysis.toRemove.forEach(col => {
      console.log(`      - ${col.name}`);
    });
  }

  console.log(`   ✅ CSV corrigido com sucesso! (${csvLines.length} linhas de dados)`);
}

/**
 * Gera e exibe o relatório final
 */
function generateReport() {
  console.log('\n' + '='.repeat(80));
  console.log('📊 RELATÓRIO FINAL DE CORREÇÕES');
  console.log('='.repeat(80));

  console.log(`\n📈 Estatísticas Gerais:`);
  console.log(`   Total de CSVs encontrados: ${report.totalCsvs}`);
  console.log(`   CSVs processados/corrigidos: ${report.processedCsvs}`);
  console.log(`   CSVs já perfeitos: ${Object.values(report.changes).filter(c => c.status === 'perfect').length}`);
  console.log(`   CSVs pulados: ${report.skippedCsvs}`);
  console.log(`   Erros: ${report.errors.length}`);

  // CSVs 100% compatíveis
  const perfectCsvs = Object.entries(report.changes)
    .filter(([_, change]) => change.status === 'perfect');

  if (perfectCsvs.length > 0) {
    console.log(`\n✅ CSVs 100% Compatíveis (${perfectCsvs.length}):`);
    perfectCsvs.forEach(([file, change]) => {
      console.log(`   - ${file} (${change.columnsCount} colunas)`);
    });
  }

  // CSVs corrigidos
  const correctedCsvs = Object.entries(report.changes)
    .filter(([_, change]) => change.status === 'corrected');

  if (correctedCsvs.length > 0) {
    console.log(`\n🔧 CSVs Corrigidos (${correctedCsvs.length}):`);
    correctedCsvs.forEach(([file, change]) => {
      console.log(`\n   📄 ${file}`);
      console.log(`      Entidade: ${change.entity} (${change.tableName})`);
      console.log(`      Linhas de dados: ${change.dataLines}`);
      console.log(`      Total de colunas: ${change.totalColumns}`);

      if (change.added.length > 0) {
        console.log(`      ➕ Adicionadas: ${change.added.join(', ')}`);
      }
      if (change.renamed.length > 0) {
        console.log(`      🔄 Renomeadas: ${change.renamed.join(', ')}`);
      }
      if (change.removed.length > 0) {
        console.log(`      ❌ Removidas: ${change.removed.join(', ')}`);
      }
    });
  }

  // CSVs pulados
  const skippedCsvs = Object.entries(report.changes)
    .filter(([_, change]) => change.status === 'skipped');

  if (skippedCsvs.length > 0) {
    console.log(`\n⚠️  CSVs Pulados (${skippedCsvs.length}):`);
    skippedCsvs.forEach(([file, change]) => {
      console.log(`   - ${file}: ${change.reason}`);
    });
  }

  // Erros
  if (report.errors.length > 0) {
    console.log(`\n❌ Erros (${report.errors.length}):`);
    report.errors.forEach(error => {
      console.log(`   - ${error.file}: ${error.message}`);
    });
  }

  console.log('\n' + '='.repeat(80));
  console.log('✨ Processo concluído!');
  console.log('='.repeat(80) + '\n');

  // Salvar relatório em arquivo JSON
  const reportPath = path.join(CONFIG.csvDir, 'relatorio_correcoes.json');
  fs.writeFileSync(
    reportPath,
    JSON.stringify(report, null, 2),
    CONFIG.encoding
  );
  console.log(`📝 Relatório detalhado salvo em: ${reportPath}\n`);
}

/**
 * Função principal
 */
async function main() {
  console.log('🚀 Iniciando correção dos CSVs...\n');

  // Verificar se os diretórios existem
  if (!fs.existsSync(CONFIG.jsonEntitiesPath)) {
    console.error(`❌ Arquivo não encontrado: ${CONFIG.jsonEntitiesPath}`);
    process.exit(1);
  }

  if (!fs.existsSync(CONFIG.jsonModelPath)) {
    console.error(`❌ Arquivo não encontrado: ${CONFIG.jsonModelPath}`);
    process.exit(1);
  }

  if (!fs.existsSync(CONFIG.csvDir)) {
    console.error(`❌ Diretório não encontrado: ${CONFIG.csvDir}`);
    process.exit(1);
  }

  // Carregar schemas JSON
  const entities = await loadJsonSchemas();

  // Listar todos os arquivos CSV
  const csvFiles = fs.readdirSync(CONFIG.csvDir)
    .filter(file => file.endsWith('.csv') && !file.endsWith('.bak.csv'));

  report.totalCsvs = csvFiles.length;
  console.log(`\n📁 Encontrados ${csvFiles.length} arquivos CSV para processar`);

  // Processar cada CSV
  for (const csvFile of csvFiles) {
    try {
      await processCsv(csvFile, entities);
    } catch (error) {
      console.error(`❌ Erro ao processar ${csvFile}:`, error.message);
      report.errors.push({ file: csvFile, message: error.message });
    }
  }

  // Gerar relatório final
  generateReport();
}

// Executar
main().catch(error => {
  console.error('❌ Erro fatal:', error);
  process.exit(1);
});
