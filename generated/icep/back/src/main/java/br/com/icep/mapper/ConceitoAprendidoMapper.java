package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.ConceitoAprendido;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ConceitoAprendido e DTOs.
 */
@Component
public class ConceitoAprendidoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ConceitoAprendidoResponseDTO toResponseDTO(ConceitoAprendido entity) {
        if (entity == null) {
            return null;
        }
        return new ConceitoAprendidoResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ConceitoAprendidoListDTO toListDTO(ConceitoAprendido entity) {
        if (entity == null) {
            return null;
        }
        return new ConceitoAprendidoListDTO(
            entity.getId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ConceitoAprendido toEntity(ConceitoAprendidoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ConceitoAprendido entity = new ConceitoAprendido();
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ConceitoAprendidoRequestDTO dto, ConceitoAprendido entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
    }
}
