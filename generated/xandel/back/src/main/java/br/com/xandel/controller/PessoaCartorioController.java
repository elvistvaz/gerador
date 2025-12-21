package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.PessoaCartorioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.PessoaCartorioId;

/**
 * Controller REST para PessoaCartorio.
 */
@RestController
@RequestMapping("/api/pessoa-cartorios")
@CrossOrigin(origins = "*")
public class PessoaCartorioController {

    private final PessoaCartorioService service;

    public PessoaCartorioController(PessoaCartorioService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<PessoaCartorioListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PessoaCartorioResponseDTO> findById(@PathVariable PessoaCartorioId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<PessoaCartorioResponseDTO> create(@Valid @RequestBody PessoaCartorioRequestDTO dto) {
        PessoaCartorioResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PessoaCartorioResponseDTO> update(
            @PathVariable PessoaCartorioId id,
            @Valid @RequestBody PessoaCartorioRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable PessoaCartorioId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
