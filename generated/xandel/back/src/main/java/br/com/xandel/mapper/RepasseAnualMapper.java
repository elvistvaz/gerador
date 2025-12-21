package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.RepasseAnual;
import br.com.xandel.entity.RepasseAnualId;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre RepasseAnual e DTOs.
 */
@Component
public class RepasseAnualMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public RepasseAnualResponseDTO toResponseDTO(RepasseAnual entity) {
        if (entity == null) {
            return null;
        }
        return new RepasseAnualResponseDTO(
            entity.getId().getAno(),
            entity.getId().getIdEmpresa(),
            entity.getId().getIdPessoa(),
            entity.getJanBruto(),
            entity.getJanTaxa(),
            entity.getFevBruto(),
            entity.getFevTaxa(),
            entity.getMarBruto(),
            entity.getMarTaxa(),
            entity.getAbrBruto(),
            entity.getAbrTaxa(),
            entity.getMaiBruto(),
            entity.getMaiTaxa(),
            entity.getJunBruto(),
            entity.getJunTaxa(),
            entity.getJulBruto(),
            entity.getJulTaxa(),
            entity.getAgoBruto(),
            entity.getAgoTaxa(),
            entity.getSetBruto(),
            entity.getSetTaxa(),
            entity.getOutBruto(),
            entity.getOutTaxa(),
            entity.getNovBruto(),
            entity.getNovTaxa(),
            entity.getDezBruto(),
            entity.getDezTaxa()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public RepasseAnualListDTO toListDTO(RepasseAnual entity) {
        if (entity == null) {
            return null;
        }
        return new RepasseAnualListDTO(
            entity.getId().getAno(),
            entity.getId().getIdEmpresa(),
            entity.getId().getIdPessoa(),
            entity.getJanBruto(),
            entity.getFevBruto(),
            entity.getMarBruto(),
            entity.getAbrBruto(),
            entity.getMaiBruto(),
            entity.getJunBruto()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public RepasseAnual toEntity(RepasseAnualRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        RepasseAnual entity = new RepasseAnual();
        RepasseAnualId id = new RepasseAnualId();
        id.setAno(dto.ano());
        id.setIdEmpresa(dto.idEmpresa());
        id.setIdPessoa(dto.idPessoa());
        entity.setId(id);
        entity.setJanBruto(dto.janBruto());
        entity.setJanTaxa(dto.janTaxa());
        entity.setFevBruto(dto.fevBruto());
        entity.setFevTaxa(dto.fevTaxa());
        entity.setMarBruto(dto.marBruto());
        entity.setMarTaxa(dto.marTaxa());
        entity.setAbrBruto(dto.abrBruto());
        entity.setAbrTaxa(dto.abrTaxa());
        entity.setMaiBruto(dto.maiBruto());
        entity.setMaiTaxa(dto.maiTaxa());
        entity.setJunBruto(dto.junBruto());
        entity.setJunTaxa(dto.junTaxa());
        entity.setJulBruto(dto.julBruto());
        entity.setJulTaxa(dto.julTaxa());
        entity.setAgoBruto(dto.agoBruto());
        entity.setAgoTaxa(dto.agoTaxa());
        entity.setSetBruto(dto.setBruto());
        entity.setSetTaxa(dto.setTaxa());
        entity.setOutBruto(dto.outBruto());
        entity.setOutTaxa(dto.outTaxa());
        entity.setNovBruto(dto.novBruto());
        entity.setNovTaxa(dto.novTaxa());
        entity.setDezBruto(dto.dezBruto());
        entity.setDezTaxa(dto.dezTaxa());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(RepasseAnualRequestDTO dto, RepasseAnual entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setJanBruto(dto.janBruto());
        entity.setJanTaxa(dto.janTaxa());
        entity.setFevBruto(dto.fevBruto());
        entity.setFevTaxa(dto.fevTaxa());
        entity.setMarBruto(dto.marBruto());
        entity.setMarTaxa(dto.marTaxa());
        entity.setAbrBruto(dto.abrBruto());
        entity.setAbrTaxa(dto.abrTaxa());
        entity.setMaiBruto(dto.maiBruto());
        entity.setMaiTaxa(dto.maiTaxa());
        entity.setJunBruto(dto.junBruto());
        entity.setJunTaxa(dto.junTaxa());
        entity.setJulBruto(dto.julBruto());
        entity.setJulTaxa(dto.julTaxa());
        entity.setAgoBruto(dto.agoBruto());
        entity.setAgoTaxa(dto.agoTaxa());
        entity.setSetBruto(dto.setBruto());
        entity.setSetTaxa(dto.setTaxa());
        entity.setOutBruto(dto.outBruto());
        entity.setOutTaxa(dto.outTaxa());
        entity.setNovBruto(dto.novBruto());
        entity.setNovTaxa(dto.novTaxa());
        entity.setDezBruto(dto.dezBruto());
        entity.setDezTaxa(dto.dezTaxa());
    }
}
