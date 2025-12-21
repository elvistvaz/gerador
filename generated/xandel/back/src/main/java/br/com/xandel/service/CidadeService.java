package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Cidade;
import br.com.xandel.mapper.CidadeMapper;
import br.com.xandel.repository.CidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Cidade.
 */
@Service
@Transactional
public class CidadeService {

    private final CidadeRepository repository;
    private final CidadeMapper mapper;
    private final AuditLogService auditLogService;

    public CidadeService(
            CidadeRepository repository,
            CidadeMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<CidadeListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<CidadeResponseDTO> findById(Integer idCidade) {
        return repository.findById(idCidade)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public CidadeResponseDTO create(CidadeRequestDTO dto) {
        Cidade entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Cidade", entity.getIdCidade(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<CidadeResponseDTO> update(Integer idCidade, CidadeRequestDTO dto) {
        return repository.findById(idCidade)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                CidadeResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Cidade saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Cidade", idCidade, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idCidade) {
        return repository.findById(idCidade)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                CidadeResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Cidade", idCidade, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idCidade) {
        return repository.existsById(idCidade);
    }
}
