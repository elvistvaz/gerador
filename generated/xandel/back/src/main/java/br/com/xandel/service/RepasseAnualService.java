package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.RepasseAnual;
import br.com.xandel.mapper.RepasseAnualMapper;
import br.com.xandel.repository.RepasseAnualRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.RepasseAnualId;

/**
 * Service para RepasseAnual.
 */
@Service
@Transactional
public class RepasseAnualService {

    private final RepasseAnualRepository repository;
    private final RepasseAnualMapper mapper;
    private final AuditLogService auditLogService;

    public RepasseAnualService(
            RepasseAnualRepository repository,
            RepasseAnualMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<RepasseAnualListDTO> findAll(Integer idEmpresa, Pageable pageable) {
        if (idEmpresa != null) {
            return repository.findByIdEmpresa(idEmpresa, pageable)
                .map(mapper::toListDTO);
        }
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<RepasseAnualResponseDTO> findById(RepasseAnualId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public RepasseAnualResponseDTO create(RepasseAnualRequestDTO dto) {
        RepasseAnual entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "RepasseAnual", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<RepasseAnualResponseDTO> update(RepasseAnualId id, RepasseAnualRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                RepasseAnualResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                RepasseAnual saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "RepasseAnual", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(RepasseAnualId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                RepasseAnualResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "RepasseAnual", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(RepasseAnualId id) {
        return repository.existsById(id);
    }
}
