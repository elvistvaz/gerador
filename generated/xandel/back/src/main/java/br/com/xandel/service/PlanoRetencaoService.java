package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PlanoRetencao;
import br.com.xandel.mapper.PlanoRetencaoMapper;
import br.com.xandel.repository.PlanoRetencaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para PlanoRetencao.
 */
@Service
@Transactional
public class PlanoRetencaoService {

    private final PlanoRetencaoRepository repository;
    private final PlanoRetencaoMapper mapper;
    private final AuditLogService auditLogService;

    public PlanoRetencaoService(
            PlanoRetencaoRepository repository,
            PlanoRetencaoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<PlanoRetencaoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoRetencaoResponseDTO> findById(Integer idPlanoRetencao) {
        return repository.findById(idPlanoRetencao)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public PlanoRetencaoResponseDTO create(PlanoRetencaoRequestDTO dto) {
        PlanoRetencao entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "PlanoRetencao", entity.getIdPlanoRetencao(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<PlanoRetencaoResponseDTO> update(Integer idPlanoRetencao, PlanoRetencaoRequestDTO dto) {
        return repository.findById(idPlanoRetencao)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                PlanoRetencaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                PlanoRetencao saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "PlanoRetencao", idPlanoRetencao, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idPlanoRetencao) {
        return repository.findById(idPlanoRetencao)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                PlanoRetencaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "PlanoRetencao", idPlanoRetencao, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idPlanoRetencao) {
        return repository.existsById(idPlanoRetencao);
    }
}
