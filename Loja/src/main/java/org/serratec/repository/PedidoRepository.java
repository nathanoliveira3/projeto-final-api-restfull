package org.serratec.repository;

import java.util.Optional;

import org.serratec.model.Cliente;
import org.serratec.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	Optional<Pedido> findByCliente(Cliente cliente);

	Optional<Pedido> findByCodigo(String codigo);

}
