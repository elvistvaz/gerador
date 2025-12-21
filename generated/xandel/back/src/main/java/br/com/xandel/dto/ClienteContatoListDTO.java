package br.com.xandel.dto;

/**
 * DTO de listagem para ClienteContato.
 */
public record ClienteContatoListDTO(
    Integer idClienteContato,
    Integer idTipoContato,
    String descricao
) {}
