package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.PessoaCartorio;
import br.com.xandel.mapper.PessoaCartorioMapper;
import br.com.xandel.repository.PessoaCartorioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.PessoaCartorioId;

/**
 * Service para PessoaCartorio.
 */
@Service
@Transactional
public class PessoaCartorioService {

    private final PessoaCartorioRepository repository;
    private final PessoaCartorioMapper mapper;
    private final AuditLogService auditLogService;

    public PessoaCartorioService(
            PessoaCartorioRepository repository,
            PessoaCartorioMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<PessoaCartorioListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaCartorioResponseDTO> findById(PessoaCartorioId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public PessoaCartorioResponseDTO create(PessoaCartorioRequestDTO dto) {
        PessoaCartorio entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "PessoaCartorio", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<PessoaCartorioResponseDTO> update(PessoaCartorioId id, PessoaCartorioRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                PessoaCartorioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                PessoaCartorio saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "PessoaCartorio", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(PessoaCartorioId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                PessoaCartorioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "PessoaCartorio", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(PessoaCartorioId id) {
        return repository.existsById(id);
    }
}
