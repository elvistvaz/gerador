package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.RetencaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.RetencaoId;

/**
 * Controller REST para Retencao.
 */
@RestController
@RequestMapping("/api/retencoes")
@CrossOrigin(origins = "*")
public class RetencaoController {

    private final RetencaoService service;

    public RetencaoController(RetencaoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<RetencaoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RetencaoResponseDTO> findById(@PathVariable RetencaoId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<RetencaoResponseDTO> create(@Valid @RequestBody RetencaoRequestDTO dto) {
        RetencaoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RetencaoResponseDTO> update(
            @PathVariable RetencaoId id,
            @Valid @RequestBody RetencaoRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable RetencaoId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
