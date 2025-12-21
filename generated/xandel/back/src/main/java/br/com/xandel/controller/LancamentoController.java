package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.LancamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Lancamento.
 */
@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

    private final LancamentoService service;

    public LancamentoController(LancamentoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<LancamentoListDTO>> findAll(
            @RequestParam(required = false) Integer idEmpresa,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(idEmpresa, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idLancamento}")
    public ResponseEntity<LancamentoResponseDTO> findById(@PathVariable Integer idLancamento) {
        return service.findById(idLancamento)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<LancamentoResponseDTO> create(@Valid @RequestBody LancamentoRequestDTO dto) {
        LancamentoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idLancamento}")
    public ResponseEntity<LancamentoResponseDTO> update(
            @PathVariable Integer idLancamento,
            @Valid @RequestBody LancamentoRequestDTO dto) {
        return service.update(idLancamento, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idLancamento}")
    public ResponseEntity<Void> delete(@PathVariable Integer idLancamento) {
        if (service.delete(idLancamento)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
