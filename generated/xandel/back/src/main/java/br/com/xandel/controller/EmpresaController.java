package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para Empresa.
 */
@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private final EmpresaService service;

    public EmpresaController(EmpresaService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<EmpresaListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaResponseDTO> findById(@PathVariable Integer idEmpresa) {
        return service.findById(idEmpresa)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> create(@Valid @RequestBody EmpresaRequestDTO dto) {
        EmpresaResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaResponseDTO> update(
            @PathVariable Integer idEmpresa,
            @Valid @RequestBody EmpresaRequestDTO dto) {
        return service.update(idEmpresa, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> delete(@PathVariable Integer idEmpresa) {
        if (service.delete(idEmpresa)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
