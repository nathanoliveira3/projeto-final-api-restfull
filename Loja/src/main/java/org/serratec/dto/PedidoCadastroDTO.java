package org.serratec.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.exceptions.ClienteException;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Cliente;
import org.serratec.model.Pedido;
import org.serratec.model.ProdutoPedido;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.ProdutoRepository;

public class PedidoCadastroDTO {

	private String cpf;

	private List<PedidoProdutoCadastroDTO> produtos = new ArrayList<>();

	public Pedido toPedido(ProdutoRepository produtoRepository, ClienteRepository clienteRepository)
			throws ProdutoException, ClienteException {
		Pedido pedido = new Pedido();

		pedido.setNumeroPedido(pedido.gerarProtocolo());
		pedido.setDataPedido(LocalDate.now());

		Cliente cliente = clienteRepository.findByCpf(this.cpf)
				.orElseThrow(() -> new ClienteException("Cliente não cadastrado."));
		
		pedido.setCliente(cliente);

		for (PedidoProdutoCadastroDTO dto : produtos) {
			ProdutoPedido pd = dto.toProdutoPedido(produtoRepository);
			pd.setPedido(pedido);
			pedido.getProdutos().add(pd);
		}

		pedido.setValor(pedido.getValorTotal());

		return pedido;
	}

	public List<PedidoProdutoCadastroDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<PedidoProdutoCadastroDTO> produtos) {
		this.produtos = produtos;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
