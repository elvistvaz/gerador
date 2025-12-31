import os
import csv

# Diretório dos CSVs
csv_dir = r'c:\java\workspace\Gerador\metamodel\data\xandel\carga-inicial'

# Valores padrão para diferentes tipos de colunas
def get_default_value(column_name, row_num):
    col_lower = column_name.lower()

    # IDs de chave estrangeira - usar valores 1, 2, 3, etc.
    if col_lower.startswith('id_') and col_lower != column_name:
        return str(min(row_num, 5))  # Limitar a 5 para ter certeza que existem

    # Datas
    if 'data' in col_lower or 'date' in col_lower:
        return '2025-01-01 00:00:00'

    # Valores numéricos
    if any(word in col_lower for word in ['valor', 'taxa', 'percentual', 'peso', 'carga']):
        return str(100.00 * row_num)

    # Booleanos
    if any(word in col_lower for word in ['ativo', 'inativ', 'pago', 'juridica']):
        return '1' if row_num % 2 == 0 else '0'

    # Strings genéricas
    return f'Valor_{row_num}'

# Processar cada CSV
for filename in os.listdir(csv_dir):
    if not filename.endswith('.csv'):
        continue

    filepath = os.path.join(csv_dir, filename)

    try:
        # Ler o CSV
        with open(filepath, 'r', encoding='utf-8') as f:
            lines = f.readlines()

        if len(lines) < 2:
            continue

        # Separar header e dados
        header = lines[0].strip()
        data_lines = [line.strip() for line in lines[1:] if line.strip()]

        # Verificar se há campos vazios
        columns = header.split(';')
        has_empty = False

        for line in data_lines:
            fields = line.split(';')
            if len(fields) < len(columns):
                has_empty = True
                break
            for field in fields:
                if not field.strip():
                    has_empty = True
                    break

        if not has_empty:
            continue

        print(f'Corrigindo: {filename}')

        # Recriar os dados preenchendo campos vazios
        new_lines = [header]
        for idx, line in enumerate(data_lines, 1):
            fields = line.split(';')

            # Preencher campos faltantes ou vazios
            while len(fields) < len(columns):
                fields.append('')

            for i, field in enumerate(fields):
                if not field.strip():
                    fields[i] = get_default_value(columns[i], idx)

            new_lines.append(';'.join(fields))

        # Escrever de volta
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write('\n'.join(new_lines) + '\n')

        print(f'  ✓ Corrigido com sucesso')

    except Exception as e:
        print(f'  ✗ Erro ao processar {filename}: {e}')

print('\nProcessamento concluído!')
