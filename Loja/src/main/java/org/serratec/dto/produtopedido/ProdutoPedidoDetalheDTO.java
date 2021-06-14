package org.serratec.dto.produtopedido;

import java.util.ArrayList;
import java.util.List;

import org.serratec.model.Pedido;
import org.serratec.model.PedidoProduto;
import org.serratec.model.Produto;

public class ProdutoPedidoDetalheDTO {

	private List<Produto> produtos;
	private Integer quantidade;
	private Double preco;
	
	//TODO Finalizar este DTO - Acrescentar visualização para produto + quantidade.
	public ProdutoPedidoDetalheDTO(Pedido pedido) {
		
		this.produtos = new ArrayList<>();
		
		for (PedidoProduto p : pedido.getProdutos()) {
			this.produtos.add(p.getProduto());
		}
		
		
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public Double getPreco() {
		return preco;
	}

	
	
}
