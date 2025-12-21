package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Adiantamento;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Adiantamento e DTOs.
 */
@Component
public class AdiantamentoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AdiantamentoResponseDTO toResponseDTO(Adiantamento entity) {
        if (entity == null) {
            return null;
        }
        return new AdiantamentoResponseDTO(
            entity.getIdAdiantamento(),
            entity.getData(),
            entity.getIdEmpresa(),
            entity.getIdPessoa(),
            entity.getIdCliente(),
            entity.getNf(),
            entity.getValorBruto(),
            entity.getRetencao(),
            entity.getValorLiquido(),
            entity.getValorTaxa(),
            entity.getValorRepasse(),
            entity.getIdLancamento(),
            entity.getPago()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AdiantamentoListDTO toListDTO(Adiantamento entity) {
        if (entity == null) {
            return null;
        }
        return new AdiantamentoListDTO(
            entity.getIdAdiantamento(),
            entity.getData(),
            entity.getIdEmpresa(),
            entity.getIdPessoa(),
            entity.getIdCliente(),
            entity.getNf(),
            entity.getValorBruto(),
            entity.getRetencao(),
            entity.getValorLiquido(),
            entity.getValorTaxa(),
            entity.getValorRepasse(),
            entity.getPago()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Adiantamento toEntity(AdiantamentoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Adiantamento entity = new Adiantamento();
        entity.setIdAdiantamento(dto.idAdiantamento());
        entity.setData(dto.data());
        entity.setIdEmpresa(dto.idEmpresa());
        entity.setIdPessoa(dto.idPessoa());
        entity.setIdCliente(dto.idCliente());
        entity.setNf(dto.nf());
        entity.setValorBruto(dto.valorBruto());
        entity.setRetencao(dto.retencao());
        entity.setValorLiquido(dto.valorLiquido());
        entity.setValorTaxa(dto.valorTaxa());
        entity.setValorRepasse(dto.valorRepasse());
        entity.setIdLancamento(dto.idLancamento());
        entity.setPago(dto.pago());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AdiantamentoRequestDTO dto, Adiantamento entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setData(dto.data());
        entity.setIdEmpresa(dto.idEmpresa());
        entity.setIdPessoa(dto.idPessoa());
        entity.setIdCliente(dto.idCliente());
        entity.setNf(dto.nf());
        entity.setValorBruto(dto.valorBruto());
        entity.setRetencao(dto.retencao());
        entity.setValorLiquido(dto.valorLiquido());
        entity.setValorTaxa(dto.valorTaxa());
        entity.setValorRepasse(dto.valorRepasse());
        entity.setIdLancamento(dto.idLancamento());
        entity.setPago(dto.pago());
    }
}
