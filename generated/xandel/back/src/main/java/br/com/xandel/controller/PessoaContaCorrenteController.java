package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.PessoaContaCorrenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para PessoaContaCorrente.
 */
@RestController
@RequestMapping("/api/pessoa-conta-correntes")
@CrossOrigin(origins = "*")
public class PessoaContaCorrenteController {

    private final PessoaContaCorrenteService service;

    public PessoaContaCorrenteController(PessoaContaCorrenteService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<PessoaContaCorrenteListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idContaCorrente}")
    public ResponseEntity<PessoaContaCorrenteResponseDTO> findById(@PathVariable Integer idContaCorrente) {
        return service.findById(idContaCorrente)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<PessoaContaCorrenteResponseDTO> create(@Valid @RequestBody PessoaContaCorrenteRequestDTO dto) {
        PessoaContaCorrenteResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idContaCorrente}")
    public ResponseEntity<PessoaContaCorrenteResponseDTO> update(
            @PathVariable Integer idContaCorrente,
            @Valid @RequestBody PessoaContaCorrenteRequestDTO dto) {
        return service.update(idContaCorrente, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idContaCorrente}")
    public ResponseEntity<Void> delete(@PathVariable Integer idContaCorrente) {
        if (service.delete(idContaCorrente)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
