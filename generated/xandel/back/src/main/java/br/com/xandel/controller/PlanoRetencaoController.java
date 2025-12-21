package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.PlanoRetencaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para PlanoRetencao.
 */
@RestController
@RequestMapping("/api/plano-retencoes")
@CrossOrigin(origins = "*")
public class PlanoRetencaoController {

    private final PlanoRetencaoService service;

    public PlanoRetencaoController(PlanoRetencaoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<PlanoRetencaoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idPlanoRetencao}")
    public ResponseEntity<PlanoRetencaoResponseDTO> findById(@PathVariable Integer idPlanoRetencao) {
        return service.findById(idPlanoRetencao)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<PlanoRetencaoResponseDTO> create(@Valid @RequestBody PlanoRetencaoRequestDTO dto) {
        PlanoRetencaoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idPlanoRetencao}")
    public ResponseEntity<PlanoRetencaoResponseDTO> update(
            @PathVariable Integer idPlanoRetencao,
            @Valid @RequestBody PlanoRetencaoRequestDTO dto) {
        return service.update(idPlanoRetencao, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idPlanoRetencao}")
    public ResponseEntity<Void> delete(@PathVariable Integer idPlanoRetencao) {
        if (service.delete(idPlanoRetencao)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
