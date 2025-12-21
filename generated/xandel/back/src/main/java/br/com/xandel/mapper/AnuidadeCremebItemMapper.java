package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.AnuidadeCremebItem;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre AnuidadeCremebItem e DTOs.
 */
@Component
public class AnuidadeCremebItemMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AnuidadeCremebItemResponseDTO toResponseDTO(AnuidadeCremebItem entity) {
        if (entity == null) {
            return null;
        }
        return new AnuidadeCremebItemResponseDTO(
            entity.getIdAnuidadeCremebItem(),
            entity.getIdAnuidadeCremeb(),
            entity.getIdPessoa(),
            entity.getDataLancamento(),
            entity.getValorIndividual(),
            entity.getDataPagamento(),
            entity.getIdLancamento()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AnuidadeCremebItemListDTO toListDTO(AnuidadeCremebItem entity) {
        if (entity == null) {
            return null;
        }
        return new AnuidadeCremebItemListDTO(
            entity.getIdAnuidadeCremebItem(),
            entity.getIdPessoa(),
            entity.getDataLancamento(),
            entity.getValorIndividual(),
            entity.getDataPagamento()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public AnuidadeCremebItem toEntity(AnuidadeCremebItemRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AnuidadeCremebItem entity = new AnuidadeCremebItem();
        entity.setIdAnuidadeCremebItem(dto.idAnuidadeCremebItem());
        entity.setIdAnuidadeCremeb(dto.idAnuidadeCremeb());
        entity.setIdPessoa(dto.idPessoa());
        entity.setDataLancamento(dto.dataLancamento());
        entity.setValorIndividual(dto.valorIndividual());
        entity.setDataPagamento(dto.dataPagamento());
        entity.setIdLancamento(dto.idLancamento());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AnuidadeCremebItemRequestDTO dto, AnuidadeCremebItem entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setIdAnuidadeCremeb(dto.idAnuidadeCremeb());
        entity.setIdPessoa(dto.idPessoa());
        entity.setDataLancamento(dto.dataLancamento());
        entity.setValorIndividual(dto.valorIndividual());
        entity.setDataPagamento(dto.dataPagamento());
        entity.setIdLancamento(dto.idLancamento());
    }
}
