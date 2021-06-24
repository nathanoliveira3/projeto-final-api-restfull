package org.serratec.dto.cliente;

import org.serratec.exceptions.ClienteException;
import org.serratec.model.Cliente;
import org.serratec.repository.ClienteRepository;

public class ClienteSolicitarEnvioEmailDTO {
	
	private String email;
	
	public Cliente toCliente(ClienteRepository clienteRepository) throws ClienteException {
		Cliente cliente = clienteRepository.findByEmail(this.email)
				.orElseThrow(() -> new ClienteException("Cliente não cadastrado."));

		return cliente;
	}

	public String getEmail() {
		return email;
	}

	public void setCpf(String email) {
		this.email = email;
	}	
}
