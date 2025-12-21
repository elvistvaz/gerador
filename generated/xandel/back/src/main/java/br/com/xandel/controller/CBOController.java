package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.CBOService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para CBO.
 */
@RestController
@RequestMapping("/api/c-b-os")
@CrossOrigin(origins = "*")
public class CBOController {

    private final CBOService service;

    public CBOController(CBOService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<CBOListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idCBO}")
    public ResponseEntity<CBOResponseDTO> findById(@PathVariable String idCBO) {
        return service.findById(idCBO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<CBOResponseDTO> create(@Valid @RequestBody CBORequestDTO dto) {
        CBOResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idCBO}")
    public ResponseEntity<CBOResponseDTO> update(
            @PathVariable String idCBO,
            @Valid @RequestBody CBORequestDTO dto) {
        return service.update(idCBO, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idCBO}")
    public ResponseEntity<Void> delete(@PathVariable String idCBO) {
        if (service.delete(idCBO)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
