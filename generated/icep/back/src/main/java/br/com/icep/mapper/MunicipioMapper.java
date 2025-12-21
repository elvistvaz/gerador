package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.Municipio;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Municipio e DTOs.
 */
@Component
public class MunicipioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public MunicipioResponseDTO toResponseDTO(Municipio entity) {
        if (entity == null) {
            return null;
        }
        return new MunicipioResponseDTO(
            entity.getId(),
            entity.getTerritorioId(),
            entity.getNome(),
            entity.getUf(),
            entity.getQuantidadeEscolas()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public MunicipioListDTO toListDTO(Municipio entity) {
        if (entity == null) {
            return null;
        }
        return new MunicipioListDTO(
            entity.getId(),
            entity.getTerritorioId(),
            entity.getNome(),
            entity.getUf(),
            entity.getQuantidadeEscolas()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Municipio toEntity(MunicipioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Municipio entity = new Municipio();
        entity.setTerritorioId(dto.territorioId());
        entity.setNome(dto.nome());
        entity.setUf(dto.uf());
        entity.setQuantidadeEscolas(dto.quantidadeEscolas());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(MunicipioRequestDTO dto, Municipio entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTerritorioId(dto.territorioId());
        entity.setNome(dto.nome());
        entity.setUf(dto.uf());
        entity.setQuantidadeEscolas(dto.quantidadeEscolas());
    }
}
