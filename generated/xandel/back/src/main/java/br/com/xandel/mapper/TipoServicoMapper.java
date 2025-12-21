package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.TipoServico;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre TipoServico e DTOs.
 */
@Component
public class TipoServicoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public TipoServicoResponseDTO toResponseDTO(TipoServico entity) {
        if (entity == null) {
            return null;
        }
        return new TipoServicoResponseDTO(
            entity.getIdTipoServico(),
            entity.getNomeTipoServico()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public TipoServicoListDTO toListDTO(TipoServico entity) {
        if (entity == null) {
            return null;
        }
        return new TipoServicoListDTO(
            entity.getIdTipoServico(),
            entity.getNomeTipoServico()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public TipoServico toEntity(TipoServicoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        TipoServico entity = new TipoServico();
        entity.setIdTipoServico(dto.idTipoServico());
        entity.setNomeTipoServico(dto.nomeTipoServico());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(TipoServicoRequestDTO dto, TipoServico entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeTipoServico(dto.nomeTipoServico());
    }
}
