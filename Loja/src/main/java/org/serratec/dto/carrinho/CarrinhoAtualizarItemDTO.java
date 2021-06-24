package org.serratec.dto.carrinho;

import org.serratec.exceptions.ClienteException;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Carrinho;
import org.serratec.model.CarrinhoProduto;
import org.serratec.model.Cliente;
import org.serratec.model.Produto;
import org.serratec.repository.CarrinhoRepository;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.ProdutoRepository;

public class CarrinhoAtualizarItemDTO {

	private String usuario;
	private String codigoProduto;
	private Integer quantidade;
	
	public Carrinho toCarrinho(ClienteRepository clienteRepository, CarrinhoRepository carrinhoRepository)
			throws ClienteException {

		Cliente cliente = clienteRepository.findByUsuarioIgnoreCase(usuario)
				.orElseThrow(() -> new ClienteException("Cliente não cadastrado."));

		Carrinho carrinho = carrinhoRepository.findByCliente(cliente)
				.orElse(new Carrinho());				

		carrinho.setCliente(cliente);
		carrinho.gerarProtocolo();
		
		return carrinho;
	}
	
	public CarrinhoProduto toProduto(ProdutoRepository produtoRepository) throws ProdutoException {

		CarrinhoProduto produtoCarrinho = new CarrinhoProduto();

		produtoCarrinho.setQuantidade(this.quantidade);

		Produto produto = produtoRepository.findByCodigo(this.codigoProduto)
				.orElseThrow(() -> new ProdutoException("Código do produto inexistente."));

		produtoCarrinho.setProduto(produto);
		produtoCarrinho.setPreco(produto.getPreco());

		return produtoCarrinho;
	}	

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
