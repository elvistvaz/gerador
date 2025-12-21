package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.Indicador;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Indicador e DTOs.
 */
@Component
public class IndicadorMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public IndicadorResponseDTO toResponseDTO(Indicador entity) {
        if (entity == null) {
            return null;
        }
        return new IndicadorResponseDTO(
            entity.getId(),
            entity.getAmbitoGestaoId(),
            entity.getDescricao(),
            entity.getOrdem()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public IndicadorListDTO toListDTO(Indicador entity) {
        if (entity == null) {
            return null;
        }
        return new IndicadorListDTO(
            entity.getId(),
            entity.getAmbitoGestaoId(),
            entity.getDescricao(),
            entity.getOrdem()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Indicador toEntity(IndicadorRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Indicador entity = new Indicador();
        entity.setAmbitoGestaoId(dto.ambitoGestaoId());
        entity.setDescricao(dto.descricao());
        entity.setOrdem(dto.ordem());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(IndicadorRequestDTO dto, Indicador entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAmbitoGestaoId(dto.ambitoGestaoId());
        entity.setDescricao(dto.descricao());
        entity.setOrdem(dto.ordem());
    }
}
