package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ContasPagarReceber;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ContasPagarReceber e DTOs.
 */
@Component
public class ContasPagarReceberMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ContasPagarReceberResponseDTO toResponseDTO(ContasPagarReceber entity) {
        if (entity == null) {
            return null;
        }
        return new ContasPagarReceberResponseDTO(
            entity.getIdContasPagarReceber(),
            entity.getDataLancamento(),
            entity.getValorOriginal(),
            entity.getValorParcela(),
            entity.getDataVencimento(),
            entity.getDataBaixa(),
            entity.getValorBaixado(),
            entity.getIdEmpresa(),
            entity.getIdPessoa(),
            entity.getMesAnoReferencia(),
            entity.getHistorico(),
            entity.getIdDespesaReceita()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ContasPagarReceberListDTO toListDTO(ContasPagarReceber entity) {
        if (entity == null) {
            return null;
        }
        return new ContasPagarReceberListDTO(
            entity.getIdContasPagarReceber(),
            entity.getDataLancamento(),
            entity.getValorOriginal(),
            entity.getValorParcela(),
            entity.getDataVencimento(),
            entity.getDataBaixa(),
            entity.getValorBaixado(),
            entity.getIdEmpresa(),
            entity.getHistorico(),
            entity.getIdDespesaReceita()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ContasPagarReceber toEntity(ContasPagarReceberRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ContasPagarReceber entity = new ContasPagarReceber();
        entity.setDataLancamento(dto.dataLancamento());
        entity.setValorOriginal(dto.valorOriginal());
        entity.setValorParcela(dto.valorParcela());
        entity.setDataVencimento(dto.dataVencimento());
        entity.setDataBaixa(dto.dataBaixa());
        entity.setValorBaixado(dto.valorBaixado());
        entity.setIdEmpresa(dto.idEmpresa());
        entity.setIdPessoa(dto.idPessoa());
        entity.setMesAnoReferencia(dto.mesAnoReferencia());
        entity.setHistorico(dto.historico());
        entity.setIdDespesaReceita(dto.idDespesaReceita());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ContasPagarReceberRequestDTO dto, ContasPagarReceber entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setDataLancamento(dto.dataLancamento());
        entity.setValorOriginal(dto.valorOriginal());
        entity.setValorParcela(dto.valorParcela());
        entity.setDataVencimento(dto.dataVencimento());
        entity.setDataBaixa(dto.dataBaixa());
        entity.setValorBaixado(dto.valorBaixado());
        entity.setIdEmpresa(dto.idEmpresa());
        entity.setIdPessoa(dto.idPessoa());
        entity.setMesAnoReferencia(dto.mesAnoReferencia());
        entity.setHistorico(dto.historico());
        entity.setIdDespesaReceita(dto.idDespesaReceita());
    }
}
