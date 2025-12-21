package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ParametroNF;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ParametroNF e DTOs.
 */
@Component
public class ParametroNFMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ParametroNFResponseDTO toResponseDTO(ParametroNF entity) {
        if (entity == null) {
            return null;
        }
        return new ParametroNFResponseDTO(
            entity.getId(),
            entity.getCofins(),
            entity.getPis(),
            entity.getCsll(),
            entity.getIrrf(),
            entity.getTetoImposto(),
            entity.getBasePisCofinsCsll()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ParametroNFListDTO toListDTO(ParametroNF entity) {
        if (entity == null) {
            return null;
        }
        return new ParametroNFListDTO(
            entity.getId(),
            entity.getCofins(),
            entity.getPis(),
            entity.getCsll(),
            entity.getIrrf(),
            entity.getTetoImposto(),
            entity.getBasePisCofinsCsll()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ParametroNF toEntity(ParametroNFRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ParametroNF entity = new ParametroNF();
        entity.setCofins(dto.cofins());
        entity.setPis(dto.pis());
        entity.setCsll(dto.csll());
        entity.setIrrf(dto.irrf());
        entity.setTetoImposto(dto.tetoImposto());
        entity.setBasePisCofinsCsll(dto.basePisCofinsCsll());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ParametroNFRequestDTO dto, ParametroNF entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setCofins(dto.cofins());
        entity.setPis(dto.pis());
        entity.setCsll(dto.csll());
        entity.setIrrf(dto.irrf());
        entity.setTetoImposto(dto.tetoImposto());
        entity.setBasePisCofinsCsll(dto.basePisCofinsCsll());
    }
}
