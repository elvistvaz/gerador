package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ImpostoDeRenda;
import br.com.xandel.mapper.ImpostoDeRendaMapper;
import br.com.xandel.repository.ImpostoDeRendaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.ImpostoDeRendaId;

/**
 * Service para ImpostoDeRenda.
 */
@Service
@Transactional
public class ImpostoDeRendaService {

    private final ImpostoDeRendaRepository repository;
    private final ImpostoDeRendaMapper mapper;
    private final AuditLogService auditLogService;

    public ImpostoDeRendaService(
            ImpostoDeRendaRepository repository,
            ImpostoDeRendaMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ImpostoDeRendaListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ImpostoDeRendaResponseDTO> findById(ImpostoDeRendaId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ImpostoDeRendaResponseDTO create(ImpostoDeRendaRequestDTO dto) {
        ImpostoDeRenda entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "ImpostoDeRenda", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ImpostoDeRendaResponseDTO> update(ImpostoDeRendaId id, ImpostoDeRendaRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ImpostoDeRendaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                ImpostoDeRenda saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "ImpostoDeRenda", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(ImpostoDeRendaId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                ImpostoDeRendaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "ImpostoDeRenda", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(ImpostoDeRendaId id) {
        return repository.existsById(id);
    }
}
