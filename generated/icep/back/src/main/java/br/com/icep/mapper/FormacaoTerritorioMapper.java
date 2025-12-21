package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.FormacaoTerritorio;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre FormacaoTerritorio e DTOs.
 */
@Component
public class FormacaoTerritorioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public FormacaoTerritorioResponseDTO toResponseDTO(FormacaoTerritorio entity) {
        if (entity == null) {
            return null;
        }
        return new FormacaoTerritorioResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public FormacaoTerritorioListDTO toListDTO(FormacaoTerritorio entity) {
        if (entity == null) {
            return null;
        }
        return new FormacaoTerritorioListDTO(
            entity.getId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public FormacaoTerritorio toEntity(FormacaoTerritorioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        FormacaoTerritorio entity = new FormacaoTerritorio();
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(FormacaoTerritorioRequestDTO dto, FormacaoTerritorio entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
    }
}
