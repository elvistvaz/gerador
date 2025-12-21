package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Usuario;
import br.com.xandel.mapper.UsuarioMapper;
import br.com.xandel.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Usuario.
 */
@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final AuditLogService auditLogService;

    public UsuarioService(
            UsuarioRepository repository,
            UsuarioMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> findById(Integer idUsuario) {
        return repository.findById(idUsuario)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        Usuario entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Usuario", entity.getIdUsuario(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<UsuarioResponseDTO> update(Integer idUsuario, UsuarioRequestDTO dto) {
        return repository.findById(idUsuario)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                UsuarioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Usuario saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Usuario", idUsuario, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idUsuario) {
        return repository.findById(idUsuario)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                UsuarioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Usuario", idUsuario, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idUsuario) {
        return repository.existsById(idUsuario);
    }
}
