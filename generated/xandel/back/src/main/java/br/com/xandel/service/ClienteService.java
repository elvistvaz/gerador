package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.Cliente;
import br.com.xandel.mapper.ClienteMapper;
import br.com.xandel.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Cliente.
 */
@Service
@Transactional
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final AuditLogService auditLogService;

    public ClienteService(
            ClienteRepository repository,
            ClienteMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ClienteListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteResponseDTO> findById(Integer idCliente) {
        return repository.findById(idCliente)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ClienteResponseDTO create(ClienteRequestDTO dto) {
        Cliente entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "Cliente", entity.getIdCliente(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ClienteResponseDTO> update(Integer idCliente, ClienteRequestDTO dto) {
        return repository.findById(idCliente)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ClienteResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                Cliente saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "Cliente", idCliente, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idCliente) {
        return repository.findById(idCliente)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                ClienteResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "Cliente", idCliente, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idCliente) {
        return repository.existsById(idCliente);
    }
}
