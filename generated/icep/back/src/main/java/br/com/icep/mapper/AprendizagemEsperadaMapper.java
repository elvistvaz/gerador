package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.AprendizagemEsperada;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre AprendizagemEsperada e DTOs.
 */
@Component
public class AprendizagemEsperadaMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AprendizagemEsperadaResponseDTO toResponseDTO(AprendizagemEsperada entity) {
        if (entity == null) {
            return null;
        }
        return new AprendizagemEsperadaResponseDTO(
            entity.getId(),
            entity.getComponenteId(),
            entity.getConceitoAprendidoId(),
            entity.getDescricao(),
            entity.getCodigo()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AprendizagemEsperadaListDTO toListDTO(AprendizagemEsperada entity) {
        if (entity == null) {
            return null;
        }
        return new AprendizagemEsperadaListDTO(
            entity.getId(),
            entity.getComponenteId(),
            entity.getConceitoAprendidoId(),
            entity.getDescricao(),
            entity.getCodigo()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public AprendizagemEsperada toEntity(AprendizagemEsperadaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AprendizagemEsperada entity = new AprendizagemEsperada();
        entity.setComponenteId(dto.componenteId());
        entity.setConceitoAprendidoId(dto.conceitoAprendidoId());
        entity.setDescricao(dto.descricao());
        entity.setCodigo(dto.codigo());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AprendizagemEsperadaRequestDTO dto, AprendizagemEsperada entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setComponenteId(dto.componenteId());
        entity.setConceitoAprendidoId(dto.conceitoAprendidoId());
        entity.setDescricao(dto.descricao());
        entity.setCodigo(dto.codigo());
    }
}
