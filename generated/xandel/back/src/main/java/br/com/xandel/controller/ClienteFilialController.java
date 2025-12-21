package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.ClienteFilialService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para ClienteFilial.
 */
@RestController
@RequestMapping("/api/cliente-filiais")
@CrossOrigin(origins = "*")
public class ClienteFilialController {

    private final ClienteFilialService service;

    public ClienteFilialController(ClienteFilialService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<ClienteFilialListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idClienteFilial}")
    public ResponseEntity<ClienteFilialResponseDTO> findById(@PathVariable Integer idClienteFilial) {
        return service.findById(idClienteFilial)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<ClienteFilialResponseDTO> create(@Valid @RequestBody ClienteFilialRequestDTO dto) {
        ClienteFilialResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idClienteFilial}")
    public ResponseEntity<ClienteFilialResponseDTO> update(
            @PathVariable Integer idClienteFilial,
            @Valid @RequestBody ClienteFilialRequestDTO dto) {
        return service.update(idClienteFilial, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idClienteFilial}")
    public ResponseEntity<Void> delete(@PathVariable Integer idClienteFilial) {
        if (service.delete(idClienteFilial)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
