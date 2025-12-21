package br.com.xandel.repository;

import br.com.xandel.entity.MedicoEspecialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.MedicoEspecialidadeId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para MedicoEspecialidade.
 */
@Repository
public interface MedicoEspecialidadeRepository extends JpaRepository<MedicoEspecialidade, MedicoEspecialidadeId>, JpaSpecificationExecutor<MedicoEspecialidade> {

    /**
     * Busca por Pessoa.
     */
    List<MedicoEspecialidade> findById_IdPessoa(Integer idPessoa);

    /**
     * Busca por Especialidade.
     */
    List<MedicoEspecialidade> findById_IdEspecialidade(Integer idEspecialidade);

}
