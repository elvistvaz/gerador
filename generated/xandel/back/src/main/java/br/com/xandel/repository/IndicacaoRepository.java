package br.com.xandel.repository;

import br.com.xandel.entity.Indicacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Indicacao.
 */
@Repository
public interface IndicacaoRepository extends JpaRepository<Indicacao, Integer>, JpaSpecificationExecutor<Indicacao> {

}
