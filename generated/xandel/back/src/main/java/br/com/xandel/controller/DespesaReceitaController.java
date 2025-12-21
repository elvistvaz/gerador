package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.DespesaReceitaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para DespesaReceita.
 */
@RestController
@RequestMapping("/api/despesa-receitas")
@CrossOrigin(origins = "*")
public class DespesaReceitaController {

    private final DespesaReceitaService service;

    public DespesaReceitaController(DespesaReceitaService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<DespesaReceitaListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idDespesaReceita}")
    public ResponseEntity<DespesaReceitaResponseDTO> findById(@PathVariable Integer idDespesaReceita) {
        return service.findById(idDespesaReceita)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<DespesaReceitaResponseDTO> create(@Valid @RequestBody DespesaReceitaRequestDTO dto) {
        DespesaReceitaResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idDespesaReceita}")
    public ResponseEntity<DespesaReceitaResponseDTO> update(
            @PathVariable Integer idDespesaReceita,
            @Valid @RequestBody DespesaReceitaRequestDTO dto) {
        return service.update(idDespesaReceita, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idDespesaReceita}")
    public ResponseEntity<Void> delete(@PathVariable Integer idDespesaReceita) {
        if (service.delete(idDespesaReceita)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
