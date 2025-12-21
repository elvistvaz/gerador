package br.com.xandel.service;

import br.com.xandel.dto.*;
import br.com.xandel.entity.AuditLog;
import br.com.xandel.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service para gerenciamento de logs de auditoria.
 */
@Service
public class AuditLogService {

    private final AuditLogRepository repository;
    private final ObjectMapper objectMapper;

    public AuditLogService(AuditLogRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    /**
     * Registra uma ação no log de auditoria.
     * Usa REQUIRES_NEW para garantir que o log seja salvo em transação separada.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrar(String acao, String entidade, Object chave, Object dadosAnteriores) {
        try {
            String usuario = getUsuarioLogado();
            String chaveStr = chave != null ? chave.toString() : "";
            String jsonDados = dadosAnteriores != null ? objectMapper.writeValueAsString(dadosAnteriores) : null;

            AuditLog log = new AuditLog(acao, entidade, chaveStr, usuario, jsonDados);
            repository.save(log);
            repository.flush();
        } catch (Exception e) {
            // Log de erro para debug
            System.err.println("Erro ao registrar auditoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtém o usuário logado do contexto de segurança.
     */
    private String getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return "sistema";
    }

    /**
     * Busca logs com filtros.
     */
    @Transactional(readOnly = true)
    public Page<AuditLogListDTO> findAll(AuditLogFilterDTO filtro, Pageable pageable) {
        Specification<AuditLog> spec = Specification.where(null);

        if (filtro.getEntidade() != null && !filtro.getEntidade().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("entidade"), filtro.getEntidade()));
        }
        if (filtro.getUsuario() != null && !filtro.getUsuario().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("usuario"), filtro.getUsuario()));
        }
        if (filtro.getDataInicio() != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataHora"), filtro.getDataInicio()));
        }
        if (filtro.getDataFim() != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("dataHora"), filtro.getDataFim()));
        }

        return repository.findAll(spec, pageable).map(this::toListDTO);
    }

    /**
     * Busca log por ID.
     */
    @Transactional(readOnly = true)
    public Optional<AuditLogResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::toResponseDTO);
    }

    /**
     * Lista todas as entidades distintas.
     */
    @Transactional(readOnly = true)
    public List<String> findDistinctEntidades() {
        return repository.findDistinctEntidades();
    }

    /**
     * Lista todos os usuários distintos.
     */
    @Transactional(readOnly = true)
    public List<String> findDistinctUsuarios() {
        return repository.findDistinctUsuarios();
    }

    private AuditLogListDTO toListDTO(AuditLog entity) {
        return new AuditLogListDTO(
            entity.getId(),
            entity.getAcao(),
            entity.getEntidade(),
            entity.getChave(),
            entity.getUsuario(),
            entity.getDataHora()
        );
    }

    private AuditLogResponseDTO toResponseDTO(AuditLog entity) {
        AuditLogResponseDTO dto = new AuditLogResponseDTO();
        dto.setId(entity.getId());
        dto.setAcao(entity.getAcao());
        dto.setEntidade(entity.getEntidade());
        dto.setChave(entity.getChave());
        dto.setUsuario(entity.getUsuario());
        dto.setDataHora(entity.getDataHora());
        dto.setDadosAnteriores(entity.getDadosAnteriores());
        return dto;
    }
}
