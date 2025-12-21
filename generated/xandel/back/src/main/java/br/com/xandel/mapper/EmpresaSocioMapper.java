package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.EmpresaSocio;
import br.com.xandel.entity.EmpresaSocioId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre EmpresaSocio e DTOs.
 */
@Component
public class EmpresaSocioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public EmpresaSocioResponseDTO toResponseDTO(EmpresaSocio entity) {
        if (entity == null) {
            return null;
        }
        return new EmpresaSocioResponseDTO(
            entity.getId().getIdEmpresa(),
            entity.getId().getIdPessoa(),
            entity.getDataAdesao(),
            entity.getNumeroQuotas(),
            entity.getValorQuota(),
            entity.getDataSaida(),
            entity.getValorCremeb(),
            entity.getNovoModelo(),
            entity.getTaxaConsulta(),
            entity.getTaxaProcedimento()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public EmpresaSocioListDTO toListDTO(EmpresaSocio entity) {
        if (entity == null) {
            return null;
        }
        return new EmpresaSocioListDTO(
            entity.getId().getIdEmpresa(),
            entity.getId().getIdPessoa(),
            entity.getDataAdesao(),
            entity.getNumeroQuotas(),
            entity.getValorQuota(),
            entity.getDataSaida(),
            entity.getValorCremeb(),
            entity.getNovoModelo(),
            entity.getTaxaConsulta(),
            entity.getTaxaProcedimento()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public EmpresaSocio toEntity(EmpresaSocioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        EmpresaSocio entity = new EmpresaSocio();
        EmpresaSocioId id = new EmpresaSocioId();
        id.setIdEmpresa(dto.idEmpresa());
        id.setIdPessoa(dto.idPessoa());
        entity.setId(id);
        entity.setDataAdesao(dto.dataAdesao());
        entity.setNumeroQuotas(dto.numeroQuotas());
        entity.setValorQuota(dto.valorQuota());
        entity.setDataSaida(dto.dataSaida());
        entity.setValorCremeb(dto.valorCremeb());
        entity.setNovoModelo(dto.novoModelo());
        entity.setTaxaConsulta(dto.taxaConsulta());
        entity.setTaxaProcedimento(dto.taxaProcedimento());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(EmpresaSocioRequestDTO dto, EmpresaSocio entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setDataAdesao(dto.dataAdesao());
        entity.setNumeroQuotas(dto.numeroQuotas());
        entity.setValorQuota(dto.valorQuota());
        entity.setDataSaida(dto.dataSaida());
        entity.setValorCremeb(dto.valorCremeb());
        entity.setNovoModelo(dto.novoModelo());
        entity.setTaxaConsulta(dto.taxaConsulta());
        entity.setTaxaProcedimento(dto.taxaProcedimento());
    }
}
