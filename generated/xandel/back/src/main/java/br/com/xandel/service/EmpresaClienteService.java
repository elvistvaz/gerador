package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.EmpresaCliente;
import br.com.xandel.mapper.EmpresaClienteMapper;
import br.com.xandel.repository.EmpresaClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.EmpresaClienteId;

/**
 * Service para EmpresaCliente.
 */
@Service
@Transactional
public class EmpresaClienteService {

    private final EmpresaClienteRepository repository;
    private final EmpresaClienteMapper mapper;
    private final AuditLogService auditLogService;

    public EmpresaClienteService(
            EmpresaClienteRepository repository,
            EmpresaClienteMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaClienteListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaClienteResponseDTO> findById(EmpresaClienteId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public EmpresaClienteResponseDTO create(EmpresaClienteRequestDTO dto) {
        EmpresaCliente entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "EmpresaCliente", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<EmpresaClienteResponseDTO> update(EmpresaClienteId id, EmpresaClienteRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                EmpresaClienteResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                EmpresaCliente saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "EmpresaCliente", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(EmpresaClienteId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                EmpresaClienteResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "EmpresaCliente", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(EmpresaClienteId id) {
        return repository.existsById(id);
    }
}
