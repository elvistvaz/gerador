package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.SegmentoAtendido;
import br.com.icep.mapper.SegmentoAtendidoMapper;
import br.com.icep.repository.SegmentoAtendidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para SegmentoAtendido.
 */
@Service
@Transactional
public class SegmentoAtendidoService {

    private final SegmentoAtendidoRepository repository;
    private final SegmentoAtendidoMapper mapper;
    private final AuditLogService auditLogService;

    public SegmentoAtendidoService(
            SegmentoAtendidoRepository repository,
            SegmentoAtendidoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<SegmentoAtendidoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<SegmentoAtendidoResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public SegmentoAtendidoResponseDTO create(SegmentoAtendidoRequestDTO dto) {
        SegmentoAtendido entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "SegmentoAtendido", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<SegmentoAtendidoResponseDTO> update(Integer id, SegmentoAtendidoRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                SegmentoAtendidoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                SegmentoAtendido saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "SegmentoAtendido", id, estadoAnterior);
                
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
                SegmentoAtendidoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "SegmentoAtendido", id, estadoAnterior);
                
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
