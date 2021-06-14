package org.serratec.dto.cliente;

import org.serratec.model.Cliente;

public class ClienteDetalheDTO {

	private String email;
	private String usuario;
	private String nome;
	private String telefone;
	
	public ClienteDetalheDTO(Cliente cliente) {
		this.email = cliente.getEmail();
		this.usuario = cliente.getUsuario();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
	}
	
	public String getEmail() {
		return email;
	}
	public String getUsuario() {
		return usuario;
	}
	public String getNome() {
		return nome;
	}
	public String getTelefone() {
		return telefone;
	}
	
	
	
}
