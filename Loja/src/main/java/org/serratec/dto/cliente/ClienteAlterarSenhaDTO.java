package org.serratec.dto.cliente;

import org.serratec.exceptions.ClienteException;
import org.serratec.model.Cliente;
import org.serratec.repository.ClienteRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClienteAlterarSenhaDTO {

	private String cpf;
	private String senha;
	
	public Cliente toCliente(ClienteRepository clienteRepository) throws ClienteException {
		Cliente cliente = clienteRepository.findByCpf(this.cpf)
				.orElseThrow(() -> new ClienteException("Cliente não cadastrado."));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCodificada = encoder.encode(this.senha);
		
		cliente.setSenha(senhaCodificada);
		
		clienteRepository.save(cliente);
		
		return cliente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
