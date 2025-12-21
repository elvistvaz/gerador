package br.com.xandel.mapper;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Usuario;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Usuario e DTOs.
 */
@Component
public class UsuarioMapper {

    /**
     * Converte Entity para ResponseDTO.
     */
    public UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }
        return new UsuarioResponseDTO(
            entity.getIdUsuario(),
            entity.getUsuarioNome(),
            entity.getUsuarioLogin(),
            entity.getUsuarioSenha(),
            entity.getUsuarioNivelAcesso(),
            entity.getUsuarioInativo(),
            entity.getUsuarioDataInicio()
        );
    }

    /**
     * Converte Entity para ListDTO.
     */
    public UsuarioListDTO toListDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }
        return new UsuarioListDTO(
            entity.getIdUsuario(),
            entity.getUsuarioNome(),
            entity.getUsuarioLogin(),
            entity.getUsuarioNivelAcesso(),
            entity.getUsuarioInativo(),
            entity.getUsuarioDataInicio()
        );
    }

    /**
     * Converte RequestDTO para Entity.
     */
    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario entity = new Usuario();
        entity.setIdUsuario(dto.idUsuario());
        entity.setUsuarioNome(dto.usuarioNome());
        entity.setUsuarioLogin(dto.usuarioLogin());
        entity.setUsuarioSenha(dto.usuarioSenha());
        entity.setUsuarioNivelAcesso(dto.usuarioNivelAcesso());
        entity.setUsuarioInativo(dto.usuarioInativo());
        entity.setUsuarioDataInicio(dto.usuarioDataInicio());
        return entity;
    }

    /**
     * Atualiza Entity a partir de RequestDTO.
     */
    public void updateEntity(UsuarioRequestDTO dto, Usuario entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setUsuarioNome(dto.usuarioNome());
        entity.setUsuarioLogin(dto.usuarioLogin());
        entity.setUsuarioSenha(dto.usuarioSenha());
        entity.setUsuarioNivelAcesso(dto.usuarioNivelAcesso());
        entity.setUsuarioInativo(dto.usuarioInativo());
        entity.setUsuarioDataInicio(dto.usuarioDataInicio());
    }
}
