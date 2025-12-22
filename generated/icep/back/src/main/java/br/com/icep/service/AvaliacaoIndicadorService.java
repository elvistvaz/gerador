package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.AvaliacaoIndicador;
import br.com.icep.mapper.AvaliacaoIndicadorMapper;
import br.com.icep.repository.AvaliacaoIndicadorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para AvaliacaoIndicador.
 */
@Service
@Transactional
public class AvaliacaoIndicadorService {

    private final AvaliacaoIndicadorRepository repository;
    private final AvaliacaoIndicadorMapper mapper;
    private final AuditLogService auditLogService;

    public AvaliacaoIndicadorService(
            AvaliacaoIndicadorRepository repository,
            AvaliacaoIndicadorMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AvaliacaoIndicadorListDTO> findAll(Integer municipioId, Integer avaliacaoId, Pageable pageable) {
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
    public Optional<AvaliacaoIndicadorResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AvaliacaoIndicadorResponseDTO create(AvaliacaoIndicadorRequestDTO dto) {
        AvaliacaoIndicador entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "AvaliacaoIndicador", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AvaliacaoIndicadorResponseDTO> update(Integer id, AvaliacaoIndicadorRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                AvaliacaoIndicadorResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                AvaliacaoIndicador saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "AvaliacaoIndicador", id, estadoAnterior);
                
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
                AvaliacaoIndicadorResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "AvaliacaoIndicador", id, estadoAnterior);
                
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
