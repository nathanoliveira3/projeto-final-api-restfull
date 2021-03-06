package org.serratec.dto.pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.dto.cliente.ClienteDetalheDTO;
import org.serratec.dto.pedidoproduto.PedidoProdutoDetalheDTO;
import org.serratec.enums.StatusPedido;
import org.serratec.model.Pedido;
import org.serratec.model.PedidoProduto;
import org.serratec.model.Produto;

public class PedidoDetalheDTO {

	private String codigo;
	private Double valor;
	private LocalDate data;
	private StatusPedido status;
	private ClienteDetalheDTO cliente;
	private List<String> produtos;
	
	public PedidoDetalheDTO(Pedido pedido) {
		this.codigo = pedido.getCodigo();
		this.valor = pedido.getValor();
		this.data = pedido.getDataPedido();
		this.status = pedido.getStatus();
		this.cliente = new ClienteDetalheDTO(pedido.getCliente());
		this.produtos = new ArrayList<>();
		
		for (PedidoProduto p : pedido.getProdutos()) {
			this.produtos.add(p.getProduto().getNome());
		}
		
	}
	
	public String getCodigo() {
		return codigo;
	}
	public Double getValor() {
		return valor;
	}
	public LocalDate getData() {
		return data;
	}
	public StatusPedido getStatus() {
		return status;
	}
	public ClienteDetalheDTO getCliente() {
		return cliente;
	}
	public List<String> getProdutos() {
		return produtos;
	}
	
	
	
}
