import csv
import os
import re
from pathlib import Path
from collections import defaultdict

# Diretórios
CSV_DIR = r"C:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial"
MIGRATION_DIR = r"C:\java\workspace\Gerador\generated\xandel-laravel\database\migrations"

def get_csv_files():
    """Retorna lista de arquivos CSV"""
    return list(Path(CSV_DIR).glob("*.csv"))

def get_migration_files():
    """Retorna lista de arquivos de migration"""
    return list(Path(MIGRATION_DIR).glob("*.php"))

def analyze_csv(csv_path):
    """Analisa um CSV e retorna informações sobre nulidade dos campos"""
    with open(csv_path, 'r', encoding='utf-8') as f:
        reader = csv.DictReader(f)
        headers = reader.fieldnames

        # Contador de nulos por campo
        null_counts = defaultdict(int)
        total_rows = 0

        for row in reader:
            total_rows += 1
            for header in headers:
                value = row.get(header, '').strip()
                if value == '' or value.lower() == 'null':
                    null_counts[header] += 1

        return {
            'headers': headers,
            'null_counts': dict(null_counts),
            'total_rows': total_rows
        }

def find_migration_for_table(table_name, migration_files):
    """Encontra o arquivo de migration para uma tabela"""
    pattern = f"create_{table_name}_table"
    for migration_file in migration_files:
        if pattern in migration_file.name:
            return migration_file
    return None

def parse_migration(migration_path):
    """Analisa uma migration e retorna informações sobre nullable dos campos"""
    with open(migration_path, 'r', encoding='utf-8') as f:
        content = f.read()

    # Extrai a seção do Schema::create
    schema_match = re.search(r'Schema::create.*?\{(.*?)\}\);', content, re.DOTALL)
    if not schema_match:
        return {}

    schema_content = schema_match.group(1)

    # Analisa cada linha de campo
    field_info = {}

    # Padrões para encontrar definições de campos
    # Exemplo: $table->string('nome')->nullable();
    # Exemplo: $table->integer('id_Cidade');
    field_pattern = r'\$table->(\w+)\([\'"]([^\'"]+)[\'"]\)(.*?);'

    for match in re.finditer(field_pattern, schema_content):
        field_type = match.group(1)
        field_name = match.group(2)
        modifiers = match.group(3)

        is_nullable = '->nullable()' in modifiers

        field_info[field_name] = {
            'type': field_type,
            'nullable': is_nullable
        }

    return field_info

def main():
    csv_files = get_csv_files()
    migration_files = get_migration_files()

    print("=" * 100)
    print("ANÁLISE DE CONSISTÊNCIA DE NULIDADE - CSVs vs Migrações Laravel")
    print("=" * 100)
    print()

    all_inconsistencies = []

    for csv_file in sorted(csv_files):
        table_name = csv_file.stem  # Nome do arquivo sem extensão

        print(f"\n{'=' * 100}")
        print(f"Analisando: {table_name}")
        print(f"{'=' * 100}")

        # Analisa CSV
        try:
            csv_data = analyze_csv(csv_file)
        except Exception as e:
            print(f"  ❌ ERRO ao ler CSV: {e}")
            continue

        # Encontra migration correspondente
        migration_file = find_migration_for_table(table_name, migration_files)
        if not migration_file:
            print(f"  ⚠️  Migration não encontrada para tabela '{table_name}'")
            continue

        print(f"  Migration: {migration_file.name}")

        # Analisa migration
        try:
            migration_fields = parse_migration(migration_file)
        except Exception as e:
            print(f"  ❌ ERRO ao ler Migration: {e}")
            continue

        # Compara consistência
        total_rows = csv_data['total_rows']
        print(f"  Total de registros no CSV: {total_rows}")
        print()

        inconsistencies = []

        for header in csv_data['headers']:
            null_count = csv_data['null_counts'].get(header, 0)
            has_nulls_in_csv = null_count > 0
            percent_null = (null_count / total_rows * 100) if total_rows > 0 else 0

            # Verifica se campo existe na migration
            if header not in migration_fields:
                inconsistencies.append({
                    'field': header,
                    'issue': 'CAMPO NO CSV MAS NÃO NA MIGRATION',
                    'csv_nulls': null_count,
                    'csv_total': total_rows,
                    'migration_nullable': 'N/A'
                })
                continue

            is_nullable_in_migration = migration_fields[header]['nullable']

            # Verifica inconsistências
            if has_nulls_in_csv and not is_nullable_in_migration:
                inconsistencies.append({
                    'field': header,
                    'issue': 'CAMPO TEM NULOS NO CSV MAS É NOT NULL NA MIGRATION',
                    'csv_nulls': null_count,
                    'csv_total': total_rows,
                    'percent_null': percent_null,
                    'migration_nullable': False
                })
            elif not has_nulls_in_csv and is_nullable_in_migration and total_rows > 0:
                inconsistencies.append({
                    'field': header,
                    'issue': 'CAMPO SEMPRE PREENCHIDO NO CSV MAS É NULLABLE NA MIGRATION',
                    'csv_nulls': 0,
                    'csv_total': total_rows,
                    'percent_null': 0,
                    'migration_nullable': True
                })

        # Verifica campos na migration que não estão no CSV
        for field_name in migration_fields:
            if field_name not in csv_data['headers'] and field_name not in ['id', 'created_at', 'updated_at', 'deleted_at']:
                inconsistencies.append({
                    'field': field_name,
                    'issue': 'CAMPO NA MIGRATION MAS NÃO NO CSV',
                    'csv_nulls': 'N/A',
                    'csv_total': total_rows,
                    'migration_nullable': migration_fields[field_name]['nullable']
                })

        if inconsistencies:
            print(f"  ⚠️  INCONSISTÊNCIAS ENCONTRADAS: {len(inconsistencies)}")
            print()
            for inc in inconsistencies:
                print(f"    Campo: {inc['field']}")
                print(f"      Problema: {inc['issue']}")
                if inc['csv_nulls'] != 'N/A':
                    print(f"      CSV: {inc['csv_nulls']}/{inc['csv_total']} nulos ({inc.get('percent_null', 0):.1f}%)")
                if inc['migration_nullable'] != 'N/A':
                    print(f"      Migration: {'NULLABLE' if inc['migration_nullable'] else 'NOT NULL'}")
                print()

            all_inconsistencies.extend([{**inc, 'table': table_name} for inc in inconsistencies])
        else:
            print(f"  ✅ Nenhuma inconsistência encontrada!")

    # Resumo final
    print("\n" + "=" * 100)
    print("RESUMO GERAL")
    print("=" * 100)
    print(f"\nTotal de inconsistências encontradas: {len(all_inconsistencies)}")

    if all_inconsistencies:
        print("\n📋 LISTA COMPLETA DE INCONSISTÊNCIAS:")
        print()

        # Agrupa por tipo de problema
        by_issue = defaultdict(list)
        for inc in all_inconsistencies:
            by_issue[inc['issue']].append(inc)

        for issue_type, items in by_issue.items():
            print(f"\n{issue_type}: {len(items)} casos")
            for item in items:
                print(f"  - {item['table']}.{item['field']}")

if __name__ == "__main__":
    main()