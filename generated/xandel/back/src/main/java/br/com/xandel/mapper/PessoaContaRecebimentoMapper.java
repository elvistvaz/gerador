package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PessoaContaRecebimento;
import br.com.xandel.entity.PessoaContaRecebimentoId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre PessoaContaRecebimento e DTOs.
 */
@Component
public class PessoaContaRecebimentoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public PessoaContaRecebimentoResponseDTO toResponseDTO(PessoaContaRecebimento entity) {
        if (entity == null) {
            return null;
        }
        return new PessoaContaRecebimentoResponseDTO(
            entity.getId().getIdPessoa(),
            entity.getId().getIdCliente(),
            entity.getId().getIdContaCorrente()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public PessoaContaRecebimentoListDTO toListDTO(PessoaContaRecebimento entity) {
        if (entity == null) {
            return null;
        }
        return new PessoaContaRecebimentoListDTO(
            entity.getId().getIdPessoa(),
            entity.getId().getIdCliente(),
            entity.getId().getIdContaCorrente()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public PessoaContaRecebimento toEntity(PessoaContaRecebimentoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        PessoaContaRecebimento entity = new PessoaContaRecebimento();
        PessoaContaRecebimentoId id = new PessoaContaRecebimentoId();
        id.setIdPessoa(dto.idPessoa());
        id.setIdCliente(dto.idCliente());
        id.setIdContaCorrente(dto.idContaCorrente());
        entity.setId(id);
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(PessoaContaRecebimentoRequestDTO dto, PessoaContaRecebimento entity) {
        if (dto == null || entity == null) {
            return;
        }
    }
}
