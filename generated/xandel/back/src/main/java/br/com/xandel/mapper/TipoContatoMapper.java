package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.TipoContato;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre TipoContato e DTOs.
 */
@Component
public class TipoContatoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public TipoContatoResponseDTO toResponseDTO(TipoContato entity) {
        if (entity == null) {
            return null;
        }
        return new TipoContatoResponseDTO(
            entity.getIdTipoContato(),
            entity.getNomeTipoContato()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public TipoContatoListDTO toListDTO(TipoContato entity) {
        if (entity == null) {
            return null;
        }
        return new TipoContatoListDTO(
            entity.getIdTipoContato(),
            entity.getNomeTipoContato()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public TipoContato toEntity(TipoContatoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        TipoContato entity = new TipoContato();
        entity.setIdTipoContato(dto.idTipoContato());
        entity.setNomeTipoContato(dto.nomeTipoContato());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(TipoContatoRequestDTO dto, TipoContato entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeTipoContato(dto.nomeTipoContato());
    }
}
