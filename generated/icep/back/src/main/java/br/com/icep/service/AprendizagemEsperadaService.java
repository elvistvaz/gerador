package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.AprendizagemEsperada;
import br.com.icep.mapper.AprendizagemEsperadaMapper;
import br.com.icep.repository.AprendizagemEsperadaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para AprendizagemEsperada.
 */
@Service
@Transactional
public class AprendizagemEsperadaService {

    private final AprendizagemEsperadaRepository repository;
    private final AprendizagemEsperadaMapper mapper;
    private final AuditLogService auditLogService;

    public AprendizagemEsperadaService(
            AprendizagemEsperadaRepository repository,
            AprendizagemEsperadaMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AprendizagemEsperadaListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<AprendizagemEsperadaResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AprendizagemEsperadaResponseDTO create(AprendizagemEsperadaRequestDTO dto) {
        AprendizagemEsperada entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "AprendizagemEsperada", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AprendizagemEsperadaResponseDTO> update(Integer id, AprendizagemEsperadaRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                AprendizagemEsperadaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                AprendizagemEsperada saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "AprendizagemEsperada", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                AprendizagemEsperadaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "AprendizagemEsperada", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
