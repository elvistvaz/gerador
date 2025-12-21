package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.CartorioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Cartorio.
 */
@RestController
@RequestMapping("/api/cartorios")
@CrossOrigin(origins = "*")
public class CartorioController {

    private final CartorioService service;

    public CartorioController(CartorioService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<CartorioListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idCartorio}")
    public ResponseEntity<CartorioResponseDTO> findById(@PathVariable Integer idCartorio) {
        return service.findById(idCartorio)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<CartorioResponseDTO> create(@Valid @RequestBody CartorioRequestDTO dto) {
        CartorioResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idCartorio}")
    public ResponseEntity<CartorioResponseDTO> update(
            @PathVariable Integer idCartorio,
            @Valid @RequestBody CartorioRequestDTO dto) {
        return service.update(idCartorio, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idCartorio}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCartorio) {
        if (service.delete(idCartorio)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
