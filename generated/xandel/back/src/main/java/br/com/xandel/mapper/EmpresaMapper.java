package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Empresa;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Empresa e DTOs.
 */
@Component
public class EmpresaMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public EmpresaResponseDTO toResponseDTO(Empresa entity) {
        if (entity == null) {
            return null;
        }
        return new EmpresaResponseDTO(
            entity.getIdEmpresa(),
            entity.getNomeEmpresa(),
            entity.getFantasiaEmpresa(),
            entity.getCnpj(),
            entity.getTaxaRetencao(),
            entity.getInativa()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public EmpresaListDTO toListDTO(Empresa entity) {
        if (entity == null) {
            return null;
        }
        return new EmpresaListDTO(
            entity.getIdEmpresa(),
            entity.getNomeEmpresa(),
            entity.getFantasiaEmpresa(),
            entity.getCnpj(),
            entity.getTaxaRetencao(),
            entity.getInativa()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Empresa toEntity(EmpresaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Empresa entity = new Empresa();
        entity.setIdEmpresa(dto.idEmpresa());
        entity.setNomeEmpresa(dto.nomeEmpresa());
        entity.setFantasiaEmpresa(dto.fantasiaEmpresa());
        entity.setCnpj(dto.cnpj());
        entity.setTaxaRetencao(dto.taxaRetencao());
        entity.setInativa(dto.inativa());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(EmpresaRequestDTO dto, Empresa entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNomeEmpresa(dto.nomeEmpresa());
        entity.setFantasiaEmpresa(dto.fantasiaEmpresa());
        entity.setCnpj(dto.cnpj());
        entity.setTaxaRetencao(dto.taxaRetencao());
        entity.setInativa(dto.inativa());
    }
}
