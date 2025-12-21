package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Cliente;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Cliente e DTOs.
 */
@Component
public class ClienteMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ClienteResponseDTO toResponseDTO(Cliente entity) {
        if (entity == null) {
            return null;
        }
        return new ClienteResponseDTO(
            entity.getIdCliente(),
            entity.getNomeCliente(),
            entity.getFantasiaCliente(),
            entity.getCnpj(),
            entity.getDataContrato(),
            entity.getTaxaADM(),
            entity.getEndereco(),
            entity.getCep(),
            entity.getIdBairro(),
            entity.getIdCidade(),
            entity.getContato(),
            entity.getFone1(),
            entity.getFone2(),
            entity.getEmail(),
            entity.getPessoaJuridica()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ClienteListDTO toListDTO(Cliente entity) {
        if (entity == null) {
            return null;
        }
        return new ClienteListDTO(
            entity.getIdCliente(),
            entity.getNomeCliente(),
            entity.getFantasiaCliente(),
            entity.getCnpj(),
            entity.getDataContrato(),
            entity.getTaxaADM(),
            entity.getContato(),
            entity.getEmail()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Cliente toEntity(ClienteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Cliente entity = new Cliente();
        entity.setIdCliente(dto.idCliente());
        entity.setNomeCliente(dto.nomeCliente());
        entity.setFantasiaCliente(dto.fantasiaCliente());
        entity.setCnpj(dto.cnpj());
        entity.setDataContrato(dto.dataContrato());
        entity.setTaxaADM(dto.taxaADM());
        entity.setEndereco(dto.endereco());
        entity.setCep(dto.cep());
        entity.setIdBairro(dto.idBairro());
        entity.setIdCidade(dto.idCidade());
        entity.setContato(dto.contato());
        entity.setFone1(dto.fone1());
        entity.setFone2(dto.fone2());
        entity.setEmail(dto.email());
        entity.setPessoaJuridica(dto.pessoaJuridica());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ClienteRequestDTO dto, Cliente entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeCliente(dto.nomeCliente());
        entity.setFantasiaCliente(dto.fantasiaCliente());
        entity.setCnpj(dto.cnpj());
        entity.setDataContrato(dto.dataContrato());
        entity.setTaxaADM(dto.taxaADM());
        entity.setEndereco(dto.endereco());
        entity.setCep(dto.cep());
        entity.setIdBairro(dto.idBairro());
        entity.setIdCidade(dto.idCidade());
        entity.setContato(dto.contato());
        entity.setFone1(dto.fone1());
        entity.setFone2(dto.fone2());
        entity.setEmail(dto.email());
        entity.setPessoaJuridica(dto.pessoaJuridica());
    }
}
