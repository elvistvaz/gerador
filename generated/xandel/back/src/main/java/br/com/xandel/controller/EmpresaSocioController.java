package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.EmpresaSocioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.EmpresaSocioId;

/**
 * Controller REST para EmpresaSocio.
 */
@RestController
@RequestMapping("/api/empresa-socios")
@CrossOrigin(origins = "*")
public class EmpresaSocioController {

    private final EmpresaSocioService service;

    public EmpresaSocioController(EmpresaSocioService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<EmpresaSocioListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaSocioResponseDTO> findById(@PathVariable EmpresaSocioId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<EmpresaSocioResponseDTO> create(@Valid @RequestBody EmpresaSocioRequestDTO dto) {
        EmpresaSocioResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaSocioResponseDTO> update(
            @PathVariable EmpresaSocioId id,
            @Valid @RequestBody EmpresaSocioRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable EmpresaSocioId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
