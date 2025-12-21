package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Pessoa;
import br.com.xandel.mapper.PessoaMapper;
import br.com.xandel.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Pessoa.
 */
@Service
@Transactional
public class PessoaService {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;
    private final AuditLogService auditLogService;

    public PessoaService(
            PessoaRepository repository,
            PessoaMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<PessoaListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaResponseDTO> findById(Integer idPessoa) {
        return repository.findById(idPessoa)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public PessoaResponseDTO create(PessoaRequestDTO dto) {
        Pessoa entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Pessoa", entity.getIdPessoa(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<PessoaResponseDTO> update(Integer idPessoa, PessoaRequestDTO dto) {
        return repository.findById(idPessoa)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                PessoaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Pessoa saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Pessoa", idPessoa, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idPessoa) {
        return repository.findById(idPessoa)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                PessoaResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Pessoa", idPessoa, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idPessoa) {
        return repository.existsById(idPessoa);
    }
}
