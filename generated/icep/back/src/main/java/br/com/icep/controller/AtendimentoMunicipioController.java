package br.com.icep.controller;

import br.com.icep.dto.*;
import br.com.icep.service.AtendimentoMunicipioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para AtendimentoMunicipio.
 */
@RestController
@RequestMapping("/api/atendimento-municipios")
@CrossOrigin(origins = "*")
public class AtendimentoMunicipioController {

    private final AtendimentoMunicipioService service;

    public AtendimentoMunicipioController(AtendimentoMunicipioService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<AtendimentoMunicipioListDTO>> findAll(
            @RequestParam(required = false) Long municipioId,
            @RequestParam(required = false) Long avaliacaoId,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(municipioId, avaliacaoId, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoMunicipioResponseDTO> findById(@PathVariable Integer id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<AtendimentoMunicipioResponseDTO> create(@Valid @RequestBody AtendimentoMunicipioRequestDTO dto) {
        AtendimentoMunicipioResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoMunicipioResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AtendimentoMunicipioRequestDTO dto) {
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
