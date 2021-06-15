package org.serratec.dto.cliente;

import org.serratec.exceptions.ClienteException;
import org.serratec.model.Cliente;
import org.serratec.repository.ClienteRepository;

public class StatusClienteDTO {

	private String cpf;
	
	public Cliente toCliente(ClienteRepository clienteRepository) throws ClienteException {
		Cliente cliente = clienteRepository.findByCpf(this.cpf)
				.orElseThrow(() -> new ClienteException("Cliente não cadastrado."));
		
		cliente.ativarDesativarCliente();
		clienteRepository.save(cliente);
		
		return cliente;
		
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
