package br.com.xandel.controller;

import br.com.xandel.dto.*;
import br.com.xandel.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller REST para análise de logs de auditoria.
 */
@RestController
@RequestMapping("/api/audit-log")
@CrossOrigin(origins = "*")
public class AuditLogController {

    private final AuditLogService service;

    public AuditLogController(AuditLogService service) {
        this.service = service;
    }

    /**
     * Lista logs de auditoria com filtros.
     */
    @GetMapping
    public ResponseEntity<Page<AuditLogListDTO>> findAll(
            @RequestParam(required = false) String entidade,
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            Pageable pageable) {
        AuditLogFilterDTO filtro = new AuditLogFilterDTO();
        filtro.setEntidade(entidade);
        filtro.setUsuario(usuario);
        filtro.setDataInicio(dataInicio);
        filtro.setDataFim(dataFim);
        return ResponseEntity.ok(service.findAll(filtro, pageable));
    }

    /**
     * Busca log por ID (retorna dados completos incluindo JSON).
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> findById(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista entidades distintas para filtro.
     */
    @GetMapping("/entidades")
    public ResponseEntity<List<String>> findDistinctEntidades() {
        return ResponseEntity.ok(service.findDistinctEntidades());
    }

    /**
     * Lista usuários distintos para filtro.
     */
    @GetMapping("/usuarios")
    public ResponseEntity<List<String>> findDistinctUsuarios() {
        return ResponseEntity.ok(service.findDistinctUsuarios());
    }
}
