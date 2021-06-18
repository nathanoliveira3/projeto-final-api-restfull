package org.serratec.dto.carrinho;

import java.util.ArrayList;
import java.util.List;

import org.serratec.exceptions.ClienteException;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Carrinho;
import org.serratec.model.CarrinhoProduto;
import org.serratec.model.Cliente;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.ProdutoRepository;

public class CarrinhoCadastroDTO {

	private String cpf;

	private List<CarrinhoProdutoCadastroDTO> produtos = new ArrayList<>();

	public Carrinho toCarrinho(ProdutoRepository produtoRepository, ClienteRepository clienteRepository)
			throws ProdutoException, ClienteException {
		Carrinho carrinho = new Carrinho();		

		Cliente cliente = clienteRepository.findByCpf(this.cpf)
				.orElseThrow(() -> new ClienteException("Cliente não cadastrado."));
		
		carrinho.setCliente(cliente);

		for (CarrinhoProdutoCadastroDTO dto : produtos) {
			CarrinhoProduto pd = dto.toCarrinhoProduto(produtoRepository);
			pd.setCarrinho(carrinho);
			carrinho.getProdutos().add(pd);
		}		

		return carrinho;
	}

	public List<CarrinhoProdutoCadastroDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<CarrinhoProdutoCadastroDTO> produtos) {
		this.produtos = produtos;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
