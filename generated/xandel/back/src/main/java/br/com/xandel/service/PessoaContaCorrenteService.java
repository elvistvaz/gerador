package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PessoaContaCorrente;
import br.com.xandel.mapper.PessoaContaCorrenteMapper;
import br.com.xandel.repository.PessoaContaCorrenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para PessoaContaCorrente.
 */
@Service
@Transactional
public class PessoaContaCorrenteService {

    private final PessoaContaCorrenteRepository repository;
    private final PessoaContaCorrenteMapper mapper;
    private final AuditLogService auditLogService;

    public PessoaContaCorrenteService(
            PessoaContaCorrenteRepository repository,
            PessoaContaCorrenteMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<PessoaContaCorrenteListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaContaCorrenteResponseDTO> findById(Integer idContaCorrente) {
        return repository.findById(idContaCorrente)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public PessoaContaCorrenteResponseDTO create(PessoaContaCorrenteRequestDTO dto) {
        PessoaContaCorrente entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "PessoaContaCorrente", entity.getIdContaCorrente(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<PessoaContaCorrenteResponseDTO> update(Integer idContaCorrente, PessoaContaCorrenteRequestDTO dto) {
        return repository.findById(idContaCorrente)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                PessoaContaCorrenteResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                PessoaContaCorrente saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "PessoaContaCorrente", idContaCorrente, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idContaCorrente) {
        return repository.findById(idContaCorrente)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                PessoaContaCorrenteResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "PessoaContaCorrente", idContaCorrente, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idContaCorrente) {
        return repository.existsById(idContaCorrente);
    }
}
