package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Cidade.
 */
@RestController
@RequestMapping("/api/cidades")
@CrossOrigin(origins = "*")
public class CidadeController {

    private final CidadeService service;

    public CidadeController(CidadeService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<CidadeListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idCidade}")
    public ResponseEntity<CidadeResponseDTO> findById(@PathVariable Integer idCidade) {
        return service.findById(idCidade)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<CidadeResponseDTO> create(@Valid @RequestBody CidadeRequestDTO dto) {
        CidadeResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idCidade}")
    public ResponseEntity<CidadeResponseDTO> update(
            @PathVariable Integer idCidade,
            @Valid @RequestBody CidadeRequestDTO dto) {
        return service.update(idCidade, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idCidade}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCidade) {
        if (service.delete(idCidade)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
