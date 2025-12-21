package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.CBO;
import br.com.xandel.mapper.CBOMapper;
import br.com.xandel.repository.CBORepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para CBO.
 */
@Service
@Transactional
public class CBOService {

    private final CBORepository repository;
    private final CBOMapper mapper;
    private final AuditLogService auditLogService;

    public CBOService(
            CBORepository repository,
            CBOMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<CBOListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<CBOResponseDTO> findById(String idCBO) {
        return repository.findById(idCBO)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public CBOResponseDTO create(CBORequestDTO dto) {
        CBO entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "CBO", entity.getIdCBO(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<CBOResponseDTO> update(String idCBO, CBORequestDTO dto) {
        return repository.findById(idCBO)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                CBOResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                CBO saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "CBO", idCBO, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(String idCBO) {
        return repository.findById(idCBO)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                CBOResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "CBO", idCBO, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(String idCBO) {
        return repository.existsById(idCBO);
    }
}
