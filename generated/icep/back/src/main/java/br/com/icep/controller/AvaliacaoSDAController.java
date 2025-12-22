package br.com.icep.controller;

import br.com.icep.dto.*;
import br.com.icep.service.AvaliacaoSDAService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para AvaliacaoSDA.
 */
@RestController
@RequestMapping("/api/avaliacao-s-d-as")
@CrossOrigin(origins = "*")
public class AvaliacaoSDAController {

    private final AvaliacaoSDAService service;

    public AvaliacaoSDAController(AvaliacaoSDAService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<AvaliacaoSDAListDTO>> findAll(
            @RequestParam(required = false) Integer municipioId,
            @RequestParam(required = false) Integer avaliacaoId,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(municipioId, avaliacaoId, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoSDAResponseDTO> findById(@PathVariable Integer id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<AvaliacaoSDAResponseDTO> create(@Valid @RequestBody AvaliacaoSDARequestDTO dto) {
        AvaliacaoSDAResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoSDAResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AvaliacaoSDARequestDTO dto) {
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
