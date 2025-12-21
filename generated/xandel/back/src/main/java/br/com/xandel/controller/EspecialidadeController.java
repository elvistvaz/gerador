package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Especialidade.
 */
@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadeController {

    private final EspecialidadeService service;

    public EspecialidadeController(EspecialidadeService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<EspecialidadeListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idEspecialidade}")
    public ResponseEntity<EspecialidadeResponseDTO> findById(@PathVariable Integer idEspecialidade) {
        return service.findById(idEspecialidade)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> create(@Valid @RequestBody EspecialidadeRequestDTO dto) {
        EspecialidadeResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idEspecialidade}")
    public ResponseEntity<EspecialidadeResponseDTO> update(
            @PathVariable Integer idEspecialidade,
            @Valid @RequestBody EspecialidadeRequestDTO dto) {
        return service.update(idEspecialidade, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idEspecialidade}")
    public ResponseEntity<Void> delete(@PathVariable Integer idEspecialidade) {
        if (service.delete(idEspecialidade)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
