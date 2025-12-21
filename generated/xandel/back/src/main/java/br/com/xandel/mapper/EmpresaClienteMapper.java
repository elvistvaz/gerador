package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.EmpresaCliente;
import br.com.xandel.entity.EmpresaClienteId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre EmpresaCliente e DTOs.
 */
@Component
public class EmpresaClienteMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public EmpresaClienteResponseDTO toResponseDTO(EmpresaCliente entity) {
        if (entity == null) {
            return null;
        }
        return new EmpresaClienteResponseDTO(
            entity.getId().getIdEmpresa(),
            entity.getId().getIdCliente(),
            entity.getTaxa(),
            entity.getProcesso(),
            entity.getTaxaISS(),
            entity.getTaxaCOFINS(),
            entity.getTaxaPIS(),
            entity.getTaxaCSLL(),
            entity.getTaxaIRRF()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public EmpresaClienteListDTO toListDTO(EmpresaCliente entity) {
        if (entity == null) {
            return null;
        }
        return new EmpresaClienteListDTO(
            entity.getId().getIdEmpresa(),
            entity.getId().getIdCliente(),
            entity.getTaxa(),
            entity.getProcesso()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public EmpresaCliente toEntity(EmpresaClienteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        EmpresaCliente entity = new EmpresaCliente();
        EmpresaClienteId id = new EmpresaClienteId();
        id.setIdEmpresa(dto.idEmpresa());
        id.setIdCliente(dto.idCliente());
        entity.setId(id);
        entity.setTaxa(dto.taxa());
        entity.setProcesso(dto.processo());
        entity.setTaxaISS(dto.taxaISS());
        entity.setTaxaCOFINS(dto.taxaCOFINS());
        entity.setTaxaPIS(dto.taxaPIS());
        entity.setTaxaCSLL(dto.taxaCSLL());
        entity.setTaxaIRRF(dto.taxaIRRF());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(EmpresaClienteRequestDTO dto, EmpresaCliente entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTaxa(dto.taxa());
        entity.setProcesso(dto.processo());
        entity.setTaxaISS(dto.taxaISS());
        entity.setTaxaCOFINS(dto.taxaCOFINS());
        entity.setTaxaPIS(dto.taxaPIS());
        entity.setTaxaCSLL(dto.taxaCSLL());
        entity.setTaxaIRRF(dto.taxaIRRF());
    }
}
