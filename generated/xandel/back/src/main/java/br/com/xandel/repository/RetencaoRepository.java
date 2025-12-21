package br.com.xandel.repository;

import br.com.xandel.entity.Retencao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.RetencaoId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Retencao.
 */
@Repository
public interface RetencaoRepository extends JpaRepository<Retencao, RetencaoId>, JpaSpecificationExecutor<Retencao> {

}
