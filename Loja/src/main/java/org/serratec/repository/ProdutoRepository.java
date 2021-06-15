package org.serratec.repository;

import java.util.Optional;

import org.serratec.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {	

	Optional<Produto> findByCodigo(String nome);

	Optional<Produto> findByNome(String nome);

	Optional<Produto> findByNomeContainingIgnoreCase(String nome);

}
