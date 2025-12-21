package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.MedicoEspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.MedicoEspecialidadeId;

/**
 * Controller REST para MedicoEspecialidade.
 */
@RestController
@RequestMapping("/api/medico-especialidades")
@CrossOrigin(origins = "*")
public class MedicoEspecialidadeController {

    private final MedicoEspecialidadeService service;

    public MedicoEspecialidadeController(MedicoEspecialidadeService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<MedicoEspecialidadeListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicoEspecialidadeResponseDTO> findById(@PathVariable MedicoEspecialidadeId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<MedicoEspecialidadeResponseDTO> create(@Valid @RequestBody MedicoEspecialidadeRequestDTO dto) {
        MedicoEspecialidadeResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicoEspecialidadeResponseDTO> update(
            @PathVariable MedicoEspecialidadeId id,
            @Valid @RequestBody MedicoEspecialidadeRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable MedicoEspecialidadeId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
