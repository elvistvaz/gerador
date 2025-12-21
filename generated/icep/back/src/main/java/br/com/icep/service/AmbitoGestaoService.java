package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.AmbitoGestao;
import br.com.icep.mapper.AmbitoGestaoMapper;
import br.com.icep.repository.AmbitoGestaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para AmbitoGestao.
 */
@Service
@Transactional
public class AmbitoGestaoService {

    private final AmbitoGestaoRepository repository;
    private final AmbitoGestaoMapper mapper;
    private final AuditLogService auditLogService;

    public AmbitoGestaoService(
            AmbitoGestaoRepository repository,
            AmbitoGestaoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AmbitoGestaoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<AmbitoGestaoResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AmbitoGestaoResponseDTO create(AmbitoGestaoRequestDTO dto) {
        AmbitoGestao entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "AmbitoGestao", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AmbitoGestaoResponseDTO> update(Integer id, AmbitoGestaoRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                AmbitoGestaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                AmbitoGestao saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "AmbitoGestao", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                AmbitoGestaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "AmbitoGestao", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
