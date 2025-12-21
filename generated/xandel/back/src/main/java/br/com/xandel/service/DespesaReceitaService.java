package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.DespesaReceita;
import br.com.xandel.mapper.DespesaReceitaMapper;
import br.com.xandel.repository.DespesaReceitaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para DespesaReceita.
 */
@Service
@Transactional
public class DespesaReceitaService {

    private final DespesaReceitaRepository repository;
    private final DespesaReceitaMapper mapper;
    private final AuditLogService auditLogService;

    public DespesaReceitaService(
            DespesaReceitaRepository repository,
            DespesaReceitaMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<DespesaReceitaListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<DespesaReceitaResponseDTO> findById(Integer idDespesaReceita) {
        return repository.findById(idDespesaReceita)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public DespesaReceitaResponseDTO create(DespesaReceitaRequestDTO dto) {
        DespesaReceita entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "DespesaReceita", entity.getIdDespesaReceita(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<DespesaReceitaResponseDTO> update(Integer idDespesaReceita, DespesaReceitaRequestDTO dto) {
        return repository.findById(idDespesaReceita)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                DespesaReceitaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                DespesaReceita saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "DespesaReceita", idDespesaReceita, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idDespesaReceita) {
        return repository.findById(idDespesaReceita)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                DespesaReceitaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "DespesaReceita", idDespesaReceita, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idDespesaReceita) {
        return repository.existsById(idDespesaReceita);
    }
}
