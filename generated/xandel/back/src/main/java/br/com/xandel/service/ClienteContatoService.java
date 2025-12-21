package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.ClienteContato;
import br.com.xandel.mapper.ClienteContatoMapper;
import br.com.xandel.repository.ClienteContatoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para ClienteContato.
 */
@Service
@Transactional
public class ClienteContatoService {

    private final ClienteContatoRepository repository;
    private final ClienteContatoMapper mapper;
    private final AuditLogService auditLogService;

    public ClienteContatoService(
            ClienteContatoRepository repository,
            ClienteContatoMapper mapper,
            AuditLogService auditLogService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ClienteContatoListDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toListDTO);
    }

    /**
     * Busca registro por ID.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteContatoResponseDTO> findById(Integer idClienteContato) {
        return repository.findById(idClienteContato)
            .map(mapper::toResponseDTO);
    }

    /**
     * Cria novo registro.
     */
    public ClienteContatoResponseDTO create(ClienteContatoRequestDTO dto) {
        ClienteContato entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        
        // Registra auditoria
        auditLogService.registrar("CREATE", "ClienteContato", entity.getIdClienteContato(), null);
        
        return mapper.toResponseDTO(entity);
    }

    /**
     * Atualiza registro existente.
     */
    public Optional<ClienteContatoResponseDTO> update(Integer idClienteContato, ClienteContatoRequestDTO dto) {
        return repository.findById(idClienteContato)
            .map(entity -> {
                // Guarda estado anterior para auditoria
                ClienteContatoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                mapper.updateEntity(dto, entity);
                ClienteContato saved = repository.save(entity);
                
                // Registra auditoria
                auditLogService.registrar("UPDATE", "ClienteContato", idClienteContato, estadoAnterior);
                
                return mapper.toResponseDTO(saved);
            });
    }

    /**
     * Remove registro por ID.
     */
    public boolean delete(Integer idClienteContato) {
        return repository.findById(idClienteContato)
            .map(entity -> {
                // Guarda estado para auditoria antes de deletar
                ClienteContatoResponseDTO estadoAnterior = mapper.toResponseDTO(entity);
                
                repository.delete(entity);
                
                // Registra auditoria
                auditLogService.registrar("DELETE", "ClienteContato", idClienteContato, estadoAnterior);
                
                return true;
            })
            .orElse(false);
    }

    /**
     * Verifica se registro existe.
     */
    @Transactional(readOnly = true)
    public boolean existsById(Integer idClienteContato) {
        return repository.existsById(idClienteContato);
    }
}
