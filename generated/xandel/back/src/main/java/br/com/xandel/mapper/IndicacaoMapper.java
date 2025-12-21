package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Indicacao;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Indicacao e DTOs.
 */
@Component
public class IndicacaoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public IndicacaoResponseDTO toResponseDTO(Indicacao entity) {
        if (entity == null) {
            return null;
        }
        return new IndicacaoResponseDTO(
            entity.getIdIndicacao(),
            entity.getDe(),
            entity.getAte(),
            entity.getIndicacaoMinima(),
            entity.getIndicacaoMaxima(),
            entity.getIndice(),
            entity.getTetoIndice(),
            entity.getGrupoIndicados()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public IndicacaoListDTO toListDTO(Indicacao entity) {
        if (entity == null) {
            return null;
        }
        return new IndicacaoListDTO(
            entity.getIdIndicacao(),
            entity.getDe(),
            entity.getAte(),
            entity.getIndicacaoMinima(),
            entity.getIndicacaoMaxima(),
            entity.getIndice(),
            entity.getTetoIndice(),
            entity.getGrupoIndicados()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Indicacao toEntity(IndicacaoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Indicacao entity = new Indicacao();
        entity.setIdIndicacao(dto.idIndicacao());
        entity.setDe(dto.de());
        entity.setAte(dto.ate());
        entity.setIndicacaoMinima(dto.indicacaoMinima());
        entity.setIndicacaoMaxima(dto.indicacaoMaxima());
        entity.setIndice(dto.indice());
        entity.setTetoIndice(dto.tetoIndice());
        entity.setGrupoIndicados(dto.grupoIndicados());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(IndicacaoRequestDTO dto, Indicacao entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setDe(dto.de());
        entity.setAte(dto.ate());
        entity.setIndicacaoMinima(dto.indicacaoMinima());
        entity.setIndicacaoMaxima(dto.indicacaoMaxima());
        entity.setIndice(dto.indice());
        entity.setTetoIndice(dto.tetoIndice());
        entity.setGrupoIndicados(dto.grupoIndicados());
    }
}
