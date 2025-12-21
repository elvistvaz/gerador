package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.ParametroEmailService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para ParametroEmail.
 */
@RestController
@RequestMapping("/api/parametro-emais")
@CrossOrigin(origins = "*")
public class ParametroEmailController {

    private final ParametroEmailService service;

    public ParametroEmailController(ParametroEmailService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<ParametroEmailListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idParametro}")
    public ResponseEntity<ParametroEmailResponseDTO> findById(@PathVariable Integer idParametro) {
        return service.findById(idParametro)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<ParametroEmailResponseDTO> create(@Valid @RequestBody ParametroEmailRequestDTO dto) {
        ParametroEmailResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idParametro}")
    public ResponseEntity<ParametroEmailResponseDTO> update(
            @PathVariable Integer idParametro,
            @Valid @RequestBody ParametroEmailRequestDTO dto) {
        return service.update(idParametro, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idParametro}")
    public ResponseEntity<Void> delete(@PathVariable Integer idParametro) {
        if (service.delete(idParametro)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
