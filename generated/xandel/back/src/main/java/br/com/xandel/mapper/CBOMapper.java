package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.CBO;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre CBO e DTOs.
 */
@Component
public class CBOMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public CBOResponseDTO toResponseDTO(CBO entity) {
        if (entity == null) {
            return null;
        }
        return new CBOResponseDTO(
            entity.getIdCBO(),
            entity.getNomeCBO()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public CBOListDTO toListDTO(CBO entity) {
        if (entity == null) {
            return null;
        }
        return new CBOListDTO(
            entity.getIdCBO(),
            entity.getNomeCBO()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public CBO toEntity(CBORequestDTO dto) {
        if (dto == null) {
            return null;
        }
        CBO entity = new CBO();
        entity.setIdCBO(dto.idCBO());
        entity.setNomeCBO(dto.nomeCBO());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(CBORequestDTO dto, CBO entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeCBO(dto.nomeCBO());
    }
}
