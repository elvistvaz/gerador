package br.com.xandel.repository;

import br.com.xandel.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {

    List<Perfil> findByAtivoTrue();

}
