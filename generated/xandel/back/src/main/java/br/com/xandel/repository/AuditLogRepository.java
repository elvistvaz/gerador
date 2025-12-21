package br.com.xandel.repository;

import br.com.xandel.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para AuditLog.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog> {

    /**
     * Busca logs por entidade.
     */
    Page<AuditLog> findByEntidade(String entidade, Pageable pageable);

    /**
     * Busca logs por usuário.
     */
    Page<AuditLog> findByUsuario(String usuario, Pageable pageable);

    /**
     * Busca logs por período.
     */
    Page<AuditLog> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    /**
     * Busca logs por entidade e período.
     */
    Page<AuditLog> findByEntidadeAndDataHoraBetween(String entidade, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    /**
     * Busca logs por usuário e período.
     */
    Page<AuditLog> findByUsuarioAndDataHoraBetween(String usuario, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    /**
     * Lista todas as entidades distintas.
     */
    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT a.entidade FROM AuditLog a ORDER BY a.entidade")
    List<String> findDistinctEntidades();

    /**
     * Lista todos os usuários distintos.
     */
    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT a.usuario FROM AuditLog a WHERE a.usuario IS NOT NULL ORDER BY a.usuario")
    List<String> findDistinctUsuarios();

}
