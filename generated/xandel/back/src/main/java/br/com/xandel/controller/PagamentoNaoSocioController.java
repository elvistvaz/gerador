package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.PagamentoNaoSocioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.PagamentoNaoSocioId;

/**
 * Controller REST para PagamentoNaoSocio.
 */
@RestController
@RequestMapping("/api/pagamento-nao-socios")
@CrossOrigin(origins = "*")
public class PagamentoNaoSocioController {

    private final PagamentoNaoSocioService service;

    public PagamentoNaoSocioController(PagamentoNaoSocioService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<PagamentoNaoSocioListDTO>> findAll(
            @RequestParam(required = false) Integer idEmpresa,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(idEmpresa, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoNaoSocioResponseDTO> findById(@PathVariable PagamentoNaoSocioId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<PagamentoNaoSocioResponseDTO> create(@Valid @RequestBody PagamentoNaoSocioRequestDTO dto) {
        PagamentoNaoSocioResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoNaoSocioResponseDTO> update(
            @PathVariable PagamentoNaoSocioId id,
            @Valid @RequestBody PagamentoNaoSocioRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable PagamentoNaoSocioId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
