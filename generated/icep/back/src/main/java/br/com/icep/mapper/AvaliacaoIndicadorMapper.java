package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.AvaliacaoIndicador;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre AvaliacaoIndicador e DTOs.
 */
@Component
public class AvaliacaoIndicadorMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AvaliacaoIndicadorResponseDTO toResponseDTO(AvaliacaoIndicador entity) {
        if (entity == null) {
            return null;
        }
        return new AvaliacaoIndicadorResponseDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getIndicadorId(),
            entity.getValor()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AvaliacaoIndicadorListDTO toListDTO(AvaliacaoIndicador entity) {
        if (entity == null) {
            return null;
        }
        return new AvaliacaoIndicadorListDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getIndicadorId(),
            entity.getValor()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public AvaliacaoIndicador toEntity(AvaliacaoIndicadorRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AvaliacaoIndicador entity = new AvaliacaoIndicador();
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setIndicadorId(dto.indicadorId());
        entity.setValor(dto.valor());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AvaliacaoIndicadorRequestDTO dto, AvaliacaoIndicador entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setIndicadorId(dto.indicadorId());
        entity.setValor(dto.valor());
    }
}
