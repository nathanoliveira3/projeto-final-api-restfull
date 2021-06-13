package org.serratec.dto;

import org.serratec.enums.Estado;
import org.serratec.model.Endereco;
import org.springframework.web.client.RestTemplate;

public class EnderecoCadastroDTO {

	private String cep;
	private String numero;
	private String complemento;
	
	public Endereco toEndereco() {
		Endereco endereco = new Endereco();
		endereco.setCep(this.cep);
		endereco.setNumero(this.numero);
		endereco.setComplemento(this.complemento);
		
		String uri = "https://viacep.com.br/ws/" + this.cep + "/json/";

	    RestTemplate rest = new RestTemplate();    
	    EnderecoViaCepDTO viaCep = rest.getForObject(uri, EnderecoViaCepDTO.class);
	    
	    
	    endereco.setEstado(Estado.valueOf(viaCep.getUf()));
	    endereco.setCidade(viaCep.getLocalidade());
	    endereco.setBairro(viaCep.getBairro());
	    endereco.setRua(viaCep.getLogradouro());
	    
	    return endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
