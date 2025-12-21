package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.DespesaReceita;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre DespesaReceita e DTOs.
 */
@Component
public class DespesaReceitaMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public DespesaReceitaResponseDTO toResponseDTO(DespesaReceita entity) {
        if (entity == null) {
            return null;
        }
        return new DespesaReceitaResponseDTO(
            entity.getIdDespesaReceita(),
            entity.getSiglaDespesaReceita(),
            entity.getNomeDespesaReceita(),
            entity.getDespesa(),
            entity.getTemRateio(),
            entity.getInativa(),
            entity.getValor(),
            entity.getParcelas()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public DespesaReceitaListDTO toListDTO(DespesaReceita entity) {
        if (entity == null) {
            return null;
        }
        return new DespesaReceitaListDTO(
            entity.getIdDespesaReceita(),
            entity.getSiglaDespesaReceita(),
            entity.getNomeDespesaReceita(),
            entity.getDespesa(),
            entity.getTemRateio(),
            entity.getInativa(),
            entity.getValor(),
            entity.getParcelas()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public DespesaReceita toEntity(DespesaReceitaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        DespesaReceita entity = new DespesaReceita();
        entity.setIdDespesaReceita(dto.idDespesaReceita());
        entity.setSiglaDespesaReceita(dto.siglaDespesaReceita());
        entity.setNomeDespesaReceita(dto.nomeDespesaReceita());
        entity.setDespesa(dto.despesa());
        entity.setTemRateio(dto.temRateio());
        entity.setInativa(dto.inativa());
        entity.setValor(dto.valor());
        entity.setParcelas(dto.parcelas());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(DespesaReceitaRequestDTO dto, DespesaReceita entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setSiglaDespesaReceita(dto.siglaDespesaReceita());
        entity.setNomeDespesaReceita(dto.nomeDespesaReceita());
        entity.setDespesa(dto.despesa());
        entity.setTemRateio(dto.temRateio());
        entity.setInativa(dto.inativa());
        entity.setValor(dto.valor());
        entity.setParcelas(dto.parcelas());
    }
}
