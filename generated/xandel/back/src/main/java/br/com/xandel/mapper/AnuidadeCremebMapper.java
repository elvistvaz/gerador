package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.AnuidadeCremeb;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre AnuidadeCremeb e DTOs.
 */
@Component
public class AnuidadeCremebMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AnuidadeCremebResponseDTO toResponseDTO(AnuidadeCremeb entity) {
        if (entity == null) {
            return null;
        }
        return new AnuidadeCremebResponseDTO(
            entity.getIdAnuidadeCremeb(),
            entity.getAnoExercicio(),
            entity.getDataInicio(),
            entity.getDataFim(),
            entity.getValorTotal(),
            entity.getIdEmpresa()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AnuidadeCremebListDTO toListDTO(AnuidadeCremeb entity) {
        if (entity == null) {
            return null;
        }
        return new AnuidadeCremebListDTO(
            entity.getIdAnuidadeCremeb(),
            entity.getAnoExercicio(),
            entity.getDataInicio(),
            entity.getDataFim(),
            entity.getValorTotal(),
            entity.getIdEmpresa()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public AnuidadeCremeb toEntity(AnuidadeCremebRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AnuidadeCremeb entity = new AnuidadeCremeb();
        entity.setIdAnuidadeCremeb(dto.idAnuidadeCremeb());
        entity.setAnoExercicio(dto.anoExercicio());
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setValorTotal(dto.valorTotal());
        entity.setIdEmpresa(dto.idEmpresa());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AnuidadeCremebRequestDTO dto, AnuidadeCremeb entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAnoExercicio(dto.anoExercicio());
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setValorTotal(dto.valorTotal());
        entity.setIdEmpresa(dto.idEmpresa());
    }
}
