package org.serratec.repository;

import java.util.Optional;

import org.serratec.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByCpf(String cpf);

	Optional<Cliente> findByUsuario(String usuario);

	Optional<Cliente> findByNomeContainingIgnoreCase(String nome);

}
