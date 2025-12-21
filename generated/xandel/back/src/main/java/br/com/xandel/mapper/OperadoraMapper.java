package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Operadora;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Operadora e DTOs.
 */
@Component
public class OperadoraMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public OperadoraResponseDTO toResponseDTO(Operadora entity) {
        if (entity == null) {
            return null;
        }
        return new OperadoraResponseDTO(
            entity.getIdOperadora(),
            entity.getNomeOperadora()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public OperadoraListDTO toListDTO(Operadora entity) {
        if (entity == null) {
            return null;
        }
        return new OperadoraListDTO(
            entity.getIdOperadora(),
            entity.getNomeOperadora()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Operadora toEntity(OperadoraRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Operadora entity = new Operadora();
        entity.setIdOperadora(dto.idOperadora());
        entity.setNomeOperadora(dto.nomeOperadora());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(OperadoraRequestDTO dto, Operadora entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeOperadora(dto.nomeOperadora());
    }
}
