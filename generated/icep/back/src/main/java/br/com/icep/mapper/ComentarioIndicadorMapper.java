package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.ComentarioIndicador;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ComentarioIndicador e DTOs.
 */
@Component
public class ComentarioIndicadorMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ComentarioIndicadorResponseDTO toResponseDTO(ComentarioIndicador entity) {
        if (entity == null) {
            return null;
        }
        return new ComentarioIndicadorResponseDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getAmbitoGestaoId(),
            entity.getComentario()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ComentarioIndicadorListDTO toListDTO(ComentarioIndicador entity) {
        if (entity == null) {
            return null;
        }
        return new ComentarioIndicadorListDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getAmbitoGestaoId(),
            entity.getComentario()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ComentarioIndicador toEntity(ComentarioIndicadorRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ComentarioIndicador entity = new ComentarioIndicador();
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setAmbitoGestaoId(dto.ambitoGestaoId());
        entity.setComentario(dto.comentario());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ComentarioIndicadorRequestDTO dto, ComentarioIndicador entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setAmbitoGestaoId(dto.ambitoGestaoId());
        entity.setComentario(dto.comentario());
    }
}
