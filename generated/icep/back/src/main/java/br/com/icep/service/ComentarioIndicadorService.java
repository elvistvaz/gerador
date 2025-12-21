package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.ComentarioIndicador;
import br.com.icep.mapper.ComentarioIndicadorMapper;
import br.com.icep.repository.ComentarioIndicadorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para ComentarioIndicador.
 */
@Service
@Transactional
public class ComentarioIndicadorService {

    private final ComentarioIndicadorRepository repository;
    private final ComentarioIndicadorMapper mapper;
    private final AuditLogService auditLogService;

    public ComentarioIndicadorService(
            ComentarioIndicadorRepository repository,
            ComentarioIndicadorMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ComentarioIndicadorListDTO> findAll(Long municipioId, Long avaliacaoId, Pageable pageable) {
        if (municipioId != null && avaliacaoId != null) {
            return repository.findByMunicipioIdAndAvaliacaoId(municipioId, avaliacaoId, pageable)
                .map(mapper::toListDTO);
        } else if (municipioId != null) {
            return repository.findByMunicipioId(municipioId, pageable)
                .map(mapper::toListDTO);
        } else if (avaliacaoId != null) {
            return repository.findByAvaliacaoId(avaliacaoId, pageable)
                .map(mapper::toListDTO);
        }
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ComentarioIndicadorResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ComentarioIndicadorResponseDTO create(ComentarioIndicadorRequestDTO dto) {
        ComentarioIndicador entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "ComentarioIndicador", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ComentarioIndicadorResponseDTO> update(Integer id, ComentarioIndicadorRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ComentarioIndicadorResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                ComentarioIndicador saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "ComentarioIndicador", id, estadoAnterior);
                
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
                ComentarioIndicadorResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "ComentarioIndicador", id, estadoAnterior);
                
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
