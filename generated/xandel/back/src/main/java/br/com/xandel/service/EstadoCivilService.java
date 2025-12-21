package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.EstadoCivil;
import br.com.xandel.mapper.EstadoCivilMapper;
import br.com.xandel.repository.EstadoCivilRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para EstadoCivil.
 */
@Service
@Transactional
public class EstadoCivilService {

    private final EstadoCivilRepository repository;
    private final EstadoCivilMapper mapper;
    private final AuditLogService auditLogService;

    public EstadoCivilService(
            EstadoCivilRepository repository,
            EstadoCivilMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<EstadoCivilListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<EstadoCivilResponseDTO> findById(Integer idEstadoCivil) {
        return repository.findById(idEstadoCivil)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public EstadoCivilResponseDTO create(EstadoCivilRequestDTO dto) {
        EstadoCivil entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "EstadoCivil", entity.getIdEstadoCivil(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<EstadoCivilResponseDTO> update(Integer idEstadoCivil, EstadoCivilRequestDTO dto) {
        return repository.findById(idEstadoCivil)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                EstadoCivilResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                EstadoCivil saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "EstadoCivil", idEstadoCivil, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idEstadoCivil) {
        return repository.findById(idEstadoCivil)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                EstadoCivilResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "EstadoCivil", idEstadoCivil, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idEstadoCivil) {
        return repository.existsById(idEstadoCivil);
    }
}
