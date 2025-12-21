package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.TipoServico;
import br.com.xandel.mapper.TipoServicoMapper;
import br.com.xandel.repository.TipoServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para TipoServico.
 */
@Service
@Transactional
public class TipoServicoService {

    private final TipoServicoRepository repository;
    private final TipoServicoMapper mapper;
    private final AuditLogService auditLogService;

    public TipoServicoService(
            TipoServicoRepository repository,
            TipoServicoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<TipoServicoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<TipoServicoResponseDTO> findById(Integer idTipoServico) {
        return repository.findById(idTipoServico)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public TipoServicoResponseDTO create(TipoServicoRequestDTO dto) {
        TipoServico entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "TipoServico", entity.getIdTipoServico(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<TipoServicoResponseDTO> update(Integer idTipoServico, TipoServicoRequestDTO dto) {
        return repository.findById(idTipoServico)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                TipoServicoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                TipoServico saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "TipoServico", idTipoServico, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idTipoServico) {
        return repository.findById(idTipoServico)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                TipoServicoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "TipoServico", idTipoServico, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idTipoServico) {
        return repository.existsById(idTipoServico);
    }
}
