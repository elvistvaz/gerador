package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Pessoa.
 */
@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<PessoaListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> findById(@PathVariable Integer idPessoa) {
        return service.findById(idPessoa)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<PessoaResponseDTO> create(@Valid @RequestBody PessoaRequestDTO dto) {
        PessoaResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> update(
            @PathVariable Integer idPessoa,
            @Valid @RequestBody PessoaRequestDTO dto) {
        return service.update(idPessoa, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<Void> delete(@PathVariable Integer idPessoa) {
        if (service.delete(idPessoa)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
