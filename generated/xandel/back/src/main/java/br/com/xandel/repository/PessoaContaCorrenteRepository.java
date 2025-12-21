package br.com.xandel.repository;

import br.com.xandel.entity.PessoaContaCorrente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para PessoaContaCorrente.
 */
@Repository
public interface PessoaContaCorrenteRepository extends JpaRepository<PessoaContaCorrente, Integer>, JpaSpecificationExecutor<PessoaContaCorrente> {

}
