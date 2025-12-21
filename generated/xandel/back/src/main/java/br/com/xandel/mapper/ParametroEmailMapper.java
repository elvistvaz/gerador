package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ParametroEmail;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre ParametroEmail e DTOs.
 */
@Component
public class ParametroEmailMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public ParametroEmailResponseDTO toResponseDTO(ParametroEmail entity) {
        if (entity == null) {
            return null;
        }
        return new ParametroEmailResponseDTO(
            entity.getIdParametro(),
            entity.getAssuntoEmail(),
            entity.getSmtp(),
            entity.getContaEmail(),
            entity.getSenhaEmail()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public ParametroEmailListDTO toListDTO(ParametroEmail entity) {
        if (entity == null) {
            return null;
        }
        return new ParametroEmailListDTO(
            entity.getIdParametro(),
            entity.getAssuntoEmail(),
            entity.getSmtp(),
            entity.getContaEmail()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public ParametroEmail toEntity(ParametroEmailRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ParametroEmail entity = new ParametroEmail();
        entity.setIdParametro(dto.idParametro());
        entity.setAssuntoEmail(dto.assuntoEmail());
        entity.setSmtp(dto.smtp());
        entity.setContaEmail(dto.contaEmail());
        entity.setSenhaEmail(dto.senhaEmail());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(ParametroEmailRequestDTO dto, ParametroEmail entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAssuntoEmail(dto.assuntoEmail());
        entity.setSmtp(dto.smtp());
        entity.setContaEmail(dto.contaEmail());
        entity.setSenhaEmail(dto.senhaEmail());
    }
}
