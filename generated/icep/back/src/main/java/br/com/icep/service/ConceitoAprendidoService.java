package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.ConceitoAprendido;
import br.com.icep.mapper.ConceitoAprendidoMapper;
import br.com.icep.repository.ConceitoAprendidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para ConceitoAprendido.
 */
@Service
@Transactional
public class ConceitoAprendidoService {

    private final ConceitoAprendidoRepository repository;
    private final ConceitoAprendidoMapper mapper;
    private final AuditLogService auditLogService;

    public ConceitoAprendidoService(
            ConceitoAprendidoRepository repository,
            ConceitoAprendidoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ConceitoAprendidoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ConceitoAprendidoResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ConceitoAprendidoResponseDTO create(ConceitoAprendidoRequestDTO dto) {
        ConceitoAprendido entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "ConceitoAprendido", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ConceitoAprendidoResponseDTO> update(Integer id, ConceitoAprendidoRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ConceitoAprendidoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                ConceitoAprendido saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "ConceitoAprendido", id, estadoAnterior);
                
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
                ConceitoAprendidoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "ConceitoAprendido", id, estadoAnterior);
                
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
