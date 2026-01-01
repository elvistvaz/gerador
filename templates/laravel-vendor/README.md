# Laravel Vendor Template - Deploy Acelerado

## O que é isso?

Este diretório contém uma cópia compactada da pasta `vendor` do Laravel com todas as dependências já instaladas.

**Benefício**: Acelera MUITO o processo de geração/deploy, eliminando a necessidade de rodar `composer install` a cada vez.

## Arquivos

- `vendor.tar.gz` - Pasta vendor completa (6 MB compactado)

## Como funciona?

### Automático

O gerador **automaticamente** descompacta o vendor.tar.gz durante a geração do projeto Laravel:

1. Você roda: `mvn exec:java -Dexec.mainClass="br.com.gerador.generator.LaravelGeneratorMain" -Dexec.args="xandel"`
2. O gerador detecta o arquivo `vendor.tar.gz`
3. Descompacta diretamente na pasta `generated/xandel-laravel/vendor`
4. Pronto! Sem necessidade de `composer install`

### Como atualizar o template quando mudar dependências?

Se você modificar o `composer.json` e precisar atualizar o template:

```bash
# 1. Gere o projeto normalmente
mvn exec:java -Dexec.mainClass="br.com.gerador.generator.LaravelGeneratorMain" -Dexec.args="xandel"

# 2. Instale as novas dependências
cd generated/xandel-laravel
composer install --no-dev --optimize-autoloader

# 3. Compacte o novo vendor
cd ../..
rm templates/laravel-vendor/vendor.tar.gz
cd generated/xandel-laravel
tar -czf ../../templates/laravel-vendor/vendor.tar.gz vendor

# Pronto! Próximas gerações usarão o vendor atualizado
```

## Compatibilidade

⚠️ **Importante**: O vendor deve ser gerado no **mesmo sistema operacional** onde será usado.

- ✅ Windows para Windows
- ✅ Linux para Linux
- ❌ Windows para Linux (algumas extensões PHP são compiladas)

## Vantagens

- ⚡ **Velocidade**: Geração em segundos ao invés de minutos
- 📦 **Offline**: Funciona sem internet após setup inicial
- 🔒 **Estabilidade**: Sempre as mesmas versões (composer.lock)
- 🎯 **Consistência**: Ambiente idêntico a cada geração

## Versões incluídas

Este template foi gerado com:
- PHP 8.2+
- Laravel 11.x
- Composer 2.x

Veja o arquivo `composer.lock` no projeto gerado para detalhes completos.
