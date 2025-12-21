package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Lancamento;
import br.com.xandel.mapper.LancamentoMapper;
import br.com.xandel.repository.LancamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Lancamento.
 */
@Service
@Transactional
public class LancamentoService {

    private final LancamentoRepository repository;
    private final LancamentoMapper mapper;
    private final AuditLogService auditLogService;

    public LancamentoService(
            LancamentoRepository repository,
            LancamentoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<LancamentoListDTO> findAll(Integer idEmpresa, Pageable pageable) {
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
    public Optional<LancamentoResponseDTO> findById(Integer idLancamento) {
        return repository.findById(idLancamento)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public LancamentoResponseDTO create(LancamentoRequestDTO dto) {
        Lancamento entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Lancamento", entity.getIdLancamento(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<LancamentoResponseDTO> update(Integer idLancamento, LancamentoRequestDTO dto) {
        return repository.findById(idLancamento)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                LancamentoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Lancamento saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Lancamento", idLancamento, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idLancamento) {
        return repository.findById(idLancamento)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                LancamentoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Lancamento", idLancamento, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idLancamento) {
        return repository.existsById(idLancamento);
    }
}
