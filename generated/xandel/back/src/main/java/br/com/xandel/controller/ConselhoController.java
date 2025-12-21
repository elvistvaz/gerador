package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.ConselhoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Conselho.
 */
@RestController
@RequestMapping("/api/conselhos")
@CrossOrigin(origins = "*")
public class ConselhoController {

    private final ConselhoService service;

    public ConselhoController(ConselhoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<ConselhoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idConselho}")
    public ResponseEntity<ConselhoResponseDTO> findById(@PathVariable Integer idConselho) {
        return service.findById(idConselho)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<ConselhoResponseDTO> create(@Valid @RequestBody ConselhoRequestDTO dto) {
        ConselhoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idConselho}")
    public ResponseEntity<ConselhoResponseDTO> update(
            @PathVariable Integer idConselho,
            @Valid @RequestBody ConselhoRequestDTO dto) {
        return service.update(idConselho, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idConselho}")
    public ResponseEntity<Void> delete(@PathVariable Integer idConselho) {
        if (service.delete(idConselho)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
