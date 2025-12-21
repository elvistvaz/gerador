package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Especialidade;
import br.com.xandel.mapper.EspecialidadeMapper;
import br.com.xandel.repository.EspecialidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Especialidade.
 */
@Service
@Transactional
public class EspecialidadeService {

    private final EspecialidadeRepository repository;
    private final EspecialidadeMapper mapper;
    private final AuditLogService auditLogService;

    public EspecialidadeService(
            EspecialidadeRepository repository,
            EspecialidadeMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<EspecialidadeListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<EspecialidadeResponseDTO> findById(Integer idEspecialidade) {
        return repository.findById(idEspecialidade)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public EspecialidadeResponseDTO create(EspecialidadeRequestDTO dto) {
        Especialidade entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Especialidade", entity.getIdEspecialidade(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<EspecialidadeResponseDTO> update(Integer idEspecialidade, EspecialidadeRequestDTO dto) {
        return repository.findById(idEspecialidade)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                EspecialidadeResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Especialidade saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Especialidade", idEspecialidade, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idEspecialidade) {
        return repository.findById(idEspecialidade)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                EspecialidadeResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Especialidade", idEspecialidade, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idEspecialidade) {
        return repository.existsById(idEspecialidade);
    }
}
