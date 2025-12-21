package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.IndicacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Indicacao.
 */
@RestController
@RequestMapping("/api/indicacoes")
@CrossOrigin(origins = "*")
public class IndicacaoController {

    private final IndicacaoService service;

    public IndicacaoController(IndicacaoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<IndicacaoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idIndicacao}")
    public ResponseEntity<IndicacaoResponseDTO> findById(@PathVariable Integer idIndicacao) {
        return service.findById(idIndicacao)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<IndicacaoResponseDTO> create(@Valid @RequestBody IndicacaoRequestDTO dto) {
        IndicacaoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idIndicacao}")
    public ResponseEntity<IndicacaoResponseDTO> update(
            @PathVariable Integer idIndicacao,
            @Valid @RequestBody IndicacaoRequestDTO dto) {
        return service.update(idIndicacao, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idIndicacao}")
    public ResponseEntity<Void> delete(@PathVariable Integer idIndicacao) {
        if (service.delete(idIndicacao)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
