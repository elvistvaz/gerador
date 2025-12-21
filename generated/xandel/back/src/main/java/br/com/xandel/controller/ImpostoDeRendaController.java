package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.ImpostoDeRendaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.ImpostoDeRendaId;

/**
 * Controller REST para ImpostoDeRenda.
 */
@RestController
@RequestMapping("/api/imposto-de-rendas")
@CrossOrigin(origins = "*")
public class ImpostoDeRendaController {

    private final ImpostoDeRendaService service;

    public ImpostoDeRendaController(ImpostoDeRendaService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<ImpostoDeRendaListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImpostoDeRendaResponseDTO> findById(@PathVariable ImpostoDeRendaId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<ImpostoDeRendaResponseDTO> create(@Valid @RequestBody ImpostoDeRendaRequestDTO dto) {
        ImpostoDeRendaResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImpostoDeRendaResponseDTO> update(
            @PathVariable ImpostoDeRendaId id,
            @Valid @RequestBody ImpostoDeRendaRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ImpostoDeRendaId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
