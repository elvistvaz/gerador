package br.com.gerador.generator.util;

import br.com.gerador.metamodel.model.DataType;

/**
 * Utilitários para conversão de nomenclatura.
 */
public class NamingUtils {

    /**
     * Converte para PascalCase (primeira letra maiúscula).
     * Ex: "nomeBanco" -> "NomeBanco"
     */
    public static String toPascalCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    /**
     * Converte para camelCase (primeira letra minúscula).
     * Ex: "NomeBanco" -> "nomeBanco"
     */
    public static String toCamelCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    /**
     * Converte para SNAKE_CASE maiúsculo.
     * Ex: "nomeBanco" -> "NOME_BANCO"
     */
    public static String toUpperSnakeCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                result.append('_');
            }
            result.append(Character.toUpperCase(c));
        }
        return result.toString();
    }

    /**
     * Converte para snake_case minúsculo.
     * Ex: "nomeBanco" -> "nome_banco"
     */
    public static String toLowerSnakeCase(String name) {
        return toUpperSnakeCase(name).toLowerCase();
    }

    /**
     * Converte para kebab-case.
     * Ex: "nomeBanco" -> "nome-banco"
     */
    public static String toKebabCase(String name) {
        return toLowerSnakeCase(name).replace('_', '-');
    }

    /**
     * Gera nome de variável no plural.
     * Ex: "Banco" -> "bancos"
     */
    public static String toPlural(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        String lower = toCamelCase(name);
        if (lower.endsWith("ao")) {
            return lower.substring(0, lower.length() - 2) + "oes";
        }
        // Palavras terminadas em 'il' tônico: civil -> civis (remove 'l', adiciona 's')
        if (lower.endsWith("il")) {
            return lower.substring(0, lower.length() - 1) + "s";
        }
        // Outras palavras terminadas em 'l': papel -> papeis (remove 'l', adiciona 'is')
        if (lower.endsWith("l")) {
            return lower.substring(0, lower.length() - 1) + "is";
        }
        if (lower.endsWith("m")) {
            return lower.substring(0, lower.length() - 1) + "ns";
        }
        if (lower.endsWith("r") || lower.endsWith("z") || lower.endsWith("s")) {
            return lower + "es";
        }
        return lower + "s";
    }

    /**
     * Converte tipo do meta-modelo para tipo Java.
     */
    public static String toJavaType(DataType dataType) {
        if (dataType == null) {
            return "String";
        }
        switch (dataType) {
            case INTEGER:
                return "Integer";
            case LONG:
                return "Long";
            case DECIMAL:
            case MONEY:
                return "BigDecimal";
            case BOOLEAN:
                return "Boolean";
            case DATE:
                return "LocalDate";
            case DATETIME:
                return "LocalDateTime";
            case TIME:
                return "LocalTime";
            case BINARY:
                return "byte[]";
            case TEXT:
            case STRING:
            default:
                return "String";
        }
    }

    /**
     * Retorna o import necessário para o tipo Java.
     */
    public static String getImportForType(DataType dataType) {
        if (dataType == null) {
            return null;
        }
        switch (dataType) {
            case DECIMAL:
            case MONEY:
                return "java.math.BigDecimal";
            case DATE:
                return "java.time.LocalDate";
            case DATETIME:
                return "java.time.LocalDateTime";
            case TIME:
                return "java.time.LocalTime";
            default:
                return null;
        }
    }

    /**
     * Verifica se o tipo é STRING ou TEXT.
     */
    public static boolean isStringType(DataType dataType) {
        return dataType == DataType.STRING || dataType == DataType.TEXT;
    }

    /**
     * Gera o endpoint REST baseado no nome da entidade.
     * Ex: "PlanoRetencao" -> "/api/planos-retencao"
     */
    public static String toRestEndpoint(String entityName) {
        return "/api/" + toKebabCase(toPlural(entityName));
    }
}
