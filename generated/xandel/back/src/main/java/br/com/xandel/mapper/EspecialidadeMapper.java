package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Especialidade;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Especialidade e DTOs.
 */
@Component
public class EspecialidadeMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public EspecialidadeResponseDTO toResponseDTO(Especialidade entity) {
        if (entity == null) {
            return null;
        }
        return new EspecialidadeResponseDTO(
            entity.getIdEspecialidade(),
            entity.getNomeEspecialidade()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public EspecialidadeListDTO toListDTO(Especialidade entity) {
        if (entity == null) {
            return null;
        }
        return new EspecialidadeListDTO(
            entity.getIdEspecialidade(),
            entity.getNomeEspecialidade()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Especialidade toEntity(EspecialidadeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Especialidade entity = new Especialidade();
        entity.setIdEspecialidade(dto.idEspecialidade());
        entity.setNomeEspecialidade(dto.nomeEspecialidade());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(EspecialidadeRequestDTO dto, Especialidade entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeEspecialidade(dto.nomeEspecialidade());
    }
}
