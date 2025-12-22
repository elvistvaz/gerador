package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.AtendimentoMunicipio;
import br.com.icep.mapper.AtendimentoMunicipioMapper;
import br.com.icep.repository.AtendimentoMunicipioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para AtendimentoMunicipio.
 */
@Service
@Transactional
public class AtendimentoMunicipioService {

    private final AtendimentoMunicipioRepository repository;
    private final AtendimentoMunicipioMapper mapper;
    private final AuditLogService auditLogService;

    public AtendimentoMunicipioService(
            AtendimentoMunicipioRepository repository,
            AtendimentoMunicipioMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<AtendimentoMunicipioListDTO> findAll(Integer municipioId, Integer avaliacaoId, Pageable pageable) {
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
    public Optional<AtendimentoMunicipioResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public AtendimentoMunicipioResponseDTO create(AtendimentoMunicipioRequestDTO dto) {
        AtendimentoMunicipio entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "AtendimentoMunicipio", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<AtendimentoMunicipioResponseDTO> update(Integer id, AtendimentoMunicipioRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                AtendimentoMunicipioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                AtendimentoMunicipio saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "AtendimentoMunicipio", id, estadoAnterior);
                
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
                AtendimentoMunicipioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "AtendimentoMunicipio", id, estadoAnterior);
                
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
