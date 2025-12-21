package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ClienteFilial;
import br.com.xandel.mapper.ClienteFilialMapper;
import br.com.xandel.repository.ClienteFilialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para ClienteFilial.
 */
@Service
@Transactional
public class ClienteFilialService {

    private final ClienteFilialRepository repository;
    private final ClienteFilialMapper mapper;
    private final AuditLogService auditLogService;

    public ClienteFilialService(
            ClienteFilialRepository repository,
            ClienteFilialMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ClienteFilialListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteFilialResponseDTO> findById(Integer idClienteFilial) {
        return repository.findById(idClienteFilial)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ClienteFilialResponseDTO create(ClienteFilialRequestDTO dto) {
        ClienteFilial entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "ClienteFilial", entity.getIdClienteFilial(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ClienteFilialResponseDTO> update(Integer idClienteFilial, ClienteFilialRequestDTO dto) {
        return repository.findById(idClienteFilial)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ClienteFilialResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                ClienteFilial saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "ClienteFilial", idClienteFilial, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idClienteFilial) {
        return repository.findById(idClienteFilial)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                ClienteFilialResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "ClienteFilial", idClienteFilial, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idClienteFilial) {
        return repository.existsById(idClienteFilial);
    }
}
