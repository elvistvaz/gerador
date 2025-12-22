package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.CargaHorariaFormacaoTerritorio;
import br.com.icep.mapper.CargaHorariaFormacaoTerritorioMapper;
import br.com.icep.repository.CargaHorariaFormacaoTerritorioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para CargaHorariaFormacaoTerritorio.
 */
@Service
@Transactional
public class CargaHorariaFormacaoTerritorioService {

    private final CargaHorariaFormacaoTerritorioRepository repository;
    private final CargaHorariaFormacaoTerritorioMapper mapper;
    private final AuditLogService auditLogService;

    public CargaHorariaFormacaoTerritorioService(
            CargaHorariaFormacaoTerritorioRepository repository,
            CargaHorariaFormacaoTerritorioMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<CargaHorariaFormacaoTerritorioListDTO> findAll(Integer avaliacaoId, Pageable pageable) {
        if (avaliacaoId != null) {
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
    public Optional<CargaHorariaFormacaoTerritorioResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public CargaHorariaFormacaoTerritorioResponseDTO create(CargaHorariaFormacaoTerritorioRequestDTO dto) {
        CargaHorariaFormacaoTerritorio entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "CargaHorariaFormacaoTerritorio", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<CargaHorariaFormacaoTerritorioResponseDTO> update(Integer id, CargaHorariaFormacaoTerritorioRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                CargaHorariaFormacaoTerritorioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                CargaHorariaFormacaoTerritorio saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "CargaHorariaFormacaoTerritorio", id, estadoAnterior);
                
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
                CargaHorariaFormacaoTerritorioResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "CargaHorariaFormacaoTerritorio", id, estadoAnterior);
                
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
