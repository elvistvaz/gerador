package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PagamentoNaoSocio;
import br.com.xandel.mapper.PagamentoNaoSocioMapper;
import br.com.xandel.repository.PagamentoNaoSocioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.PagamentoNaoSocioId;

/**
 * Service para PagamentoNaoSocio.
 */
@Service
@Transactional
public class PagamentoNaoSocioService {

    private final PagamentoNaoSocioRepository repository;
    private final PagamentoNaoSocioMapper mapper;
    private final AuditLogService auditLogService;

    public PagamentoNaoSocioService(
            PagamentoNaoSocioRepository repository,
            PagamentoNaoSocioMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<PagamentoNaoSocioListDTO> findAll(Integer idEmpresa, Pageable pageable) {
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
    public Optional<PagamentoNaoSocioResponseDTO> findById(PagamentoNaoSocioId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public PagamentoNaoSocioResponseDTO create(PagamentoNaoSocioRequestDTO dto) {
        PagamentoNaoSocio entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "PagamentoNaoSocio", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<PagamentoNaoSocioResponseDTO> update(PagamentoNaoSocioId id, PagamentoNaoSocioRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                PagamentoNaoSocioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                PagamentoNaoSocio saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "PagamentoNaoSocio", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(PagamentoNaoSocioId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                PagamentoNaoSocioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "PagamentoNaoSocio", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(PagamentoNaoSocioId id) {
        return repository.existsById(id);
    }
}
