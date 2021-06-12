package org.serratec.dto;

import org.serratec.exceptions.ClienteException;
import org.serratec.exceptions.PedidoException;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Cliente;
import org.serratec.model.Pedido;
import org.serratec.model.Produto;
import org.serratec.model.ProdutoPedido;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.PedidoRepository;
import org.serratec.repository.ProdutoRepository;

public class PedidoAtualizarItemDTO {

	private String cpf;
	private String codigoProduto;
	private Integer quantidade;
	
	public Pedido toPedido(ClienteRepository clienteRepository, PedidoRepository pedidoRepository)
			throws PedidoException, ClienteException {

		Cliente cliente = clienteRepository.findByCpf(this.cpf)
				.orElseThrow(() -> new ClienteException("Cliente não cadastrado."));

		Pedido pedido = pedidoRepository.findByCliente(cliente)
				.orElse(new Pedido());				

		pedido.setCliente(cliente);
		
		return pedido;
	}
	
	public ProdutoPedido toProduto(ProdutoRepository produtoRepository) throws ProdutoException {

		ProdutoPedido produtoPedido = new ProdutoPedido();

		produtoPedido.setQuantidade(this.quantidade);

		Produto produto = produtoRepository.findByCodigo(this.codigoProduto)
				.orElseThrow(() -> new ProdutoException("Código do livro inexistente."));

		produtoPedido.setProduto(produto);

		return produtoPedido;
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
