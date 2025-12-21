package br.com.gerador.metamodel.model;

/**
 * Ações referenciais para foreign keys.
 */
public enum ReferentialAction {
    CASCADE,
    SET_NULL,
    RESTRICT,
    NO_ACTION
}
