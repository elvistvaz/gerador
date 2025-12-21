package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PessoaContaCorrente;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre PessoaContaCorrente e DTOs.
 */
@Component
public class PessoaContaCorrenteMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public PessoaContaCorrenteResponseDTO toResponseDTO(PessoaContaCorrente entity) {
        if (entity == null) {
            return null;
        }
        return new PessoaContaCorrenteResponseDTO(
            entity.getIdContaCorrente(),
            entity.getIdPessoa(),
            entity.getIdBanco(),
            entity.getAgencia(),
            entity.getContaCorrente(),
            entity.getDvContaCorrente(),
            entity.getAtiva(),
            entity.getContaPoupanca(),
            entity.getContaPadrao(),
            entity.getNomeDependente(),
            entity.getPix()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public PessoaContaCorrenteListDTO toListDTO(PessoaContaCorrente entity) {
        if (entity == null) {
            return null;
        }
        return new PessoaContaCorrenteListDTO(
            entity.getIdContaCorrente(),
            entity.getIdBanco(),
            entity.getAgencia(),
            entity.getContaCorrente(),
            entity.getDvContaCorrente(),
            entity.getAtiva(),
            entity.getContaPoupanca(),
            entity.getContaPadrao(),
            entity.getPix()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public PessoaContaCorrente toEntity(PessoaContaCorrenteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        PessoaContaCorrente entity = new PessoaContaCorrente();
        entity.setIdPessoa(dto.idPessoa());
        entity.setIdBanco(dto.idBanco());
        entity.setAgencia(dto.agencia());
        entity.setContaCorrente(dto.contaCorrente());
        entity.setDvContaCorrente(dto.dvContaCorrente());
        entity.setAtiva(dto.ativa());
        entity.setContaPoupanca(dto.contaPoupanca());
        entity.setContaPadrao(dto.contaPadrao());
        entity.setNomeDependente(dto.nomeDependente());
        entity.setPix(dto.pix());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(PessoaContaCorrenteRequestDTO dto, PessoaContaCorrente entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setIdPessoa(dto.idPessoa());
        entity.setIdBanco(dto.idBanco());
        entity.setAgencia(dto.agencia());
        entity.setContaCorrente(dto.contaCorrente());
        entity.setDvContaCorrente(dto.dvContaCorrente());
        entity.setAtiva(dto.ativa());
        entity.setContaPoupanca(dto.contaPoupanca());
        entity.setContaPadrao(dto.contaPadrao());
        entity.setNomeDependente(dto.nomeDependente());
        entity.setPix(dto.pix());
    }
}
