package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PessoaContaRecebimento;
import br.com.xandel.mapper.PessoaContaRecebimentoMapper;
import br.com.xandel.repository.PessoaContaRecebimentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.PessoaContaRecebimentoId;

/**
 * Service para PessoaContaRecebimento.
 */
@Service
@Transactional
public class PessoaContaRecebimentoService {

    private final PessoaContaRecebimentoRepository repository;
    private final PessoaContaRecebimentoMapper mapper;
    private final AuditLogService auditLogService;

    public PessoaContaRecebimentoService(
            PessoaContaRecebimentoRepository repository,
            PessoaContaRecebimentoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<PessoaContaRecebimentoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaContaRecebimentoResponseDTO> findById(PessoaContaRecebimentoId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public PessoaContaRecebimentoResponseDTO create(PessoaContaRecebimentoRequestDTO dto) {
        PessoaContaRecebimento entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "PessoaContaRecebimento", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<PessoaContaRecebimentoResponseDTO> update(PessoaContaRecebimentoId id, PessoaContaRecebimentoRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                PessoaContaRecebimentoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                PessoaContaRecebimento saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "PessoaContaRecebimento", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(PessoaContaRecebimentoId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                PessoaContaRecebimentoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "PessoaContaRecebimento", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(PessoaContaRecebimentoId id) {
        return repository.existsById(id);
    }
}
