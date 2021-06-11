package org.serratec.repository;

import java.util.Optional;

import org.serratec.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {	

	Optional<Produto> findByCodigo(String nome);

}
