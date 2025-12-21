package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.AnuidadeCremebService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para AnuidadeCremeb.
 */
@RestController
@RequestMapping("/api/anuidade-cremebs")
@CrossOrigin(origins = "*")
public class AnuidadeCremebController {

    private final AnuidadeCremebService service;

    public AnuidadeCremebController(AnuidadeCremebService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<AnuidadeCremebListDTO>> findAll(
            @RequestParam(required = false) Integer idEmpresa,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(idEmpresa, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idAnuidadeCremeb}")
    public ResponseEntity<AnuidadeCremebResponseDTO> findById(@PathVariable Integer idAnuidadeCremeb) {
        return service.findById(idAnuidadeCremeb)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<AnuidadeCremebResponseDTO> create(@Valid @RequestBody AnuidadeCremebRequestDTO dto) {
        AnuidadeCremebResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idAnuidadeCremeb}")
    public ResponseEntity<AnuidadeCremebResponseDTO> update(
            @PathVariable Integer idAnuidadeCremeb,
            @Valid @RequestBody AnuidadeCremebRequestDTO dto) {
        return service.update(idAnuidadeCremeb, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idAnuidadeCremeb}")
    public ResponseEntity<Void> delete(@PathVariable Integer idAnuidadeCremeb) {
        if (service.delete(idAnuidadeCremeb)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
