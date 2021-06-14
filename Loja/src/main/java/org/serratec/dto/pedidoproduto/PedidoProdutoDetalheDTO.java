package org.serratec.dto.pedidoproduto;

import org.serratec.model.PedidoProduto;

public class PedidoProdutoDetalheDTO {

	private String produto;
	private Integer quantidade;
	private Double preco;
	
	//TODO Finalizar este DTO - Acrescentar visualização para produto + quantidade.
	public PedidoProdutoDetalheDTO(PedidoProduto pedidoProduto) {
		
		this.produto = pedidoProduto.getProduto().getNome();
		this.quantidade = pedidoProduto.getQuantidade();
		this.preco = pedidoProduto.getPreco();
	}
	
	public String getProduto() {
		return produto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public Double getPreco() {
		return preco;
	}

	
	
}
