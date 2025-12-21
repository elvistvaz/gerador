package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.ContasPagarReceberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para ContasPagarReceber.
 */
@RestController
@RequestMapping("/api/contas-pagar-receberes")
@CrossOrigin(origins = "*")
public class ContasPagarReceberController {

    private final ContasPagarReceberService service;

    public ContasPagarReceberController(ContasPagarReceberService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<ContasPagarReceberListDTO>> findAll(
            @RequestParam(required = false) Integer idEmpresa,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(idEmpresa, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idContasPagarReceber}")
    public ResponseEntity<ContasPagarReceberResponseDTO> findById(@PathVariable Integer idContasPagarReceber) {
        return service.findById(idContasPagarReceber)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<ContasPagarReceberResponseDTO> create(@Valid @RequestBody ContasPagarReceberRequestDTO dto) {
        ContasPagarReceberResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idContasPagarReceber}")
    public ResponseEntity<ContasPagarReceberResponseDTO> update(
            @PathVariable Integer idContasPagarReceber,
            @Valid @RequestBody ContasPagarReceberRequestDTO dto) {
        return service.update(idContasPagarReceber, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idContasPagarReceber}")
    public ResponseEntity<Void> delete(@PathVariable Integer idContasPagarReceber) {
        if (service.delete(idContasPagarReceber)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
