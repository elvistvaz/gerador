package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.AvaliacaoSDA;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre AvaliacaoSDA e DTOs.
 */
@Component
public class AvaliacaoSDAMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AvaliacaoSDAResponseDTO toResponseDTO(AvaliacaoSDA entity) {
        if (entity == null) {
            return null;
        }
        return new AvaliacaoSDAResponseDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getAnoEscolarId(),
            entity.getAprendizagemId(),
            entity.getNivel(),
            entity.getQuantidadeAlunos()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AvaliacaoSDAListDTO toListDTO(AvaliacaoSDA entity) {
        if (entity == null) {
            return null;
        }
        return new AvaliacaoSDAListDTO(
            entity.getId(),
            entity.getAvaliacaoId(),
            entity.getMunicipioId(),
            entity.getAnoEscolarId(),
            entity.getAprendizagemId(),
            entity.getNivel(),
            entity.getQuantidadeAlunos()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public AvaliacaoSDA toEntity(AvaliacaoSDARequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AvaliacaoSDA entity = new AvaliacaoSDA();
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setAnoEscolarId(dto.anoEscolarId());
        entity.setAprendizagemId(dto.aprendizagemId());
        entity.setNivel(dto.nivel());
        entity.setQuantidadeAlunos(dto.quantidadeAlunos());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AvaliacaoSDARequestDTO dto, AvaliacaoSDA entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAvaliacaoId(dto.avaliacaoId());
        entity.setMunicipioId(dto.municipioId());
        entity.setAnoEscolarId(dto.anoEscolarId());
        entity.setAprendizagemId(dto.aprendizagemId());
        entity.setNivel(dto.nivel());
        entity.setQuantidadeAlunos(dto.quantidadeAlunos());
    }
}
