package org.serratec.dto.categoria;

import org.serratec.model.Categoria;

public class CategoriaDTO {

	private String nome;
	private String descricao;
	
	public CategoriaDTO(Categoria categoria) {
		this.nome = categoria.getNome();
		this.descricao = categoria.getDescricao();
	}
	
	public String getNome() {
		return nome;
	}
	public String getDescricao() {
		return descricao;
	}
	
}
