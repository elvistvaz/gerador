package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.NF;
import br.com.xandel.entity.NFId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre NF e DTOs.
 */
@Component
public class NFMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public NFResponseDTO toResponseDTO(NF entity) {
        if (entity == null) {
            return null;
        }
        return new NFResponseDTO(
            entity.getId().getIdEmpresa(),
            entity.getId().getNf(),
            entity.getIdCliente(),
            entity.getEmissao(),
            entity.getVencimento(),
            entity.getTotal(),
            entity.getIrrf(),
            entity.getPis(),
            entity.getCofins(),
            entity.getCsll(),
            entity.getIss(),
            entity.getBaixa(),
            entity.getValorQuitado(),
            entity.getCancelada(),
            entity.getObservacao()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public NFListDTO toListDTO(NF entity) {
        if (entity == null) {
            return null;
        }
        return new NFListDTO(
            entity.getId().getIdEmpresa(),
            entity.getId().getNf(),
            entity.getIdCliente(),
            entity.getEmissao(),
            entity.getVencimento(),
            entity.getTotal(),
            entity.getBaixa(),
            entity.getValorQuitado(),
            entity.getCancelada()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public NF toEntity(NFRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        NF entity = new NF();
        NFId id = new NFId();
        id.setIdEmpresa(dto.idEmpresa());
        id.setNf(dto.nf());
        entity.setId(id);
        entity.setIdCliente(dto.idCliente());
        entity.setEmissao(dto.emissao());
        entity.setVencimento(dto.vencimento());
        entity.setTotal(dto.total());
        entity.setIrrf(dto.irrf());
        entity.setPis(dto.pis());
        entity.setCofins(dto.cofins());
        entity.setCsll(dto.csll());
        entity.setIss(dto.iss());
        entity.setBaixa(dto.baixa());
        entity.setValorQuitado(dto.valorQuitado());
        entity.setCancelada(dto.cancelada());
        entity.setObservacao(dto.observacao());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(NFRequestDTO dto, NF entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setIdCliente(dto.idCliente());
        entity.setEmissao(dto.emissao());
        entity.setVencimento(dto.vencimento());
        entity.setTotal(dto.total());
        entity.setIrrf(dto.irrf());
        entity.setPis(dto.pis());
        entity.setCofins(dto.cofins());
        entity.setCsll(dto.csll());
        entity.setIss(dto.iss());
        entity.setBaixa(dto.baixa());
        entity.setValorQuitado(dto.valorQuitado());
        entity.setCancelada(dto.cancelada());
        entity.setObservacao(dto.observacao());
    }
}
