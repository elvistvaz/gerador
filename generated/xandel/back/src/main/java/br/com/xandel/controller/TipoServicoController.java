package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.TipoServicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para TipoServico.
 */
@RestController
@RequestMapping("/api/tipo-servicos")
@CrossOrigin(origins = "*")
public class TipoServicoController {

    private final TipoServicoService service;

    public TipoServicoController(TipoServicoService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<TipoServicoListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idTipoServico}")
    public ResponseEntity<TipoServicoResponseDTO> findById(@PathVariable Integer idTipoServico) {
        return service.findById(idTipoServico)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<TipoServicoResponseDTO> create(@Valid @RequestBody TipoServicoRequestDTO dto) {
        TipoServicoResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idTipoServico}")
    public ResponseEntity<TipoServicoResponseDTO> update(
            @PathVariable Integer idTipoServico,
            @Valid @RequestBody TipoServicoRequestDTO dto) {
        return service.update(idTipoServico, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idTipoServico}")
    public ResponseEntity<Void> delete(@PathVariable Integer idTipoServico) {
        if (service.delete(idTipoServico)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
