package br.com.gerador.generator.util;

/**
 * Utilitários para manipulação de strings.
 */
public class StringUtils {

    /**
     * Adiciona indentação a cada linha.
     */
    public static String indent(String text, int spaces) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String indent = " ".repeat(spaces);
        return indent + text.replace("\n", "\n" + indent);
    }

    /**
     * Adiciona indentação com tabs.
     */
    public static String indentTabs(String text, int tabs) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String indent = "\t".repeat(tabs);
        return indent + text.replace("\n", "\n" + indent);
    }

    /**
     * Verifica se a string é nula ou vazia.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Verifica se a string não é nula nem vazia.
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Retorna valor padrão se string for nula.
     */
    public static String defaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    /**
     * Capitaliza primeira letra.
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * Descapitaliza primeira letra.
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
