package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.AmbitoGestao;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre AmbitoGestao e DTOs.
 */
@Component
public class AmbitoGestaoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AmbitoGestaoResponseDTO toResponseDTO(AmbitoGestao entity) {
        if (entity == null) {
            return null;
        }
        return new AmbitoGestaoResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AmbitoGestaoListDTO toListDTO(AmbitoGestao entity) {
        if (entity == null) {
            return null;
        }
        return new AmbitoGestaoListDTO(
            entity.getId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public AmbitoGestao toEntity(AmbitoGestaoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AmbitoGestao entity = new AmbitoGestao();
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AmbitoGestaoRequestDTO dto, AmbitoGestao entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
    }
}
