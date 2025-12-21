package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.EmpresaDespesaFixa;
import br.com.xandel.entity.EmpresaDespesaFixaId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre EmpresaDespesaFixa e DTOs.
 */
@Component
public class EmpresaDespesaFixaMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public EmpresaDespesaFixaResponseDTO toResponseDTO(EmpresaDespesaFixa entity) {
        if (entity == null) {
            return null;
        }
        return new EmpresaDespesaFixaResponseDTO(
            entity.getId().getIdEmpresa(),
            entity.getId().getIdDespesaReceita(),
            entity.getDataLancamento(),
            entity.getParcelas(),
            entity.getValorEmpresa(),
            entity.getInativa()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public EmpresaDespesaFixaListDTO toListDTO(EmpresaDespesaFixa entity) {
        if (entity == null) {
            return null;
        }
        return new EmpresaDespesaFixaListDTO(
            entity.getId().getIdEmpresa(),
            entity.getId().getIdDespesaReceita(),
            entity.getDataLancamento(),
            entity.getParcelas(),
            entity.getValorEmpresa(),
            entity.getInativa()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public EmpresaDespesaFixa toEntity(EmpresaDespesaFixaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        EmpresaDespesaFixa entity = new EmpresaDespesaFixa();
        EmpresaDespesaFixaId id = new EmpresaDespesaFixaId();
        id.setIdEmpresa(dto.idEmpresa());
        id.setIdDespesaReceita(dto.idDespesaReceita());
        entity.setId(id);
        entity.setDataLancamento(dto.dataLancamento());
        entity.setParcelas(dto.parcelas());
        entity.setValorEmpresa(dto.valorEmpresa());
        entity.setInativa(dto.inativa());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(EmpresaDespesaFixaRequestDTO dto, EmpresaDespesaFixa entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setDataLancamento(dto.dataLancamento());
        entity.setParcelas(dto.parcelas());
        entity.setValorEmpresa(dto.valorEmpresa());
        entity.setInativa(dto.inativa());
    }
}
