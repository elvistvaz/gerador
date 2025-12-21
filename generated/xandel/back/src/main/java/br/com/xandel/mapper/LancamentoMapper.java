package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Lancamento;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Lancamento e DTOs.
 */
@Component
public class LancamentoMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public LancamentoResponseDTO toResponseDTO(Lancamento entity) {
        if (entity == null) {
            return null;
        }
        return new LancamentoResponseDTO(
            entity.getIdLancamento(),
            entity.getData(),
            entity.getIdEmpresa(),
            entity.getIdCliente(),
            entity.getIdPessoa(),
            entity.getNf(),
            entity.getValorBruto(),
            entity.getDespesas(),
            entity.getRetencao(),
            entity.getValorLiquido(),
            entity.getValorTaxa(),
            entity.getValorRepasse(),
            entity.getBaixa(),
            entity.getMesAno(),
            entity.getTaxa(),
            entity.getIdTipoServico()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public LancamentoListDTO toListDTO(Lancamento entity) {
        if (entity == null) {
            return null;
        }
        return new LancamentoListDTO(
            entity.getIdLancamento(),
            entity.getData(),
            entity.getIdEmpresa(),
            entity.getIdCliente(),
            entity.getIdPessoa(),
            entity.getNf(),
            entity.getValorBruto(),
            entity.getRetencao(),
            entity.getValorLiquido(),
            entity.getValorRepasse(),
            entity.getBaixa()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Lancamento toEntity(LancamentoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Lancamento entity = new Lancamento();
        entity.setData(dto.data());
        entity.setIdEmpresa(dto.idEmpresa());
        entity.setIdCliente(dto.idCliente());
        entity.setIdPessoa(dto.idPessoa());
        entity.setNf(dto.nf());
        entity.setValorBruto(dto.valorBruto());
        entity.setDespesas(dto.despesas());
        entity.setRetencao(dto.retencao());
        entity.setValorLiquido(dto.valorLiquido());
        entity.setValorTaxa(dto.valorTaxa());
        entity.setValorRepasse(dto.valorRepasse());
        entity.setBaixa(dto.baixa());
        entity.setMesAno(dto.mesAno());
        entity.setTaxa(dto.taxa());
        entity.setIdTipoServico(dto.idTipoServico());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(LancamentoRequestDTO dto, Lancamento entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setData(dto.data());
        entity.setIdEmpresa(dto.idEmpresa());
        entity.setIdCliente(dto.idCliente());
        entity.setIdPessoa(dto.idPessoa());
        entity.setNf(dto.nf());
        entity.setValorBruto(dto.valorBruto());
        entity.setDespesas(dto.despesas());
        entity.setRetencao(dto.retencao());
        entity.setValorLiquido(dto.valorLiquido());
        entity.setValorTaxa(dto.valorTaxa());
        entity.setValorRepasse(dto.valorRepasse());
        entity.setBaixa(dto.baixa());
        entity.setMesAno(dto.mesAno());
        entity.setTaxa(dto.taxa());
        entity.setIdTipoServico(dto.idTipoServico());
    }
}
