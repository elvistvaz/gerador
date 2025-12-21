package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Cartorio;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Cartorio e DTOs.
 */
@Component
public class CartorioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public CartorioResponseDTO toResponseDTO(Cartorio entity) {
        if (entity == null) {
            return null;
        }
        return new CartorioResponseDTO(
            entity.getIdCartorio(),
            entity.getNomeCartorio(),
            entity.getEndereco(),
            entity.getIdBairro(),
            entity.getIdCidade(),
            entity.getTelefone()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public CartorioListDTO toListDTO(Cartorio entity) {
        if (entity == null) {
            return null;
        }
        return new CartorioListDTO(
            entity.getIdCartorio(),
            entity.getNomeCartorio(),
            entity.getEndereco(),
            entity.getIdBairro(),
            entity.getIdCidade(),
            entity.getTelefone()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Cartorio toEntity(CartorioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Cartorio entity = new Cartorio();
        entity.setIdCartorio(dto.idCartorio());
        entity.setNomeCartorio(dto.nomeCartorio());
        entity.setEndereco(dto.endereco());
        entity.setIdBairro(dto.idBairro());
        entity.setIdCidade(dto.idCidade());
        entity.setTelefone(dto.telefone());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(CartorioRequestDTO dto, Cartorio entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeCartorio(dto.nomeCartorio());
        entity.setEndereco(dto.endereco());
        entity.setIdBairro(dto.idBairro());
        entity.setIdCidade(dto.idCidade());
        entity.setTelefone(dto.telefone());
    }
}
