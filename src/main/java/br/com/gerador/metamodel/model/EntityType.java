package br.com.gerador.metamodel.model;

/**
 * Tipos de entidade no meta-modelo.
 */
public enum EntityType {
    /**
     * Entidade principal com tela de cadastro própria.
     */
    MAIN,

    /**
     * Tabela de lookup/apoio (ex: Banco, Cidade, Estado Civil).
     */
    LOOKUP,

    /**
     * Tabela de junção/relacionamento N:N (ex: MedicoEspecialidade).
     */
    JUNCTION,

    /**
     * Entidade filha, gerenciada dentro da tela do pai (ex: PessoaContaCorrente).
     */
    CHILD,

    /**
     * Tabela de configuração do sistema (ex: ParametroNF, ParametroEmail).
     */
    CONFIG
}
