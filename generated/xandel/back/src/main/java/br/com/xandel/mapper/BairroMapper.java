package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Bairro;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Bairro e DTOs.
 */
@Component
public class BairroMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public BairroResponseDTO toResponseDTO(Bairro entity) {
        if (entity == null) {
            return null;
        }
        return new BairroResponseDTO(
            entity.getIdBairro(),
            entity.getNomeBairro()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public BairroListDTO toListDTO(Bairro entity) {
        if (entity == null) {
            return null;
        }
        return new BairroListDTO(
            entity.getIdBairro(),
            entity.getNomeBairro()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Bairro toEntity(BairroRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Bairro entity = new Bairro();
        entity.setIdBairro(dto.idBairro());
        entity.setNomeBairro(dto.nomeBairro());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(BairroRequestDTO dto, Bairro entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeBairro(dto.nomeBairro());
    }
}
