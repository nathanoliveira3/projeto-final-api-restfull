package org.serratec.dto.cliente;

import java.time.LocalDate;

import org.serratec.dto.EnderecoCadastroDTO;
import org.serratec.model.Cliente;
import org.serratec.model.Endereco;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClienteCadastroDTO {

	private String email;
	private String usuario;
	private String nome;
	private String senha;
	private String cpf;
	private String telefone;
	private LocalDate dataNascimento;
	private EnderecoCadastroDTO endereco;

	public Cliente toCliente() {
		Cliente cliente = new Cliente();

		cliente.setEmail(this.email);
		cliente.setUsuario(this.usuario);
		cliente.setNome(this.nome);		
		cliente.setCpf(this.cpf);
		cliente.setTelefone(this.telefone);
		cliente.setDataNascimento(dataNascimento);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCodificada = encoder.encode(this.senha);
		
		cliente.setSenha(senhaCodificada);

		Endereco endereco = this.endereco.toEndereco();
		cliente.setEndereco(endereco);

		return cliente;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EnderecoCadastroDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoCadastroDTO endereco) {
		this.endereco = endereco;
	}

}
