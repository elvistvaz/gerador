package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.BairroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Bairro.
 */
@RestController
@RequestMapping("/api/bairros")
@CrossOrigin(origins = "*")
public class BairroController {

    private final BairroService service;

    public BairroController(BairroService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<BairroListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idBairro}")
    public ResponseEntity<BairroResponseDTO> findById(@PathVariable Integer idBairro) {
        return service.findById(idBairro)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<BairroResponseDTO> create(@Valid @RequestBody BairroRequestDTO dto) {
        BairroResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idBairro}")
    public ResponseEntity<BairroResponseDTO> update(
            @PathVariable Integer idBairro,
            @Valid @RequestBody BairroRequestDTO dto) {
        return service.update(idBairro, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idBairro}")
    public ResponseEntity<Void> delete(@PathVariable Integer idBairro) {
        if (service.delete(idBairro)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
