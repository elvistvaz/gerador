package br.com.xandel.repository;

import br.com.xandel.entity.ImpostoDeRenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.ImpostoDeRendaId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para ImpostoDeRenda.
 */
@Repository
public interface ImpostoDeRendaRepository extends JpaRepository<ImpostoDeRenda, ImpostoDeRendaId>, JpaSpecificationExecutor<ImpostoDeRenda> {

}
