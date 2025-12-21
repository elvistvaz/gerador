package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Bairro;
import br.com.xandel.mapper.BairroMapper;
import br.com.xandel.repository.BairroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Bairro.
 */
@Service
@Transactional
public class BairroService {

    private final BairroRepository repository;
    private final BairroMapper mapper;
    private final AuditLogService auditLogService;

    public BairroService(
            BairroRepository repository,
            BairroMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<BairroListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<BairroResponseDTO> findById(Integer idBairro) {
        return repository.findById(idBairro)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public BairroResponseDTO create(BairroRequestDTO dto) {
        Bairro entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Bairro", entity.getIdBairro(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<BairroResponseDTO> update(Integer idBairro, BairroRequestDTO dto) {
        return repository.findById(idBairro)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                BairroResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Bairro saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Bairro", idBairro, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idBairro) {
        return repository.findById(idBairro)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                BairroResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Bairro", idBairro, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idBairro) {
        return repository.existsById(idBairro);
    }
}
