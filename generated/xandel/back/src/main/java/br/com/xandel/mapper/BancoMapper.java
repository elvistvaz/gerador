package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Banco;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Banco e DTOs.
 */
@Component
public class BancoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public BancoResponseDTO toResponseDTO(Banco entity) {
        if (entity == null) {
            return null;
        }
        return new BancoResponseDTO(
            entity.getIdBanco(),
            entity.getNomeBanco()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public BancoListDTO toListDTO(Banco entity) {
        if (entity == null) {
            return null;
        }
        return new BancoListDTO(
            entity.getIdBanco(),
            entity.getNomeBanco()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Banco toEntity(BancoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Banco entity = new Banco();
        entity.setIdBanco(dto.idBanco());
        entity.setNomeBanco(dto.nomeBanco());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(BancoRequestDTO dto, Banco entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeBanco(dto.nomeBanco());
    }
}
