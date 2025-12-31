import json
import csv
import os
from pathlib import Path

# Ler o JSON
json_path = r'c:\java\workspace\Gerador\metamodel\data\xandel\xandel-entities.json'
csv_dir = r'c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial'

with open(json_path, 'r', encoding='utf-8') as f:
    data = json.load(f)

entities = data.get('entities_continuation', [])

# Mapear CSVs disponíveis
csv_files = {}
for csv_file in Path(csv_dir).glob('*.csv'):
    csv_files[csv_file.stem.lower()] = csv_file

print("=" * 100)
print("RELATÓRIO DE COMPATIBILIDADE - JSON vs CSV")
print("=" * 100)
print()

# Estatísticas gerais
total_entities = len(entities)
total_csvs = len(csv_files)
print(f"Total de entidades no JSON: {total_entities}")
print(f"Total de CSVs disponíveis: {total_csvs}")
print()

# Análise por entidade
entities_with_csv = []
entities_without_csv = []
field_mismatches = []

for entity in entities:
    table_name = entity.get('tableName', '')
    entity_id = entity.get('id', '')
    entity_name = entity.get('name', '')

    print("-" * 100)
    print(f"ENTIDADE: {entity_name} (ID: {entity_id}, Tabela: {table_name})")
    print("-" * 100)

    # Buscar CSV correspondente
    csv_key = table_name.lower()

    if csv_key not in csv_files:
        print(f"❌ CSV NÃO ENCONTRADO para tabela '{table_name}'")
        entities_without_csv.append({
            'entity': entity_name,
            'table': table_name,
            'id': entity_id
        })
        print()
        continue

    entities_with_csv.append(entity_name)
    csv_path = csv_files[csv_key]

    # Ler cabeçalho do CSV
    try:
        with open(csv_path, 'r', encoding='utf-8') as csvfile:
            reader = csv.reader(csvfile)
            csv_columns = next(reader)
            csv_columns_lower = [col.lower() for col in csv_columns]
    except Exception as e:
        print(f"⚠️  ERRO ao ler CSV: {e}")
        print()
        continue

    print(f"✅ CSV ENCONTRADO: {csv_path.name}")
    print(f"   Colunas no CSV ({len(csv_columns)}): {', '.join(csv_columns)}")
    print()

    # Extrair campos da entidade
    fields = entity.get('fields', [])
    json_columns = []
    json_column_map = {}

    for field in fields:
        col_name = field.get('columnName', '')
        field_name = field.get('name', '')
        data_type = field.get('dataType', '')
        is_pk = field.get('primaryKey', False)
        is_required = field.get('required', False)
        reference = field.get('reference', {})

        json_columns.append(col_name)
        json_column_map[col_name.lower()] = {
            'name': field_name,
            'dataType': data_type,
            'isPK': is_pk,
            'required': is_required,
            'reference': reference
        }

    print(f"   Campos no JSON ({len(json_columns)}): {', '.join(json_columns)}")
    print()

    # Comparar campos
    json_set = set(col.lower() for col in json_columns)
    csv_set = set(csv_columns_lower)

    # Campos no JSON mas não no CSV
    missing_in_csv = json_set - csv_set
    # Campos no CSV mas não no JSON
    extra_in_csv = csv_set - json_set
    # Campos comuns
    common = json_set & csv_set

    if missing_in_csv or extra_in_csv:
        field_mismatches.append({
            'entity': entity_name,
            'table': table_name,
            'missing_in_csv': missing_in_csv,
            'extra_in_csv': extra_in_csv
        })

        if missing_in_csv:
            print(f"   ⚠️  CAMPOS NO JSON MAS NÃO NO CSV ({len(missing_in_csv)}):")
            for col in sorted(missing_in_csv):
                field_info = json_column_map.get(col, {})
                pk_marker = " [PK]" if field_info.get('isPK') else ""
                req_marker = " [OBRIGATÓRIO]" if field_info.get('required') else ""
                ref_marker = ""
                if field_info.get('reference'):
                    ref_entity = field_info['reference'].get('entity', '')
                    ref_marker = f" [FK -> {ref_entity}]"
                print(f"      - {col}{pk_marker}{req_marker}{ref_marker}")
            print()

        if extra_in_csv:
            print(f"   ⚠️  CAMPOS NO CSV MAS NÃO NO JSON ({len(extra_in_csv)}):")
            for col in sorted(extra_in_csv):
                original_col = next((c for c in csv_columns if c.lower() == col), col)
                print(f"      - {original_col}")
            print()
    else:
        print(f"   ✅ TODOS OS CAMPOS CORRESPONDEM ({len(common)} campos)")
        print()

    # Verificar relacionamentos
    relationships = entity.get('childEntities', [])
    if relationships:
        print(f"   📋 RELACIONAMENTOS (hasMany):")
        for rel in relationships:
            rel_entity = rel.get('entity', '')
            fk = rel.get('foreignKey', '')
            print(f"      - {entity_name} -> {rel_entity} (FK: {fk})")
        print()

    # Verificar foreign keys
    fk_fields = [f for f in fields if f.get('reference')]
    if fk_fields:
        print(f"   🔗 FOREIGN KEYS ({len(fk_fields)}):")
        for fk_field in fk_fields:
            col_name = fk_field.get('columnName', '')
            ref = fk_field.get('reference', {})
            ref_entity = ref.get('entity', '')
            ref_field = ref.get('field', '')
            on_delete = ref.get('onDelete', '')
            on_delete_str = f" [ON DELETE {on_delete}]" if on_delete else ""

            # Verificar se a FK está no CSV
            if col_name.lower() in csv_columns_lower:
                status = "✅"
            else:
                status = "❌"

            print(f"      {status} {col_name} -> {ref_entity}.{ref_field}{on_delete_str}")
        print()

print()
print("=" * 100)
print("RESUMO GERAL")
print("=" * 100)
print()

print(f"📊 Entidades com CSV correspondente: {len(entities_with_csv)}/{total_entities}")
print(f"❌ Entidades SEM CSV: {len(entities_without_csv)}/{total_entities}")
print(f"⚠️  Entidades com incompatibilidades de campos: {len(field_mismatches)}/{total_entities}")
print()

if entities_without_csv:
    print("ENTIDADES SEM CSV:")
    for ent in entities_without_csv:
        print(f"  - {ent['entity']} (tabela: {ent['table']})")
    print()

if field_mismatches:
    print("RESUMO DE INCOMPATIBILIDADES POR ENTIDADE:")
    for mismatch in field_mismatches:
        total_issues = len(mismatch['missing_in_csv']) + len(mismatch['extra_in_csv'])
        print(f"  - {mismatch['entity']} ({total_issues} discrepâncias)")
        if mismatch['missing_in_csv']:
            print(f"      • {len(mismatch['missing_in_csv'])} campos no JSON mas não no CSV")
        if mismatch['extra_in_csv']:
            print(f"      • {len(mismatch['extra_in_csv'])} campos no CSV mas não no JSON")
    print()

# CSVs sem entidade correspondente
csv_names = set(csv_files.keys())
entity_tables = set(e.get('tableName', '').lower() for e in entities)
orphan_csvs = csv_names - entity_tables

if orphan_csvs:
    print(f"📁 CSVs SEM entidade correspondente no JSON ({len(orphan_csvs)}):")
    for csv_name in sorted(orphan_csvs):
        print(f"  - {csv_name}.csv")
    print()

print("=" * 100)