package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Pessoa;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Pessoa e DTOs.
 */
@Component
public class PessoaMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public PessoaResponseDTO toResponseDTO(Pessoa entity) {
        if (entity == null) {
            return null;
        }
        return new PessoaResponseDTO(
            entity.getIdPessoa(),
            entity.getNome(),
            entity.getCpf(),
            entity.getIdConselho(),
            entity.getNumeroConselho(),
            entity.getNascimento(),
            entity.getRg(),
            entity.getEmail(),
            entity.getTelefone(),
            entity.getCelular(),
            entity.getEndereco(),
            entity.getIdBairro(),
            entity.getIdCidade(),
            entity.getCep(),
            entity.getIdEstadoCivil(),
            entity.getDataAdesao(),
            entity.getDataInativo(),
            entity.getIdBanco(),
            entity.getAgencia(),
            entity.getConta(),
            entity.getIdPlanoRetencao(),
            entity.getIdOperadora()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public PessoaListDTO toListDTO(Pessoa entity) {
        if (entity == null) {
            return null;
        }
        return new PessoaListDTO(
            entity.getIdPessoa(),
            entity.getNome(),
            entity.getCpf(),
            entity.getIdConselho(),
            entity.getNumeroConselho(),
            entity.getNascimento(),
            entity.getEmail(),
            entity.getCelular(),
            entity.getDataAdesao(),
            entity.getDataInativo()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Pessoa toEntity(PessoaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Pessoa entity = new Pessoa();
        entity.setIdPessoa(dto.idPessoa());
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setIdConselho(dto.idConselho());
        entity.setNumeroConselho(dto.numeroConselho());
        entity.setNascimento(dto.nascimento());
        entity.setRg(dto.rg());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setCelular(dto.celular());
        entity.setEndereco(dto.endereco());
        entity.setIdBairro(dto.idBairro());
        entity.setIdCidade(dto.idCidade());
        entity.setCep(dto.cep());
        entity.setIdEstadoCivil(dto.idEstadoCivil());
        entity.setDataAdesao(dto.dataAdesao());
        entity.setDataInativo(dto.dataInativo());
        entity.setIdBanco(dto.idBanco());
        entity.setAgencia(dto.agencia());
        entity.setConta(dto.conta());
        entity.setIdPlanoRetencao(dto.idPlanoRetencao());
        entity.setIdOperadora(dto.idOperadora());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(PessoaRequestDTO dto, Pessoa entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setIdConselho(dto.idConselho());
        entity.setNumeroConselho(dto.numeroConselho());
        entity.setNascimento(dto.nascimento());
        entity.setRg(dto.rg());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setCelular(dto.celular());
        entity.setEndereco(dto.endereco());
        entity.setIdBairro(dto.idBairro());
        entity.setIdCidade(dto.idCidade());
        entity.setCep(dto.cep());
        entity.setIdEstadoCivil(dto.idEstadoCivil());
        entity.setDataAdesao(dto.dataAdesao());
        entity.setDataInativo(dto.dataInativo());
        entity.setIdBanco(dto.idBanco());
        entity.setAgencia(dto.agencia());
        entity.setConta(dto.conta());
        entity.setIdPlanoRetencao(dto.idPlanoRetencao());
        entity.setIdOperadora(dto.idOperadora());
    }
}
