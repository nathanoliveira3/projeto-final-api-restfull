package org.serratec.dto.pedido;

import org.serratec.enums.StatusPedido;
import org.serratec.exceptions.PedidoException;
import org.serratec.model.Pedido;
import org.serratec.repository.PedidoRepository;

public class StatusPedidoAlterarDTO {
	private String codigo;
	private String status;
	
	public Pedido toPedido(PedidoRepository pedidoRepository) throws PedidoException {
		Pedido pedido = pedidoRepository.findByCodigo(this.codigo)
				.orElseThrow(() -> new PedidoException("Pedido não cadastrado."));
		
		pedido.setStatus(StatusPedido.valueOf(this.status));
		return pedido;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
