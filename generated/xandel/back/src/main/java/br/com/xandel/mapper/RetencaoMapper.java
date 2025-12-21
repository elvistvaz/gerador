package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Retencao;
import br.com.xandel.entity.RetencaoId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Retencao e DTOs.
 */
@Component
public class RetencaoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public RetencaoResponseDTO toResponseDTO(Retencao entity) {
        if (entity == null) {
            return null;
        }
        return new RetencaoResponseDTO(
            entity.getId().getIdPlanoRetencao(),
            entity.getId().getAte(),
            entity.getReter()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public RetencaoListDTO toListDTO(Retencao entity) {
        if (entity == null) {
            return null;
        }
        return new RetencaoListDTO(
            entity.getId().getIdPlanoRetencao(),
            entity.getId().getAte(),
            entity.getReter()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Retencao toEntity(RetencaoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Retencao entity = new Retencao();
        RetencaoId id = new RetencaoId();
        id.setIdPlanoRetencao(dto.idPlanoRetencao());
        id.setAte(dto.ate());
        entity.setId(id);
        entity.setReter(dto.reter());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(RetencaoRequestDTO dto, Retencao entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setReter(dto.reter());
    }
}
