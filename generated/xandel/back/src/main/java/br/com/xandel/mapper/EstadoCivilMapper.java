package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.EstadoCivil;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre EstadoCivil e DTOs.
 */
@Component
public class EstadoCivilMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public EstadoCivilResponseDTO toResponseDTO(EstadoCivil entity) {
        if (entity == null) {
            return null;
        }
        return new EstadoCivilResponseDTO(
            entity.getIdEstadoCivil(),
            entity.getNomeEstadoCivil()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public EstadoCivilListDTO toListDTO(EstadoCivil entity) {
        if (entity == null) {
            return null;
        }
        return new EstadoCivilListDTO(
            entity.getIdEstadoCivil(),
            entity.getNomeEstadoCivil()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public EstadoCivil toEntity(EstadoCivilRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        EstadoCivil entity = new EstadoCivil();
        entity.setIdEstadoCivil(dto.idEstadoCivil());
        entity.setNomeEstadoCivil(dto.nomeEstadoCivil());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(EstadoCivilRequestDTO dto, EstadoCivil entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeEstadoCivil(dto.nomeEstadoCivil());
    }
}
