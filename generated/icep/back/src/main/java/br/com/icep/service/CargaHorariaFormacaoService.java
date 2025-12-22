package br.com.icep.service;

import br.com.icep.dto.*;
import br.com.icep.entity.CargaHorariaFormacao;
import br.com.icep.mapper.CargaHorariaFormacaoMapper;
import br.com.icep.repository.CargaHorariaFormacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para CargaHorariaFormacao.
 */
@Service
@Transactional
public class CargaHorariaFormacaoService {

    private final CargaHorariaFormacaoRepository repository;
    private final CargaHorariaFormacaoMapper mapper;
    private final AuditLogService auditLogService;

    public CargaHorariaFormacaoService(
            CargaHorariaFormacaoRepository repository,
            CargaHorariaFormacaoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<CargaHorariaFormacaoListDTO> findAll(Integer municipioId, Integer avaliacaoId, Pageable pageable) {
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
    public Optional<CargaHorariaFormacaoResponseDTO> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public CargaHorariaFormacaoResponseDTO create(CargaHorariaFormacaoRequestDTO dto) {
        CargaHorariaFormacao entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "CargaHorariaFormacao", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<CargaHorariaFormacaoResponseDTO> update(Integer id, CargaHorariaFormacaoRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                CargaHorariaFormacaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                CargaHorariaFormacao saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "CargaHorariaFormacao", id, estadoAnterior);
                
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
                CargaHorariaFormacaoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "CargaHorariaFormacao", id, estadoAnterior);
                
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
