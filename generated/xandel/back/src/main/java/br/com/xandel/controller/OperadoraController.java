package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.OperadoraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Operadora.
 */
@RestController
@RequestMapping("/api/operadoras")
@CrossOrigin(origins = "*")
public class OperadoraController {

    private final OperadoraService service;

    public OperadoraController(OperadoraService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<OperadoraListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idOperadora}")
    public ResponseEntity<OperadoraResponseDTO> findById(@PathVariable Integer idOperadora) {
        return service.findById(idOperadora)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<OperadoraResponseDTO> create(@Valid @RequestBody OperadoraRequestDTO dto) {
        OperadoraResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idOperadora}")
    public ResponseEntity<OperadoraResponseDTO> update(
            @PathVariable Integer idOperadora,
            @Valid @RequestBody OperadoraRequestDTO dto) {
        return service.update(idOperadora, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idOperadora}")
    public ResponseEntity<Void> delete(@PathVariable Integer idOperadora) {
        if (service.delete(idOperadora)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
