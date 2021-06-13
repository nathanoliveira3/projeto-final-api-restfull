package org.serratec.dto.produto;

import java.time.LocalDate;

import org.serratec.dto.categoria.CategoriaDTO;
import org.serratec.model.Produto;

public class ProdutoDetalheDTO {

	private String codigo;
	private String nome;
	private String descricao;
	private Double preco;
	private CategoriaDTO categoria;
	private LocalDate dataCadastro;
	private Integer quantidade;
	
	public ProdutoDetalheDTO(Produto produto) {
		
		this.codigo = produto.getCodigo();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
		this.categoria = new CategoriaDTO(produto.getCategoria());
		this.dataCadastro = produto.getDataCadastro();
		this.quantidade = produto.getEstoque();
	}
	
	
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
	public CategoriaDTO getCategoria() {
		return categoria;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	
}
