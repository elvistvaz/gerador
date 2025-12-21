package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.RepasseAnualService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.RepasseAnualId;

/**
 * Controller REST para RepasseAnual.
 */
@RestController
@RequestMapping("/api/repasse-anuais")
@CrossOrigin(origins = "*")
public class RepasseAnualController {

    private final RepasseAnualService service;

    public RepasseAnualController(RepasseAnualService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<RepasseAnualListDTO>> findAll(
            @RequestParam(required = false) Integer idEmpresa,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(idEmpresa, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepasseAnualResponseDTO> findById(@PathVariable RepasseAnualId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<RepasseAnualResponseDTO> create(@Valid @RequestBody RepasseAnualRequestDTO dto) {
        RepasseAnualResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepasseAnualResponseDTO> update(
            @PathVariable RepasseAnualId id,
            @Valid @RequestBody RepasseAnualRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable RepasseAnualId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
