package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.Formacao;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Formacao e DTOs.
 */
@Component
public class FormacaoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public FormacaoResponseDTO toResponseDTO(Formacao entity) {
        if (entity == null) {
            return null;
        }
        return new FormacaoResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public FormacaoListDTO toListDTO(Formacao entity) {
        if (entity == null) {
            return null;
        }
        return new FormacaoListDTO(
            entity.getId(),
            entity.getNome(),
            entity.getOrdem()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Formacao toEntity(FormacaoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Formacao entity = new Formacao();
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(FormacaoRequestDTO dto, Formacao entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setOrdem(dto.ordem());
    }
}
