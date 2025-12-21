package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Indicacao;
import br.com.xandel.mapper.IndicacaoMapper;
import br.com.xandel.repository.IndicacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Indicacao.
 */
@Service
@Transactional
public class IndicacaoService {

    private final IndicacaoRepository repository;
    private final IndicacaoMapper mapper;
    private final AuditLogService auditLogService;

    public IndicacaoService(
            IndicacaoRepository repository,
            IndicacaoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<IndicacaoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<IndicacaoResponseDTO> findById(Integer idIndicacao) {
        return repository.findById(idIndicacao)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public IndicacaoResponseDTO create(IndicacaoRequestDTO dto) {
        Indicacao entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Indicacao", entity.getIdIndicacao(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<IndicacaoResponseDTO> update(Integer idIndicacao, IndicacaoRequestDTO dto) {
        return repository.findById(idIndicacao)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                IndicacaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Indicacao saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Indicacao", idIndicacao, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idIndicacao) {
        return repository.findById(idIndicacao)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                IndicacaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Indicacao", idIndicacao, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idIndicacao) {
        return repository.existsById(idIndicacao);
    }
}
