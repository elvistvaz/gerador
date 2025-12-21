package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ClienteFilial;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ClienteFilial e DTOs.
 */
@Component
public class ClienteFilialMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ClienteFilialResponseDTO toResponseDTO(ClienteFilial entity) {
        if (entity == null) {
            return null;
        }
        return new ClienteFilialResponseDTO(
            entity.getIdClienteFilial(),
            entity.getIdCliente(),
            entity.getNomeFilial()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ClienteFilialListDTO toListDTO(ClienteFilial entity) {
        if (entity == null) {
            return null;
        }
        return new ClienteFilialListDTO(
            entity.getIdClienteFilial(),
            entity.getNomeFilial()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ClienteFilial toEntity(ClienteFilialRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ClienteFilial entity = new ClienteFilial();
        entity.setIdCliente(dto.idCliente());
        entity.setNomeFilial(dto.nomeFilial());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ClienteFilialRequestDTO dto, ClienteFilial entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setIdCliente(dto.idCliente());
        entity.setNomeFilial(dto.nomeFilial());
    }
}
