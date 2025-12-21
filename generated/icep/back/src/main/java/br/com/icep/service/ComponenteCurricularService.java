package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.ComponenteCurricular;
import br.com.icep.mapper.ComponenteCurricularMapper;
import br.com.icep.repository.ComponenteCurricularRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para ComponenteCurricular.
 */
@Service
@Transactional
public class ComponenteCurricularService {

    private final ComponenteCurricularRepository repository;
    private final ComponenteCurricularMapper mapper;
    private final AuditLogService auditLogService;

    public ComponenteCurricularService(
            ComponenteCurricularRepository repository,
            ComponenteCurricularMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ComponenteCurricularListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ComponenteCurricularResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ComponenteCurricularResponseDTO create(ComponenteCurricularRequestDTO dto) {
        ComponenteCurricular entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "ComponenteCurricular", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ComponenteCurricularResponseDTO> update(Integer id, ComponenteCurricularRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ComponenteCurricularResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                ComponenteCurricular saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "ComponenteCurricular", id, estadoAnterior);
                
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
                ComponenteCurricularResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "ComponenteCurricular", id, estadoAnterior);
                
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
