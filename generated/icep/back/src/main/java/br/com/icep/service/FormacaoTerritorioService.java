package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.FormacaoTerritorio;
import br.com.icep.mapper.FormacaoTerritorioMapper;
import br.com.icep.repository.FormacaoTerritorioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para FormacaoTerritorio.
 */
@Service
@Transactional
public class FormacaoTerritorioService {

    private final FormacaoTerritorioRepository repository;
    private final FormacaoTerritorioMapper mapper;
    private final AuditLogService auditLogService;

    public FormacaoTerritorioService(
            FormacaoTerritorioRepository repository,
            FormacaoTerritorioMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<FormacaoTerritorioListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<FormacaoTerritorioResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public FormacaoTerritorioResponseDTO create(FormacaoTerritorioRequestDTO dto) {
        FormacaoTerritorio entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "FormacaoTerritorio", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<FormacaoTerritorioResponseDTO> update(Integer id, FormacaoTerritorioRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                FormacaoTerritorioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                FormacaoTerritorio saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "FormacaoTerritorio", id, estadoAnterior);
                
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
                FormacaoTerritorioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "FormacaoTerritorio", id, estadoAnterior);
                
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
