package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Conselho;
import br.com.xandel.mapper.ConselhoMapper;
import br.com.xandel.repository.ConselhoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Conselho.
 */
@Service
@Transactional
public class ConselhoService {

    private final ConselhoRepository repository;
    private final ConselhoMapper mapper;
    private final AuditLogService auditLogService;

    public ConselhoService(
            ConselhoRepository repository,
            ConselhoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ConselhoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ConselhoResponseDTO> findById(Integer idConselho) {
        return repository.findById(idConselho)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ConselhoResponseDTO create(ConselhoRequestDTO dto) {
        Conselho entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Conselho", entity.getIdConselho(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ConselhoResponseDTO> update(Integer idConselho, ConselhoRequestDTO dto) {
        return repository.findById(idConselho)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ConselhoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Conselho saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Conselho", idConselho, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idConselho) {
        return repository.findById(idConselho)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                ConselhoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Conselho", idConselho, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idConselho) {
        return repository.existsById(idConselho);
    }
}
