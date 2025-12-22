package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.AvaliacaoSDA;
import br.com.icep.mapper.AvaliacaoSDAMapper;
import br.com.icep.repository.AvaliacaoSDARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para AvaliacaoSDA.
 */
@Service
@Transactional
public class AvaliacaoSDAService {

    private final AvaliacaoSDARepository repository;
    private final AvaliacaoSDAMapper mapper;
    private final AuditLogService auditLogService;

    public AvaliacaoSDAService(
            AvaliacaoSDARepository repository,
            AvaliacaoSDAMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AvaliacaoSDAListDTO> findAll(Integer municipioId, Integer avaliacaoId, Pageable pageable) {
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
    public Optional<AvaliacaoSDAResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AvaliacaoSDAResponseDTO create(AvaliacaoSDARequestDTO dto) {
        AvaliacaoSDA entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "AvaliacaoSDA", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AvaliacaoSDAResponseDTO> update(Integer id, AvaliacaoSDARequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                AvaliacaoSDAResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                AvaliacaoSDA saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "AvaliacaoSDA", id, estadoAnterior);
                
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
                AvaliacaoSDAResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "AvaliacaoSDA", id, estadoAnterior);
                
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
