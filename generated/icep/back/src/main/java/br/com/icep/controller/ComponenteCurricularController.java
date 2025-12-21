package br.com.icep.controller;

import br.com.icep.dto.*;
import br.com.icep.service.ComponenteCurricularService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para ComponenteCurricular.
 */
@RestController
@RequestMapping("/api/componente-curriculares")
@CrossOrigin(origins = "*")
public class ComponenteCurricularController {

    private final ComponenteCurricularService service;

    public ComponenteCurricularController(ComponenteCurricularService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<ComponenteCurricularListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ComponenteCurricularResponseDTO> findById(@PathVariable Integer id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<ComponenteCurricularResponseDTO> create(@Valid @RequestBody ComponenteCurricularRequestDTO dto) {
        ComponenteCurricularResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ComponenteCurricularResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ComponenteCurricularRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
