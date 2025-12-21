package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ImpostoDeRenda;
import br.com.xandel.entity.ImpostoDeRendaId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ImpostoDeRenda e DTOs.
 */
@Component
public class ImpostoDeRendaMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ImpostoDeRendaResponseDTO toResponseDTO(ImpostoDeRenda entity) {
        if (entity == null) {
            return null;
        }
        return new ImpostoDeRendaResponseDTO(
            entity.getId().getData(),
            entity.getId().getDe(),
            entity.getAte(),
            entity.getAliquota(),
            entity.getValorDeduzir(),
            entity.getDeducaoDependente()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ImpostoDeRendaListDTO toListDTO(ImpostoDeRenda entity) {
        if (entity == null) {
            return null;
        }
        return new ImpostoDeRendaListDTO(
            entity.getId().getData(),
            entity.getId().getDe(),
            entity.getAte(),
            entity.getAliquota(),
            entity.getValorDeduzir(),
            entity.getDeducaoDependente()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ImpostoDeRenda toEntity(ImpostoDeRendaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ImpostoDeRenda entity = new ImpostoDeRenda();
        ImpostoDeRendaId id = new ImpostoDeRendaId();
        id.setData(dto.data());
        id.setDe(dto.de());
        entity.setId(id);
        entity.setAte(dto.ate());
        entity.setAliquota(dto.aliquota());
        entity.setValorDeduzir(dto.valorDeduzir());
        entity.setDeducaoDependente(dto.deducaoDependente());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ImpostoDeRendaRequestDTO dto, ImpostoDeRenda entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAte(dto.ate());
        entity.setAliquota(dto.aliquota());
        entity.setValorDeduzir(dto.valorDeduzir());
        entity.setDeducaoDependente(dto.deducaoDependente());
    }
}
