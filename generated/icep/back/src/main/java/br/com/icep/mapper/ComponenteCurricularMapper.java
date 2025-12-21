package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.ComponenteCurricular;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ComponenteCurricular e DTOs.
 */
@Component
public class ComponenteCurricularMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ComponenteCurricularResponseDTO toResponseDTO(ComponenteCurricular entity) {
        if (entity == null) {
            return null;
        }
        return new ComponenteCurricularResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getSigla()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ComponenteCurricularListDTO toListDTO(ComponenteCurricular entity) {
        if (entity == null) {
            return null;
        }
        return new ComponenteCurricularListDTO(
            entity.getId(),
            entity.getNome(),
            entity.getSigla()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ComponenteCurricular toEntity(ComponenteCurricularRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ComponenteCurricular entity = new ComponenteCurricular();
        entity.setNome(dto.nome());
        entity.setSigla(dto.sigla());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ComponenteCurricularRequestDTO dto, ComponenteCurricular entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setSigla(dto.sigla());
    }
}
