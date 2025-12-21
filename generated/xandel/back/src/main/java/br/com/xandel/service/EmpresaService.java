package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Empresa;
import br.com.xandel.mapper.EmpresaMapper;
import br.com.xandel.repository.EmpresaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Empresa.
 */
@Service
@Transactional
public class EmpresaService {

    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;
    private final AuditLogService auditLogService;

    public EmpresaService(
            EmpresaRepository repository,
            EmpresaMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaResponseDTO> findById(Integer idEmpresa) {
        return repository.findById(idEmpresa)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public EmpresaResponseDTO create(EmpresaRequestDTO dto) {
        Empresa entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Empresa", entity.getIdEmpresa(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<EmpresaResponseDTO> update(Integer idEmpresa, EmpresaRequestDTO dto) {
        return repository.findById(idEmpresa)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                EmpresaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Empresa saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Empresa", idEmpresa, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idEmpresa) {
        return repository.findById(idEmpresa)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                EmpresaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Empresa", idEmpresa, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idEmpresa) {
        return repository.existsById(idEmpresa);
    }
}
