package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.EmpresaDespesaFixaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.EmpresaDespesaFixaId;

/**
 * Controller REST para EmpresaDespesaFixa.
 */
@RestController
@RequestMapping("/api/empresa-despesa-fixas")
@CrossOrigin(origins = "*")
public class EmpresaDespesaFixaController {

    private final EmpresaDespesaFixaService service;

    public EmpresaDespesaFixaController(EmpresaDespesaFixaService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<EmpresaDespesaFixaListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDespesaFixaResponseDTO> findById(@PathVariable EmpresaDespesaFixaId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<EmpresaDespesaFixaResponseDTO> create(@Valid @RequestBody EmpresaDespesaFixaRequestDTO dto) {
        EmpresaDespesaFixaResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDespesaFixaResponseDTO> update(
            @PathVariable EmpresaDespesaFixaId id,
            @Valid @RequestBody EmpresaDespesaFixaRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable EmpresaDespesaFixaId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
