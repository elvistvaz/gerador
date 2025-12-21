package br.com.icep.repository;

import br.com.icep.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {

    List<Perfil> findByAtivoTrue();

}
