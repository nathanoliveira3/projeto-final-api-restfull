package org.serratec.dto.carrinho;

import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Produto;
import org.serratec.model.CarrinhoProduto;
import org.serratec.repository.ProdutoRepository;

public class CarrinhoProdutoCadastroDTO {

	private String codigo;
	private Integer quantidade;

	public CarrinhoProduto toCarrinhoProduto(ProdutoRepository produtoRepository) throws ProdutoException {
		CarrinhoProduto produtoPedido = new CarrinhoProduto();
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
