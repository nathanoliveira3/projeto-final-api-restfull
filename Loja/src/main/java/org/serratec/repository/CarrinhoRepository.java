package org.serratec.repository;

import java.util.Optional;

import org.serratec.model.Carrinho;
import org.serratec.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

	Optional<Carrinho> findByCliente(Cliente cliente);

	Optional<Carrinho> findByCodigo(String codigo);

}
