package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Cartorio;
import br.com.xandel.mapper.CartorioMapper;
import br.com.xandel.repository.CartorioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Cartorio.
 */
@Service
@Transactional
public class CartorioService {

    private final CartorioRepository repository;
    private final CartorioMapper mapper;
    private final AuditLogService auditLogService;

    public CartorioService(
            CartorioRepository repository,
            CartorioMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<CartorioListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<CartorioResponseDTO> findById(Integer idCartorio) {
        return repository.findById(idCartorio)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public CartorioResponseDTO create(CartorioRequestDTO dto) {
        Cartorio entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Cartorio", entity.getIdCartorio(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<CartorioResponseDTO> update(Integer idCartorio, CartorioRequestDTO dto) {
        return repository.findById(idCartorio)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                CartorioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Cartorio saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Cartorio", idCartorio, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idCartorio) {
        return repository.findById(idCartorio)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                CartorioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Cartorio", idCartorio, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idCartorio) {
        return repository.existsById(idCartorio);
    }
}
