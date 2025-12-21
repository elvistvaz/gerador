package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.MedicoEspecialidade;
import br.com.xandel.entity.MedicoEspecialidadeId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre MedicoEspecialidade e DTOs.
 */
@Component
public class MedicoEspecialidadeMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public MedicoEspecialidadeResponseDTO toResponseDTO(MedicoEspecialidade entity) {
        if (entity == null) {
            return null;
        }
        return new MedicoEspecialidadeResponseDTO(
            entity.getId().getIdPessoa(),
            entity.getId().getIdEspecialidade()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public MedicoEspecialidadeListDTO toListDTO(MedicoEspecialidade entity) {
        if (entity == null) {
            return null;
        }
        return new MedicoEspecialidadeListDTO(
            entity.getId().getIdPessoa(),
            entity.getId().getIdEspecialidade()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public MedicoEspecialidade toEntity(MedicoEspecialidadeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        MedicoEspecialidade entity = new MedicoEspecialidade();
        MedicoEspecialidadeId id = new MedicoEspecialidadeId();
        id.setIdPessoa(dto.idPessoa());
        id.setIdEspecialidade(dto.idEspecialidade());
        entity.setId(id);
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(MedicoEspecialidadeRequestDTO dto, MedicoEspecialidade entity) {
        if (dto == null || entity == null) {
            return;
        }
    }
}
