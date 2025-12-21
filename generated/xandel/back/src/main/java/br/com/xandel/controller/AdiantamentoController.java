package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.AdiantamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Adiantamento.
 */
@RestController
@RequestMapping("/api/adiantamentos")
@CrossOrigin(origins = "*")
public class AdiantamentoController {

    private final AdiantamentoService service;

    public AdiantamentoController(AdiantamentoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<AdiantamentoListDTO>> findAll(
            @RequestParam(required = false) Integer idEmpresa,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(idEmpresa, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idAdiantamento}")
    public ResponseEntity<AdiantamentoResponseDTO> findById(@PathVariable Integer idAdiantamento) {
        return service.findById(idAdiantamento)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<AdiantamentoResponseDTO> create(@Valid @RequestBody AdiantamentoRequestDTO dto) {
        AdiantamentoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idAdiantamento}")
    public ResponseEntity<AdiantamentoResponseDTO> update(
            @PathVariable Integer idAdiantamento,
            @Valid @RequestBody AdiantamentoRequestDTO dto) {
        return service.update(idAdiantamento, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idAdiantamento}")
    public ResponseEntity<Void> delete(@PathVariable Integer idAdiantamento) {
        if (service.delete(idAdiantamento)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
