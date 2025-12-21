package br.com.icep.controller;

import br.com.icep.dto.*;
import br.com.icep.service.TerritorioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Territorio.
 */
@RestController
@RequestMapping("/api/territorios")
@CrossOrigin(origins = "*")
public class TerritorioController {

    private final TerritorioService service;

    public TerritorioController(TerritorioService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<TerritorioListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TerritorioResponseDTO> findById(@PathVariable Integer id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<TerritorioResponseDTO> create(@Valid @RequestBody TerritorioRequestDTO dto) {
        TerritorioResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TerritorioResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TerritorioRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
