package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Banco;
import br.com.xandel.mapper.BancoMapper;
import br.com.xandel.repository.BancoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Banco.
 */
@Service
@Transactional
public class BancoService {

    private final BancoRepository repository;
    private final BancoMapper mapper;
    private final AuditLogService auditLogService;

    public BancoService(
            BancoRepository repository,
            BancoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<BancoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<BancoResponseDTO> findById(String idBanco) {
        return repository.findById(idBanco)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public BancoResponseDTO create(BancoRequestDTO dto) {
        Banco entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Banco", entity.getIdBanco(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<BancoResponseDTO> update(String idBanco, BancoRequestDTO dto) {
        return repository.findById(idBanco)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                BancoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Banco saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Banco", idBanco, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(String idBanco) {
        return repository.findById(idBanco)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                BancoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Banco", idBanco, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(String idBanco) {
        return repository.existsById(idBanco);
    }
}
