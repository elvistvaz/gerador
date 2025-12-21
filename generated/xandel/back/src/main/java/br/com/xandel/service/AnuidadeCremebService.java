package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.AnuidadeCremeb;
import br.com.xandel.mapper.AnuidadeCremebMapper;
import br.com.xandel.repository.AnuidadeCremebRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para AnuidadeCremeb.
 */
@Service
@Transactional
public class AnuidadeCremebService {

    private final AnuidadeCremebRepository repository;
    private final AnuidadeCremebMapper mapper;
    private final AuditLogService auditLogService;

    public AnuidadeCremebService(
            AnuidadeCremebRepository repository,
            AnuidadeCremebMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AnuidadeCremebListDTO> findAll(Integer idEmpresa, Pageable pageable) {
        if (idEmpresa != null) {
            return repository.findByIdEmpresa(idEmpresa, pageable)
                .map(mapper::toListDTO);
        }
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<AnuidadeCremebResponseDTO> findById(Integer idAnuidadeCremeb) {
        return repository.findById(idAnuidadeCremeb)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AnuidadeCremebResponseDTO create(AnuidadeCremebRequestDTO dto) {
        AnuidadeCremeb entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "AnuidadeCremeb", entity.getIdAnuidadeCremeb(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AnuidadeCremebResponseDTO> update(Integer idAnuidadeCremeb, AnuidadeCremebRequestDTO dto) {
        return repository.findById(idAnuidadeCremeb)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                AnuidadeCremebResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                AnuidadeCremeb saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "AnuidadeCremeb", idAnuidadeCremeb, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idAnuidadeCremeb) {
        return repository.findById(idAnuidadeCremeb)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                AnuidadeCremebResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "AnuidadeCremeb", idAnuidadeCremeb, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idAnuidadeCremeb) {
        return repository.existsById(idAnuidadeCremeb);
    }
}
