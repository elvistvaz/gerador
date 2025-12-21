package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.AnuidadeCremebItem;
import br.com.xandel.mapper.AnuidadeCremebItemMapper;
import br.com.xandel.repository.AnuidadeCremebItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para AnuidadeCremebItem.
 */
@Service
@Transactional
public class AnuidadeCremebItemService {

    private final AnuidadeCremebItemRepository repository;
    private final AnuidadeCremebItemMapper mapper;
    private final AuditLogService auditLogService;

    public AnuidadeCremebItemService(
            AnuidadeCremebItemRepository repository,
            AnuidadeCremebItemMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AnuidadeCremebItemListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<AnuidadeCremebItemResponseDTO> findById(Integer idAnuidadeCremebItem) {
        return repository.findById(idAnuidadeCremebItem)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AnuidadeCremebItemResponseDTO create(AnuidadeCremebItemRequestDTO dto) {
        AnuidadeCremebItem entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "AnuidadeCremebItem", entity.getIdAnuidadeCremebItem(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AnuidadeCremebItemResponseDTO> update(Integer idAnuidadeCremebItem, AnuidadeCremebItemRequestDTO dto) {
        return repository.findById(idAnuidadeCremebItem)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                AnuidadeCremebItemResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                AnuidadeCremebItem saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "AnuidadeCremebItem", idAnuidadeCremebItem, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idAnuidadeCremebItem) {
        return repository.findById(idAnuidadeCremebItem)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                AnuidadeCremebItemResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "AnuidadeCremebItem", idAnuidadeCremebItem, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idAnuidadeCremebItem) {
        return repository.existsById(idAnuidadeCremebItem);
    }
}
