package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PessoaCartorio;
import br.com.xandel.entity.PessoaCartorioId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre PessoaCartorio e DTOs.
 */
@Component
public class PessoaCartorioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public PessoaCartorioResponseDTO toResponseDTO(PessoaCartorio entity) {
        if (entity == null) {
            return null;
        }
        return new PessoaCartorioResponseDTO(
            entity.getId().getIdPessoa(),
            entity.getId().getIdCartorio()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public PessoaCartorioListDTO toListDTO(PessoaCartorio entity) {
        if (entity == null) {
            return null;
        }
        return new PessoaCartorioListDTO(
            entity.getId().getIdPessoa(),
            entity.getId().getIdCartorio()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public PessoaCartorio toEntity(PessoaCartorioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        PessoaCartorio entity = new PessoaCartorio();
        PessoaCartorioId id = new PessoaCartorioId();
        id.setIdPessoa(dto.idPessoa());
        id.setIdCartorio(dto.idCartorio());
        entity.setId(id);
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(PessoaCartorioRequestDTO dto, PessoaCartorio entity) {
        if (dto == null || entity == null) {
            return;
        }
    }
}
