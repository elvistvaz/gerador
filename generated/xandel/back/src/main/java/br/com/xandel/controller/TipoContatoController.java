package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.TipoContatoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para TipoContato.
 */
@RestController
@RequestMapping("/api/tipo-contatos")
@CrossOrigin(origins = "*")
public class TipoContatoController {

    private final TipoContatoService service;

    public TipoContatoController(TipoContatoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<TipoContatoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idTipoContato}")
    public ResponseEntity<TipoContatoResponseDTO> findById(@PathVariable Integer idTipoContato) {
        return service.findById(idTipoContato)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<TipoContatoResponseDTO> create(@Valid @RequestBody TipoContatoRequestDTO dto) {
        TipoContatoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idTipoContato}")
    public ResponseEntity<TipoContatoResponseDTO> update(
            @PathVariable Integer idTipoContato,
            @Valid @RequestBody TipoContatoRequestDTO dto) {
        return service.update(idTipoContato, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idTipoContato}")
    public ResponseEntity<Void> delete(@PathVariable Integer idTipoContato) {
        if (service.delete(idTipoContato)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
