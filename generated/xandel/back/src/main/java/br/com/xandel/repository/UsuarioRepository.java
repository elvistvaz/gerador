package br.com.xandel.repository;

import br.com.xandel.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {

    /**
     * Busca por Nome (contém).
     */
    List<Usuario> findByUsuarioNomeContainingIgnoreCase(String usuarioNome);

    /**
     * Busca por Login.
     */
    Optional<Usuario> findByUsuarioLogin(String usuarioLogin);

    /**
     * Busca por Login (contém).
     */
    List<Usuario> findByUsuarioLoginContainingIgnoreCase(String usuarioLogin);

}
