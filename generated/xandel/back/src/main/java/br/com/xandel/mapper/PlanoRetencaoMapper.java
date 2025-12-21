package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PlanoRetencao;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre PlanoRetencao e DTOs.
 */
@Component
public class PlanoRetencaoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public PlanoRetencaoResponseDTO toResponseDTO(PlanoRetencao entity) {
        if (entity == null) {
            return null;
        }
        return new PlanoRetencaoResponseDTO(
            entity.getIdPlanoRetencao(),
            entity.getNomePlanoRetencao(),
            entity.getInativo(),
            entity.getLiberaDespesas()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public PlanoRetencaoListDTO toListDTO(PlanoRetencao entity) {
        if (entity == null) {
            return null;
        }
        return new PlanoRetencaoListDTO(
            entity.getIdPlanoRetencao(),
            entity.getNomePlanoRetencao(),
            entity.getInativo(),
            entity.getLiberaDespesas()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public PlanoRetencao toEntity(PlanoRetencaoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        PlanoRetencao entity = new PlanoRetencao();
        entity.setIdPlanoRetencao(dto.idPlanoRetencao());
        entity.setNomePlanoRetencao(dto.nomePlanoRetencao());
        entity.setInativo(dto.inativo());
        entity.setLiberaDespesas(dto.liberaDespesas());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(PlanoRetencaoRequestDTO dto, PlanoRetencao entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomePlanoRetencao(dto.nomePlanoRetencao());
        entity.setInativo(dto.inativo());
        entity.setLiberaDespesas(dto.liberaDespesas());
    }
}
