package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.MedicoEspecialidade;
import br.com.xandel.mapper.MedicoEspecialidadeMapper;
import br.com.xandel.repository.MedicoEspecialidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import br.com.xandel.entity.MedicoEspecialidadeId;

/**
 * Service para MedicoEspecialidade.
 */
@Service
@Transactional
public class MedicoEspecialidadeService {

    private final MedicoEspecialidadeRepository repository;
    private final MedicoEspecialidadeMapper mapper;
    private final AuditLogService auditLogService;

    public MedicoEspecialidadeService(
            MedicoEspecialidadeRepository repository,
            MedicoEspecialidadeMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<MedicoEspecialidadeListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<MedicoEspecialidadeResponseDTO> findById(MedicoEspecialidadeId id) {
        return repository.findById(id)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public MedicoEspecialidadeResponseDTO create(MedicoEspecialidadeRequestDTO dto) {
        MedicoEspecialidade entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "MedicoEspecialidade", entity.getId(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<MedicoEspecialidadeResponseDTO> update(MedicoEspecialidadeId id, MedicoEspecialidadeRequestDTO dto) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                MedicoEspecialidadeResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                MedicoEspecialidade saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "MedicoEspecialidade", id, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(MedicoEspecialidadeId id) {
        return repository.findById(id)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                MedicoEspecialidadeResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "MedicoEspecialidade", id, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(MedicoEspecialidadeId id) {
        return repository.existsById(id);
    }
}
