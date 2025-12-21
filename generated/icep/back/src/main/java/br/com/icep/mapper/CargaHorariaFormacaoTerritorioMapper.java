package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.CargaHorariaFormacaoTerritorio;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre CargaHorariaFormacaoTerritorio e DTOs.
 */
@Component
public class CargaHorariaFormacaoTerritorioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public CargaHorariaFormacaoTerritorioResponseDTO toResponseDTO(CargaHorariaFormacaoTerritorio entity) {
        if (entity == null) {
            return null;
        }
        return new CargaHorariaFormacaoTerritorioResponseDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getTerritorioId(),
            entity.getFormacaoTerritorioId(),
            entity.getCargaHoraria()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public CargaHorariaFormacaoTerritorioListDTO toListDTO(CargaHorariaFormacaoTerritorio entity) {
        if (entity == null) {
            return null;
        }
        return new CargaHorariaFormacaoTerritorioListDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getTerritorioId(),
            entity.getFormacaoTerritorioId(),
            entity.getCargaHoraria()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public CargaHorariaFormacaoTerritorio toEntity(CargaHorariaFormacaoTerritorioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        CargaHorariaFormacaoTerritorio entity = new CargaHorariaFormacaoTerritorio();
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setTerritorioId(dto.territorioId());
        entity.setFormacaoTerritorioId(dto.formacaoTerritorioId());
        entity.setCargaHoraria(dto.cargaHoraria());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(CargaHorariaFormacaoTerritorioRequestDTO dto, CargaHorariaFormacaoTerritorio entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setTerritorioId(dto.territorioId());
        entity.setFormacaoTerritorioId(dto.formacaoTerritorioId());
        entity.setCargaHoraria(dto.cargaHoraria());
    }
}
