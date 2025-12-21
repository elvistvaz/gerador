package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ParametroEmail;
import br.com.xandel.mapper.ParametroEmailMapper;
import br.com.xandel.repository.ParametroEmailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para ParametroEmail.
 */
@Service
@Transactional
public class ParametroEmailService {

    private final ParametroEmailRepository repository;
    private final ParametroEmailMapper mapper;
    private final AuditLogService auditLogService;

    public ParametroEmailService(
            ParametroEmailRepository repository,
            ParametroEmailMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ParametroEmailListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ParametroEmailResponseDTO> findById(Integer idParametro) {
        return repository.findById(idParametro)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ParametroEmailResponseDTO create(ParametroEmailRequestDTO dto) {
        ParametroEmail entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "ParametroEmail", entity.getIdParametro(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ParametroEmailResponseDTO> update(Integer idParametro, ParametroEmailRequestDTO dto) {
        return repository.findById(idParametro)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ParametroEmailResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                ParametroEmail saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "ParametroEmail", idParametro, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idParametro) {
        return repository.findById(idParametro)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                ParametroEmailResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "ParametroEmail", idParametro, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idParametro) {
        return repository.existsById(idParametro);
    }
}
