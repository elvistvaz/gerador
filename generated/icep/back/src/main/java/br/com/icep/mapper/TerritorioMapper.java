package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.Territorio;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Territorio e DTOs.
 */
@Component
public class TerritorioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public TerritorioResponseDTO toResponseDTO(Territorio entity) {
        if (entity == null) {
            return null;
        }
        return new TerritorioResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public TerritorioListDTO toListDTO(Territorio entity) {
        if (entity == null) {
            return null;
        }
        return new TerritorioListDTO(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Territorio toEntity(TerritorioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Territorio entity = new Territorio();
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(TerritorioRequestDTO dto, Territorio entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
    }
}
