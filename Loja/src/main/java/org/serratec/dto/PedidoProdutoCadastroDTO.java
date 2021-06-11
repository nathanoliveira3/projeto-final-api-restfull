package org.serratec.dto;

import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Produto;
import org.serratec.model.ProdutoPedido;
import org.serratec.repository.ProdutoRepository;

public class PedidoProdutoCadastroDTO {

	private String codigo;
	private Integer quantidade;

	public ProdutoPedido toProdutoPedido(ProdutoRepository produtoRepository) throws ProdutoException {
		ProdutoPedido produtoPedido = new ProdutoPedido();
		produtoPedido.setQuantidade(this.quantidade);

		Produto produto = produtoRepository.findByCodigo(this.codigo)
				.orElseThrow(() -> new ProdutoException("Produto não cadastrado."));

		produtoPedido.setProduto(produto);

		produtoPedido.setPreco(produto.getPreco());

		return produtoPedido;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
