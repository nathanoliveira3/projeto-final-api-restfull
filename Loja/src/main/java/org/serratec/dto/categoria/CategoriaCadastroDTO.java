package org.serratec.dto.categoria;

import org.serratec.model.Categoria;

public class CategoriaCadastroDTO {

	private String nome;
	private String codigo;
	private String descricao;
	
	public Categoria toCategoria() {
		
		Categoria categoria = new Categoria();
		categoria.setNome(this.nome);
		categoria.setDescricao(this.descricao);
		categoria.setCodigo(this.codigo);
		
		return categoria;
	}
	
	public String getNome() {
		return nome;
	}
	public String getDescricao() {
		return descricao;
	}
	
}
