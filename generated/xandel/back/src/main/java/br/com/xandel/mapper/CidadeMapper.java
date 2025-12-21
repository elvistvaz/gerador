package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Cidade;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Cidade e DTOs.
 */
@Component
public class CidadeMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public CidadeResponseDTO toResponseDTO(Cidade entity) {
        if (entity == null) {
            return null;
        }
        return new CidadeResponseDTO(
            entity.getIdCidade(),
            entity.getNomeCidade(),
            entity.getUf(),
            entity.getDdd(),
            entity.getIss()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public CidadeListDTO toListDTO(Cidade entity) {
        if (entity == null) {
            return null;
        }
        return new CidadeListDTO(
            entity.getIdCidade(),
            entity.getNomeCidade(),
            entity.getUf(),
            entity.getDdd(),
            entity.getIss()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Cidade toEntity(CidadeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Cidade entity = new Cidade();
        entity.setIdCidade(dto.idCidade());
        entity.setNomeCidade(dto.nomeCidade());
        entity.setUf(dto.uf());
        entity.setDdd(dto.ddd());
        entity.setIss(dto.iss());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(CidadeRequestDTO dto, Cidade entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeCidade(dto.nomeCidade());
        entity.setUf(dto.uf());
        entity.setDdd(dto.ddd());
        entity.setIss(dto.iss());
    }
}
