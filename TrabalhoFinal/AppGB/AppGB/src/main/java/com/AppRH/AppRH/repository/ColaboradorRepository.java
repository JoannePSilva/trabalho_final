package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Colaborador;

public interface ColaboradorRepository extends CrudRepository<Colaborador, Long> {

	Colaborador findById(long id);

	Colaborador findByNome(String nome);

	// Query para a busca
	@Query(value = "select u from Colaborador u where u.nome like %?1%")
	List<Colaborador> findByNomes(String nome);

}
