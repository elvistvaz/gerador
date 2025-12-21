package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.PublicoAlvo;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre PublicoAlvo e DTOs.
 */
@Component
public class PublicoAlvoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public PublicoAlvoResponseDTO toResponseDTO(PublicoAlvo entity) {
        if (entity == null) {
            return null;
        }
        return new PublicoAlvoResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getTipo()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public PublicoAlvoListDTO toListDTO(PublicoAlvo entity) {
        if (entity == null) {
            return null;
        }
        return new PublicoAlvoListDTO(
            entity.getId(),
            entity.getNome(),
            entity.getTipo()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public PublicoAlvo toEntity(PublicoAlvoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        PublicoAlvo entity = new PublicoAlvo();
        entity.setNome(dto.nome());
        entity.setTipo(dto.tipo());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(PublicoAlvoRequestDTO dto, PublicoAlvo entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setTipo(dto.tipo());
    }
}
