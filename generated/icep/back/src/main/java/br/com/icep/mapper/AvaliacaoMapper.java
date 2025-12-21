package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.Avaliacao;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Avaliacao e DTOs.
 */
@Component
public class AvaliacaoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AvaliacaoResponseDTO toResponseDTO(Avaliacao entity) {
        if (entity == null) {
            return null;
        }
        return new AvaliacaoResponseDTO(
            entity.getId(),
            entity.getAvaliacao()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AvaliacaoListDTO toListDTO(Avaliacao entity) {
        if (entity == null) {
            return null;
        }
        return new AvaliacaoListDTO(
            entity.getId(),
            entity.getAvaliacao()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Avaliacao toEntity(AvaliacaoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Avaliacao entity = new Avaliacao();
        entity.setAvaliacao(dto.avaliacao());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AvaliacaoRequestDTO dto, Avaliacao entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAvaliacao(dto.avaliacao());
    }
}
