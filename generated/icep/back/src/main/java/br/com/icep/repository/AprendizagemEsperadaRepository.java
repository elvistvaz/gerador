package br.com.icep.repository;

import br.com.icep.entity.AprendizagemEsperada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AprendizagemEsperada.
 */
@Repository
public interface AprendizagemEsperadaRepository extends JpaRepository<AprendizagemEsperada, Integer>, JpaSpecificationExecutor<AprendizagemEsperada> {

    /**
     * Busca por Descrição (contém).
     */
    List<AprendizagemEsperada> findByDescricaoContainingIgnoreCase(String descricao);

    /**
     * Busca por Código (contém).
     */
    List<AprendizagemEsperada> findByCodigoContainingIgnoreCase(String codigo);

    /**
     * Busca por Componente Curricular.
     */
    List<AprendizagemEsperada> findByComponenteId(Integer componenteId);

    /**
     * Busca por Conceito Aprendido.
     */
    List<AprendizagemEsperada> findByConceitoAprendidoId(Integer conceitoAprendidoId);

}
