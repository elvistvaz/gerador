package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.PublicoAlvo;
import br.com.icep.mapper.PublicoAlvoMapper;
import br.com.icep.repository.PublicoAlvoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para PublicoAlvo.
 */
@Service
@Transactional
public class PublicoAlvoService {

    private final PublicoAlvoRepository repository;
    private final PublicoAlvoMapper mapper;
    private final AuditLogService auditLogService;

    public PublicoAlvoService(
            PublicoAlvoRepository repository,
            PublicoAlvoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<PublicoAlvoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<PublicoAlvoResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public PublicoAlvoResponseDTO create(PublicoAlvoRequestDTO dto) {
        PublicoAlvo entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "PublicoAlvo", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<PublicoAlvoResponseDTO> update(Integer id, PublicoAlvoRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                PublicoAlvoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                PublicoAlvo saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "PublicoAlvo", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                PublicoAlvoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "PublicoAlvo", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
