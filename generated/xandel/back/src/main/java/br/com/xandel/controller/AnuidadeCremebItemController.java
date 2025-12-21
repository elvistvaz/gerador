package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.AnuidadeCremebItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para AnuidadeCremebItem.
 */
@RestController
@RequestMapping("/api/anuidade-cremeb-itens")
@CrossOrigin(origins = "*")
public class AnuidadeCremebItemController {

    private final AnuidadeCremebItemService service;

    public AnuidadeCremebItemController(AnuidadeCremebItemService service) {
        this.service = service;
    }

    /**
     * Lista todos os registros com paginação.
     */
    @GetMapping
    public ResponseEntity<Page<AnuidadeCremebItemListDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Busca registro por ID.
     */
    @GetMapping("/{idAnuidadeCremebItem}")
    public ResponseEntity<AnuidadeCremebItemResponseDTO> findById(@PathVariable Integer idAnuidadeCremebItem) {
        return service.findById(idAnuidadeCremebItem)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria novo registro.
     */
    @PostMapping
    public ResponseEntity<AnuidadeCremebItemResponseDTO> create(@Valid @RequestBody AnuidadeCremebItemRequestDTO dto) {
        AnuidadeCremebItemResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza registro existente.
     */
    @PutMapping("/{idAnuidadeCremebItem}")
    public ResponseEntity<AnuidadeCremebItemResponseDTO> update(
            @PathVariable Integer idAnuidadeCremebItem,
            @Valid @RequestBody AnuidadeCremebItemRequestDTO dto) {
        return service.update(idAnuidadeCremebItem, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove registro por ID.
     */
    @DeleteMapping("/{idAnuidadeCremebItem}")
    public ResponseEntity<Void> delete(@PathVariable Integer idAnuidadeCremebItem) {
        if (service.delete(idAnuidadeCremebItem)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
