package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.CargaHorariaFormacao;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre CargaHorariaFormacao e DTOs.
 */
@Component
public class CargaHorariaFormacaoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public CargaHorariaFormacaoResponseDTO toResponseDTO(CargaHorariaFormacao entity) {
        if (entity == null) {
            return null;
        }
        return new CargaHorariaFormacaoResponseDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getFormacaoId(),
            entity.getCargaHoraria()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public CargaHorariaFormacaoListDTO toListDTO(CargaHorariaFormacao entity) {
        if (entity == null) {
            return null;
        }
        return new CargaHorariaFormacaoListDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getFormacaoId(),
            entity.getCargaHoraria()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public CargaHorariaFormacao toEntity(CargaHorariaFormacaoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        CargaHorariaFormacao entity = new CargaHorariaFormacao();
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setFormacaoId(dto.formacaoId());
        entity.setCargaHoraria(dto.cargaHoraria());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(CargaHorariaFormacaoRequestDTO dto, CargaHorariaFormacao entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setFormacaoId(dto.formacaoId());
        entity.setCargaHoraria(dto.cargaHoraria());
    }
}
