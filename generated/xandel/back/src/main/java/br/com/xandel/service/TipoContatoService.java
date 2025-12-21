package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.TipoContato;
import br.com.xandel.mapper.TipoContatoMapper;
import br.com.xandel.repository.TipoContatoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para TipoContato.
 */
@Service
@Transactional
public class TipoContatoService {

    private final TipoContatoRepository repository;
    private final TipoContatoMapper mapper;
    private final AuditLogService auditLogService;

    public TipoContatoService(
            TipoContatoRepository repository,
            TipoContatoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<TipoContatoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<TipoContatoResponseDTO> findById(Integer idTipoContato) {
        return repository.findById(idTipoContato)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public TipoContatoResponseDTO create(TipoContatoRequestDTO dto) {
        TipoContato entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "TipoContato", entity.getIdTipoContato(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<TipoContatoResponseDTO> update(Integer idTipoContato, TipoContatoRequestDTO dto) {
        return repository.findById(idTipoContato)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                TipoContatoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                TipoContato saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "TipoContato", idTipoContato, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idTipoContato) {
        return repository.findById(idTipoContato)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                TipoContatoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "TipoContato", idTipoContato, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idTipoContato) {
        return repository.existsById(idTipoContato);
    }
}
