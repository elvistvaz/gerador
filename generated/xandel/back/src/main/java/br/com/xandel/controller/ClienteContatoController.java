package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.ClienteContatoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para ClienteContato.
 */
@RestController
@RequestMapping("/api/cliente-contatos")
@CrossOrigin(origins = "*")
public class ClienteContatoController {

    private final ClienteContatoService service;

    public ClienteContatoController(ClienteContatoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<ClienteContatoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idClienteContato}")
    public ResponseEntity<ClienteContatoResponseDTO> findById(@PathVariable Integer idClienteContato) {
        return service.findById(idClienteContato)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<ClienteContatoResponseDTO> create(@Valid @RequestBody ClienteContatoRequestDTO dto) {
        ClienteContatoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idClienteContato}")
    public ResponseEntity<ClienteContatoResponseDTO> update(
            @PathVariable Integer idClienteContato,
            @Valid @RequestBody ClienteContatoRequestDTO dto) {
        return service.update(idClienteContato, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idClienteContato}")
    public ResponseEntity<Void> delete(@PathVariable Integer idClienteContato) {
        if (service.delete(idClienteContato)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
