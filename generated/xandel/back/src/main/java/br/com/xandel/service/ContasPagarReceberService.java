package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ContasPagarReceber;
import br.com.xandel.mapper.ContasPagarReceberMapper;
import br.com.xandel.repository.ContasPagarReceberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para ContasPagarReceber.
 */
@Service
@Transactional
public class ContasPagarReceberService {

    private final ContasPagarReceberRepository repository;
    private final ContasPagarReceberMapper mapper;
    private final AuditLogService auditLogService;

    public ContasPagarReceberService(
            ContasPagarReceberRepository repository,
            ContasPagarReceberMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ContasPagarReceberListDTO> findAll(Integer idEmpresa, Pageable pageable) {
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
    public Optional<ContasPagarReceberResponseDTO> findById(Integer idContasPagarReceber) {
        return repository.findById(idContasPagarReceber)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ContasPagarReceberResponseDTO create(ContasPagarReceberRequestDTO dto) {
        ContasPagarReceber entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "ContasPagarReceber", entity.getIdContasPagarReceber(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ContasPagarReceberResponseDTO> update(Integer idContasPagarReceber, ContasPagarReceberRequestDTO dto) {
        return repository.findById(idContasPagarReceber)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ContasPagarReceberResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                ContasPagarReceber saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "ContasPagarReceber", idContasPagarReceber, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idContasPagarReceber) {
        return repository.findById(idContasPagarReceber)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                ContasPagarReceberResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "ContasPagarReceber", idContasPagarReceber, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idContasPagarReceber) {
        return repository.existsById(idContasPagarReceber);
    }
}
