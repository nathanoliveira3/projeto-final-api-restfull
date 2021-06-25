package org.serratec.repository;

import java.util.Optional;

import org.serratec.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByNome(String nome);

	Optional<Categoria> findByNomeContainingIgnoreCase(String nome);	

}
