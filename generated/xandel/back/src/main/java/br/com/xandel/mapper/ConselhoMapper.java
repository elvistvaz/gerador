package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Conselho;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Conselho e DTOs.
 */
@Component
public class ConselhoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ConselhoResponseDTO toResponseDTO(Conselho entity) {
        if (entity == null) {
            return null;
        }
        return new ConselhoResponseDTO(
            entity.getIdConselho(),
            entity.getNomeConselho(),
            entity.getSigla()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ConselhoListDTO toListDTO(Conselho entity) {
        if (entity == null) {
            return null;
        }
        return new ConselhoListDTO(
            entity.getIdConselho(),
            entity.getNomeConselho(),
            entity.getSigla()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Conselho toEntity(ConselhoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Conselho entity = new Conselho();
        entity.setIdConselho(dto.idConselho());
        entity.setNomeConselho(dto.nomeConselho());
        entity.setSigla(dto.sigla());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ConselhoRequestDTO dto, Conselho entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeConselho(dto.nomeConselho());
        entity.setSigla(dto.sigla());
    }
}
