package br.com.icep.mapper;

import br.com.icep.dto.*;
import br.com.icep.entity.AnoEscolar;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre AnoEscolar e DTOs.
 */
@Component
public class AnoEscolarMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public AnoEscolarResponseDTO toResponseDTO(AnoEscolar entity) {
        if (entity == null) {
            return null;
        }
        return new AnoEscolarResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getEtapa(),
            entity.getOrdem()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public AnoEscolarListDTO toListDTO(AnoEscolar entity) {
        if (entity == null) {
            return null;
        }
        return new AnoEscolarListDTO(
            entity.getId(),
            entity.getNome(),
            entity.getEtapa(),
            entity.getOrdem()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public AnoEscolar toEntity(AnoEscolarRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AnoEscolar entity = new AnoEscolar();
        entity.setNome(dto.nome());
        entity.setEtapa(dto.etapa());
        entity.setOrdem(dto.ordem());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(AnoEscolarRequestDTO dto, AnoEscolar entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNome(dto.nome());
        entity.setEtapa(dto.etapa());
        entity.setOrdem(dto.ordem());
    }
}
