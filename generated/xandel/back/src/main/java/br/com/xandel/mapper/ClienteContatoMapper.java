package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ClienteContato;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ClienteContato e DTOs.
 */
@Component
public class ClienteContatoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ClienteContatoResponseDTO toResponseDTO(ClienteContato entity) {
        if (entity == null) {
            return null;
        }
        return new ClienteContatoResponseDTO(
            entity.getIdClienteContato(),
            entity.getIdCliente(),
            entity.getIdTipoContato(),
            entity.getDescricao()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ClienteContatoListDTO toListDTO(ClienteContato entity) {
        if (entity == null) {
            return null;
        }
        return new ClienteContatoListDTO(
            entity.getIdClienteContato(),
            entity.getIdTipoContato(),
            entity.getDescricao()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ClienteContato toEntity(ClienteContatoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ClienteContato entity = new ClienteContato();
        entity.setIdCliente(dto.idCliente());
        entity.setIdTipoContato(dto.idTipoContato());
        entity.setDescricao(dto.descricao());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ClienteContatoRequestDTO dto, ClienteContato entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setIdCliente(dto.idCliente());
        entity.setIdTipoContato(dto.idTipoContato());
        entity.setDescricao(dto.descricao());
    }
}
