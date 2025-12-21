package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Retencao;
import br.com.xandel.mapper.RetencaoMapper;
import br.com.xandel.repository.RetencaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.RetencaoId;

/**
 * Service para Retencao.
 */
@Service
@Transactional
public class RetencaoService {

    private final RetencaoRepository repository;
    private final RetencaoMapper mapper;
    private final AuditLogService auditLogService;

    public RetencaoService(
            RetencaoRepository repository,
            RetencaoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<RetencaoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<RetencaoResponseDTO> findById(RetencaoId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public RetencaoResponseDTO create(RetencaoRequestDTO dto) {
        Retencao entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Retencao", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<RetencaoResponseDTO> update(RetencaoId id, RetencaoRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                RetencaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Retencao saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Retencao", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(RetencaoId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                RetencaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Retencao", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(RetencaoId id) {
        return repository.existsById(id);
    }
}
