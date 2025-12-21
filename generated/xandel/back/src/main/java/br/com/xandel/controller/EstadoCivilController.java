package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.EstadoCivilService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para EstadoCivil.
 */
@RestController
@RequestMapping("/api/estado-civis")
@CrossOrigin(origins = "*")
public class EstadoCivilController {

    private final EstadoCivilService service;

    public EstadoCivilController(EstadoCivilService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<EstadoCivilListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idEstadoCivil}")
    public ResponseEntity<EstadoCivilResponseDTO> findById(@PathVariable Integer idEstadoCivil) {
        return service.findById(idEstadoCivil)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<EstadoCivilResponseDTO> create(@Valid @RequestBody EstadoCivilRequestDTO dto) {
        EstadoCivilResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idEstadoCivil}")
    public ResponseEntity<EstadoCivilResponseDTO> update(
            @PathVariable Integer idEstadoCivil,
            @Valid @RequestBody EstadoCivilRequestDTO dto) {
        return service.update(idEstadoCivil, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idEstadoCivil}")
    public ResponseEntity<Void> delete(@PathVariable Integer idEstadoCivil) {
        if (service.delete(idEstadoCivil)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
