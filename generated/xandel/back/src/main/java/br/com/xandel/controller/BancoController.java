package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Banco.
 */
@RestController
@RequestMapping("/api/bancos")
@CrossOrigin(origins = "*")
public class BancoController {

    private final BancoService service;

    public BancoController(BancoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<BancoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idBanco}")
    public ResponseEntity<BancoResponseDTO> findById(@PathVariable String idBanco) {
        return service.findById(idBanco)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<BancoResponseDTO> create(@Valid @RequestBody BancoRequestDTO dto) {
        BancoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idBanco}")
    public ResponseEntity<BancoResponseDTO> update(
            @PathVariable String idBanco,
            @Valid @RequestBody BancoRequestDTO dto) {
        return service.update(idBanco, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idBanco}")
    public ResponseEntity<Void> delete(@PathVariable String idBanco) {
        if (service.delete(idBanco)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
