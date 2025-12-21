package br.com.xandel.repository;

import br.com.xandel.entity.PessoaCartorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.PessoaCartorioId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para PessoaCartorio.
 */
@Repository
public interface PessoaCartorioRepository extends JpaRepository<PessoaCartorio, PessoaCartorioId>, JpaSpecificationExecutor<PessoaCartorio> {

}
