package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.NFService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.NFId;

/**
 * Controller REST para NF.
 */
@RestController
@RequestMapping("/api/n-fs")
@CrossOrigin(origins = "*")
public class NFController {

    private final NFService service;

    public NFController(NFService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<NFListDTO>> findAll(
            @RequestParam(required = false) Integer idEmpresa,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(idEmpresa, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NFResponseDTO> findById(@PathVariable NFId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<NFResponseDTO> create(@Valid @RequestBody NFRequestDTO dto) {
        NFResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NFResponseDTO> update(
            @PathVariable NFId id,
            @Valid @RequestBody NFRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable NFId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
