#!/bin/bash
#
# Script para atualizar o vendor template do Laravel
#
# Uso: ./update-vendor-template.sh [nome-do-projeto]
# Exemplo: ./update-vendor-template.sh xandel
#

set -e  # Sai se houver erro

PROJECT=${1:-xandel}
BASE_DIR="c:/java/workspace/Gerador"
GENERATED_DIR="$BASE_DIR/generated/${PROJECT}-laravel"
TEMPLATE_DIR="$BASE_DIR/templates/laravel-vendor"

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  ATUALIZAÇÃO DO VENDOR TEMPLATE - LARAVEL                 ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""
echo "Projeto: $PROJECT"
echo "Diretório: $GENERATED_DIR"
echo ""

# Verifica se o projeto existe
if [ ! -d "$GENERATED_DIR" ]; then
    echo "❌ Erro: Projeto não encontrado em: $GENERATED_DIR"
    echo ""
    echo "Execute primeiro:"
    echo "  mvn exec:java -Dexec.mainClass=\"br.com.gerador.generator.LaravelGeneratorMain\" -Dexec.args=\"$PROJECT\""
    exit 1
fi

# Verifica se vendor existe
if [ ! -d "$GENERATED_DIR/vendor" ]; then
    echo "⚠️  Pasta vendor não encontrada. Executando composer install..."
    cd "$GENERATED_DIR"
    composer install --no-dev --optimize-autoloader

    if [ $? -ne 0 ]; then
        echo "❌ Erro ao executar composer install"
        exit 1
    fi
else
    echo "✓ Pasta vendor encontrada"
fi

# Remove vendor.tar.gz antigo
if [ -f "$TEMPLATE_DIR/vendor.tar.gz" ]; then
    echo "🗑️  Removendo vendor.tar.gz antigo..."
    rm -f "$TEMPLATE_DIR/vendor.tar.gz"
fi

# Compacta o novo vendor
echo "📦 Compactando vendor..."
cd "$GENERATED_DIR"
tar -czf "$TEMPLATE_DIR/vendor.tar.gz" vendor

if [ $? -eq 0 ]; then
    SIZE=$(ls -lh "$TEMPLATE_DIR/vendor.tar.gz" | awk '{print $5}')
    echo ""
    echo "╔═══════════════════════════════════════════════════════════╗"
    echo "║  ✓ VENDOR TEMPLATE ATUALIZADO COM SUCESSO!               ║"
    echo "╚═══════════════════════════════════════════════════════════╝"
    echo ""
    echo "Arquivo: $TEMPLATE_DIR/vendor.tar.gz"
    echo "Tamanho: $SIZE"
    echo ""
    echo "Próximas gerações usarão este vendor automaticamente! ⚡"
else
    echo "❌ Erro ao compactar vendor"
    exit 1
fi
