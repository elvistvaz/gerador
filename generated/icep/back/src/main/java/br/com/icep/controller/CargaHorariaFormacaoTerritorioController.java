package br.com.icep.controller;

import br.com.icep.dto.*;
import br.com.icep.service.CargaHorariaFormacaoTerritorioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para CargaHorariaFormacaoTerritorio.
 */
@RestController
@RequestMapping("/api/carga-horaria-formacao-territorios")
@CrossOrigin(origins = "*")
public class CargaHorariaFormacaoTerritorioController {

    private final CargaHorariaFormacaoTerritorioService service;

    public CargaHorariaFormacaoTerritorioController(CargaHorariaFormacaoTerritorioService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<CargaHorariaFormacaoTerritorioListDTO>> findAll(
            @RequestParam(required = false) Integer avaliacaoId,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAll(avaliacaoId, pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CargaHorariaFormacaoTerritorioResponseDTO> findById(@PathVariable Integer id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<CargaHorariaFormacaoTerritorioResponseDTO> create(@Valid @RequestBody CargaHorariaFormacaoTerritorioRequestDTO dto) {
        CargaHorariaFormacaoTerritorioResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CargaHorariaFormacaoTerritorioResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody CargaHorariaFormacaoTerritorioRequestDTO dto) {
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
