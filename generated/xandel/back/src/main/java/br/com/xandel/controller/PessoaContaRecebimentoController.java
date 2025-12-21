package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.PessoaContaRecebimentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.PessoaContaRecebimentoId;

/**
 * Controller REST para PessoaContaRecebimento.
 */
@RestController
@RequestMapping("/api/pessoa-conta-recebimentos")
@CrossOrigin(origins = "*")
public class PessoaContaRecebimentoController {

    private final PessoaContaRecebimentoService service;

    public PessoaContaRecebimentoController(PessoaContaRecebimentoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<PessoaContaRecebimentoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PessoaContaRecebimentoResponseDTO> findById(@PathVariable PessoaContaRecebimentoId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<PessoaContaRecebimentoResponseDTO> create(@Valid @RequestBody PessoaContaRecebimentoRequestDTO dto) {
        PessoaContaRecebimentoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PessoaContaRecebimentoResponseDTO> update(
            @PathVariable PessoaContaRecebimentoId id,
            @Valid @RequestBody PessoaContaRecebimentoRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable PessoaContaRecebimentoId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
