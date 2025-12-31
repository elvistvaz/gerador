const fs = require('fs');
const path = require('path');

// Paths
const jsonPath = path.join(__dirname, 'metamodel', 'data', 'xandel', 'xandel-entities.json');
const csvDir = path.join(__dirname, 'metamodel', 'data', 'xandel', 'carga-inicial');

// Read JSON
const jsonData = JSON.parse(fs.readFileSync(jsonPath, 'utf8'));
const entities = jsonData.entities_continuation || [];

// Read CSV files
const csvFiles = {};
fs.readdirSync(csvDir)
  .filter(file => file.endsWith('.csv'))
  .forEach(file => {
    const tableName = path.basename(file, '.csv').toLowerCase();
    csvFiles[tableName] = path.join(csvDir, file);
  });

console.log('='.repeat(100));
console.log('RELATÓRIO DE COMPATIBILIDADE - JSON vs CSV');
console.log('='.repeat(100));
console.log();

// Statistics
console.log(`Total de entidades no JSON: ${entities.length}`);
console.log(`Total de CSVs disponíveis: ${Object.keys(csvFiles).length}`);
console.log();

// Analysis
const results = {
  withCSV: [],
  withoutCSV: [],
  mismatches: [],
  csvWithoutEntity: []
};

// Helper to read CSV header
function readCSVHeader(filePath) {
  const content = fs.readFileSync(filePath, 'utf8');
  const firstLine = content.split('\n')[0].trim();
  return firstLine.split(';').map(col => col.trim());
}

// Analyze each entity
entities.forEach(entity => {
  const tableName = entity.tableName || '';
  const entityId = entity.id || '';
  const entityName = entity.name || '';
  const tableNameLower = tableName.toLowerCase();

  console.log('-'.repeat(100));
  console.log(`ENTIDADE: ${entityName} (ID: ${entityId}, Tabela: ${tableName})`);
  console.log('-'.repeat(100));

  // Check if CSV exists
  if (!csvFiles[tableNameLower]) {
    console.log(`❌ CSV NÃO ENCONTRADO para tabela '${tableName}'`);
    results.withoutCSV.push({ entity: entityName, table: tableName, id: entityId });
    console.log();
    return;
  }

  results.withCSV.push(entityName);
  const csvPath = csvFiles[tableNameLower];

  // Read CSV columns
  let csvColumns = [];
  try {
    csvColumns = readCSVHeader(csvPath);
    const csvColumnsLower = csvColumns.map(c => c.toLowerCase());

    console.log(`✅ CSV ENCONTRADO: ${path.basename(csvPath)}`);
    console.log(`   Colunas no CSV (${csvColumns.length}): ${csvColumns.join(', ')}`);
    console.log();

    // Extract fields from entity
    const fields = entity.fields || [];
    const jsonColumns = [];
    const jsonColumnMap = {};

    fields.forEach(field => {
      const colName = field.columnName || '';
      const fieldName = field.name || '';
      const dataType = field.dataType || '';
      const isPK = field.primaryKey || false;
      const isRequired = field.required || false;
      const reference = field.reference || null;

      jsonColumns.push(colName);
      jsonColumnMap[colName.toLowerCase()] = {
        name: fieldName,
        dataType,
        isPK,
        required: isRequired,
        reference
      };
    });

    console.log(`   Campos no JSON (${jsonColumns.length}): ${jsonColumns.join(', ')}`);
    console.log();

    // Compare columns
    const jsonSet = new Set(jsonColumns.map(c => c.toLowerCase()));
    const csvSet = new Set(csvColumnsLower);

    const missingInCSV = [...jsonSet].filter(c => !csvSet.has(c));
    const extraInCSV = [...csvSet].filter(c => !jsonSet.has(c));
    const common = [...jsonSet].filter(c => csvSet.has(c));

    if (missingInCSV.length > 0 || extraInCSV.length > 0) {
      results.mismatches.push({
        entity: entityName,
        table: tableName,
        missingInCSV,
        extraInCSV
      });

      if (missingInCSV.length > 0) {
        console.log(`   ⚠️  CAMPOS NO JSON MAS NÃO NO CSV (${missingInCSV.length}):`);
        missingInCSV.sort().forEach(col => {
          const fieldInfo = jsonColumnMap[col] || {};
          const pkMarker = fieldInfo.isPK ? ' [PK]' : '';
          const reqMarker = fieldInfo.required ? ' [OBRIGATÓRIO]' : '';
          let refMarker = '';
          if (fieldInfo.reference) {
            const refEntity = fieldInfo.reference.entity || '';
            refMarker = ` [FK -> ${refEntity}]`;
          }
          console.log(`      - ${col}${pkMarker}${reqMarker}${refMarker}`);
        });
        console.log();
      }

      if (extraInCSV.length > 0) {
        console.log(`   ⚠️  CAMPOS NO CSV MAS NÃO NO JSON (${extraInCSV.length}):`);
        extraInCSV.sort().forEach(col => {
          const originalCol = csvColumns.find(c => c.toLowerCase() === col) || col;
          console.log(`      - ${originalCol}`);
        });
        console.log();
      }
    } else {
      console.log(`   ✅ TODOS OS CAMPOS CORRESPONDEM (${common.length} campos)`);
      console.log();
    }

    // Check relationships
    const relationships = entity.childEntities || [];
    if (relationships.length > 0) {
      console.log(`   📋 RELACIONAMENTOS (hasMany):`);
      relationships.forEach(rel => {
        const relEntity = rel.entity || '';
        const fk = rel.foreignKey || '';
        console.log(`      - ${entityName} -> ${relEntity} (FK: ${fk})`);
      });
      console.log();
    }

    // Check foreign keys
    const fkFields = fields.filter(f => f.reference);
    if (fkFields.length > 0) {
      console.log(`   🔗 FOREIGN KEYS (${fkFields.length}):`);
      fkFields.forEach(fkField => {
        const colName = fkField.columnName || '';
        const ref = fkField.reference || {};
        const refEntity = ref.entity || '';
        const refField = ref.field || '';
        const onDelete = ref.onDelete || '';
        const onDeleteStr = onDelete ? ` [ON DELETE ${onDelete}]` : '';

        const status = csvColumnsLower.includes(colName.toLowerCase()) ? '✅' : '❌';
        console.log(`      ${status} ${colName} -> ${refEntity}.${refField}${onDeleteStr}`);
      });
      console.log();
    }

  } catch (error) {
    console.log(`⚠️  ERRO ao ler CSV: ${error.message}`);
    console.log();
  }
});

// Summary
console.log();
console.log('='.repeat(100));
console.log('RESUMO GERAL');
console.log('='.repeat(100));
console.log();

console.log(`📊 Entidades com CSV correspondente: ${results.withCSV.length}/${entities.length}`);
console.log(`❌ Entidades SEM CSV: ${results.withoutCSV.length}/${entities.length}`);
console.log(`⚠️  Entidades com incompatibilidades de campos: ${results.mismatches.length}/${entities.length}`);
console.log();

if (results.withoutCSV.length > 0) {
  console.log('ENTIDADES SEM CSV:');
  results.withoutCSV.forEach(ent => {
    console.log(`  - ${ent.entity} (tabela: ${ent.table})`);
  });
  console.log();
}

if (results.mismatches.length > 0) {
  console.log('RESUMO DE INCOMPATIBILIDADES POR ENTIDADE:');
  results.mismatches.forEach(mismatch => {
    const totalIssues = mismatch.missingInCSV.length + mismatch.extraInCSV.length;
    console.log(`  - ${mismatch.entity} (${totalIssues} discrepâncias)`);
    if (mismatch.missingInCSV.length > 0) {
      console.log(`      • ${mismatch.missingInCSV.length} campos no JSON mas não no CSV`);
    }
    if (mismatch.extraInCSV.length > 0) {
      console.log(`      • ${mismatch.extraInCSV.length} campos no CSV mas não no JSON`);
    }
  });
  console.log();
}

// Find CSVs without entities
const csvNames = new Set(Object.keys(csvFiles));
const entityTables = new Set(entities.map(e => (e.tableName || '').toLowerCase()));
const orphanCSVs = [...csvNames].filter(csv => !entityTables.has(csv));

if (orphanCSVs.length > 0) {
  console.log(`📁 CSVs SEM entidade correspondente no JSON (${orphanCSVs.length}):`);
  orphanCSVs.sort().forEach(csv => {
    console.log(`  - ${csv}.csv`);
  });
  console.log();
}

console.log('='.repeat(100));
