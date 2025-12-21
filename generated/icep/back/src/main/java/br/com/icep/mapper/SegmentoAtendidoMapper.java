package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.SegmentoAtendido;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre SegmentoAtendido e DTOs.
 */
@Component
public class SegmentoAtendidoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public SegmentoAtendidoResponseDTO toResponseDTO(SegmentoAtendido entity) {
        if (entity == null) {
            return null;
        }
        return new SegmentoAtendidoResponseDTO(
            entity.getId(),
            entity.getPublicoAlvoId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public SegmentoAtendidoListDTO toListDTO(SegmentoAtendido entity) {
        if (entity == null) {
            return null;
        }
        return new SegmentoAtendidoListDTO(
            entity.getId(),
            entity.getPublicoAlvoId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public SegmentoAtendido toEntity(SegmentoAtendidoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        SegmentoAtendido entity = new SegmentoAtendido();
        entity.setPublicoAlvoId(dto.publicoAlvoId());
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(SegmentoAtendidoRequestDTO dto, SegmentoAtendido entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setPublicoAlvoId(dto.publicoAlvoId());
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
    }
}
