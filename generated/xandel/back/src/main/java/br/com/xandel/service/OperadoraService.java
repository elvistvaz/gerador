package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Operadora;
import br.com.xandel.mapper.OperadoraMapper;
import br.com.xandel.repository.OperadoraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Operadora.
 */
@Service
@Transactional
public class OperadoraService {

    private final OperadoraRepository repository;
    private final OperadoraMapper mapper;
    private final AuditLogService auditLogService;

    public OperadoraService(
            OperadoraRepository repository,
            OperadoraMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<OperadoraListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<OperadoraResponseDTO> findById(Integer idOperadora) {
        return repository.findById(idOperadora)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public OperadoraResponseDTO create(OperadoraRequestDTO dto) {
        Operadora entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Operadora", entity.getIdOperadora(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<OperadoraResponseDTO> update(Integer idOperadora, OperadoraRequestDTO dto) {
        return repository.findById(idOperadora)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                OperadoraResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Operadora saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Operadora", idOperadora, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idOperadora) {
        return repository.findById(idOperadora)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                OperadoraResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Operadora", idOperadora, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idOperadora) {
        return repository.existsById(idOperadora);
    }
}
