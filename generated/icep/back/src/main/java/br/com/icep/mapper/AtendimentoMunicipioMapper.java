package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.AtendimentoMunicipio;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre AtendimentoMunicipio e DTOs.
 */
@Component
public class AtendimentoMunicipioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AtendimentoMunicipioResponseDTO toResponseDTO(AtendimentoMunicipio entity) {
        if (entity == null) {
            return null;
        }
        return new AtendimentoMunicipioResponseDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getSegmentoAtendidoId(),
            entity.getQuantidade()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AtendimentoMunicipioListDTO toListDTO(AtendimentoMunicipio entity) {
        if (entity == null) {
            return null;
        }
        return new AtendimentoMunicipioListDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getSegmentoAtendidoId(),
            entity.getQuantidade()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public AtendimentoMunicipio toEntity(AtendimentoMunicipioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AtendimentoMunicipio entity = new AtendimentoMunicipio();
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setSegmentoAtendidoId(dto.segmentoAtendidoId());
        entity.setQuantidade(dto.quantidade());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AtendimentoMunicipioRequestDTO dto, AtendimentoMunicipio entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setSegmentoAtendidoId(dto.segmentoAtendidoId());
        entity.setQuantidade(dto.quantidade());
    }
}
