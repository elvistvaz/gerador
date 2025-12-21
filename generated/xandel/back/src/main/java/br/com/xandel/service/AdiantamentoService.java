package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Adiantamento;
import br.com.xandel.mapper.AdiantamentoMapper;
import br.com.xandel.repository.AdiantamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Adiantamento.
 */
@Service
@Transactional
public class AdiantamentoService {

    private final AdiantamentoRepository repository;
    private final AdiantamentoMapper mapper;
    private final AuditLogService auditLogService;

    public AdiantamentoService(
            AdiantamentoRepository repository,
            AdiantamentoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AdiantamentoListDTO> findAll(Integer idEmpresa, Pageable pageable) {
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
    public Optional<AdiantamentoResponseDTO> findById(Integer idAdiantamento) {
        return repository.findById(idAdiantamento)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AdiantamentoResponseDTO create(AdiantamentoRequestDTO dto) {
        Adiantamento entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Adiantamento", entity.getIdAdiantamento(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AdiantamentoResponseDTO> update(Integer idAdiantamento, AdiantamentoRequestDTO dto) {
        return repository.findById(idAdiantamento)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                AdiantamentoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Adiantamento saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Adiantamento", idAdiantamento, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idAdiantamento) {
        return repository.findById(idAdiantamento)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                AdiantamentoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Adiantamento", idAdiantamento, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idAdiantamento) {
        return repository.existsById(idAdiantamento);
    }
}
