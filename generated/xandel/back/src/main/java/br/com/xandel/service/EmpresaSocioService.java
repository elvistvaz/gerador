package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.EmpresaSocio;
import br.com.xandel.mapper.EmpresaSocioMapper;
import br.com.xandel.repository.EmpresaSocioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.EmpresaSocioId;

/**
 * Service para EmpresaSocio.
 */
@Service
@Transactional
public class EmpresaSocioService {

    private final EmpresaSocioRepository repository;
    private final EmpresaSocioMapper mapper;
    private final AuditLogService auditLogService;

    public EmpresaSocioService(
            EmpresaSocioRepository repository,
            EmpresaSocioMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaSocioListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaSocioResponseDTO> findById(EmpresaSocioId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public EmpresaSocioResponseDTO create(EmpresaSocioRequestDTO dto) {
        EmpresaSocio entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "EmpresaSocio", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<EmpresaSocioResponseDTO> update(EmpresaSocioId id, EmpresaSocioRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                EmpresaSocioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                EmpresaSocio saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "EmpresaSocio", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(EmpresaSocioId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                EmpresaSocioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "EmpresaSocio", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(EmpresaSocioId id) {
        return repository.existsById(id);
    }
}
