package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.EmpresaClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.xandel.entity.EmpresaClienteId;

/**
 * Controller REST para EmpresaCliente.
 */
@RestController
@RequestMapping("/api/empresa-clientes")
@CrossOrigin(origins = "*")
public class EmpresaClienteController {

    private final EmpresaClienteService service;

    public EmpresaClienteController(EmpresaClienteService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<EmpresaClienteListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaClienteResponseDTO> findById(@PathVariable EmpresaClienteId id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<EmpresaClienteResponseDTO> create(@Valid @RequestBody EmpresaClienteRequestDTO dto) {
        EmpresaClienteResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaClienteResponseDTO> update(
            @PathVariable EmpresaClienteId id,
            @Valid @RequestBody EmpresaClienteRequestDTO dto) {
        return service.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable EmpresaClienteId id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
