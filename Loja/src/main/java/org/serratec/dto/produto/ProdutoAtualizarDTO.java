package org.serratec.dto.produto;


public class ProdutoAtualizarDTO {

	private String codigo;
	private String nome;
	private String descricao;
	private Double preco;
	private Integer quantidade;
	private String imagem;
	private String categoria;
	
		
	public String getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public String getImagem() {
		return imagem;
	}
	public String getCategoria() {
		return categoria;
	}
	
}
