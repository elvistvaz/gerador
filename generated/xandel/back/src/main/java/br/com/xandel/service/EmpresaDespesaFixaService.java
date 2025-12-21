package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.EmpresaDespesaFixa;
import br.com.xandel.mapper.EmpresaDespesaFixaMapper;
import br.com.xandel.repository.EmpresaDespesaFixaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.EmpresaDespesaFixaId;

/**
 * Service para EmpresaDespesaFixa.
 */
@Service
@Transactional
public class EmpresaDespesaFixaService {

    private final EmpresaDespesaFixaRepository repository;
    private final EmpresaDespesaFixaMapper mapper;
    private final AuditLogService auditLogService;

    public EmpresaDespesaFixaService(
            EmpresaDespesaFixaRepository repository,
            EmpresaDespesaFixaMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaDespesaFixaListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaDespesaFixaResponseDTO> findById(EmpresaDespesaFixaId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public EmpresaDespesaFixaResponseDTO create(EmpresaDespesaFixaRequestDTO dto) {
        EmpresaDespesaFixa entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "EmpresaDespesaFixa", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<EmpresaDespesaFixaResponseDTO> update(EmpresaDespesaFixaId id, EmpresaDespesaFixaRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                EmpresaDespesaFixaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                EmpresaDespesaFixa saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "EmpresaDespesaFixa", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(EmpresaDespesaFixaId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                EmpresaDespesaFixaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "EmpresaDespesaFixa", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(EmpresaDespesaFixaId id) {
        return repository.existsById(id);
    }
}
