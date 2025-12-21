package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PagamentoNaoSocio;
import br.com.xandel.entity.PagamentoNaoSocioId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre PagamentoNaoSocio e DTOs.
 */
@Component
public class PagamentoNaoSocioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public PagamentoNaoSocioResponseDTO toResponseDTO(PagamentoNaoSocio entity) {
        if (entity == null) {
            return null;
        }
        return new PagamentoNaoSocioResponseDTO(
            entity.getId().getData(),
            entity.getId().getIdEmpresa(),
            entity.getId().getIdCliente(),
            entity.getId().getIdPessoaNaoSocio(),
            entity.getNf(),
            entity.getValorBruto(),
            entity.getRetencao(),
            entity.getValorLiquido(),
            entity.getValorTaxa(),
            entity.getValorRepasse()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public PagamentoNaoSocioListDTO toListDTO(PagamentoNaoSocio entity) {
        if (entity == null) {
            return null;
        }
        return new PagamentoNaoSocioListDTO(
            entity.getId().getData(),
            entity.getId().getIdEmpresa(),
            entity.getId().getIdCliente(),
            entity.getId().getIdPessoaNaoSocio(),
            entity.getNf(),
            entity.getValorBruto(),
            entity.getRetencao(),
            entity.getValorLiquido(),
            entity.getValorRepasse()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public PagamentoNaoSocio toEntity(PagamentoNaoSocioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        PagamentoNaoSocio entity = new PagamentoNaoSocio();
        PagamentoNaoSocioId id = new PagamentoNaoSocioId();
        id.setData(dto.data());
        id.setIdEmpresa(dto.idEmpresa());
        id.setIdCliente(dto.idCliente());
        id.setIdPessoaNaoSocio(dto.idPessoaNaoSocio());
        entity.setId(id);
        entity.setNf(dto.nf());
        entity.setValorBruto(dto.valorBruto());
        entity.setRetencao(dto.retencao());
        entity.setValorLiquido(dto.valorLiquido());
        entity.setValorTaxa(dto.valorTaxa());
        entity.setValorRepasse(dto.valorRepasse());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(PagamentoNaoSocioRequestDTO dto, PagamentoNaoSocio entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNf(dto.nf());
        entity.setValorBruto(dto.valorBruto());
        entity.setRetencao(dto.retencao());
        entity.setValorLiquido(dto.valorLiquido());
        entity.setValorTaxa(dto.valorTaxa());
        entity.setValorRepasse(dto.valorRepasse());
    }
}
