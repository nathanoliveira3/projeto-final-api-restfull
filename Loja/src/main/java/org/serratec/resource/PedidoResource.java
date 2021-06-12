package org.serratec.resource;

import java.util.List;

import org.serratec.exceptions.PedidoException;
import org.serratec.model.Pedido;
import org.serratec.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoResource {

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping("/pedido")
	public ResponseEntity<?> getPedidos() {
		List<Pedido> pedidos = pedidoRepository.findAll();

		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}

	@PutMapping("/pedido/status/{codigo}")
	public ResponseEntity<?> putStatus(@RequestBody Pedido pedidoAlterado, @PathVariable String codigo)
			throws PedidoException {
		Pedido pedido = pedidoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new PedidoException("Pedido não encontrado."));

		pedido.setStatus(pedidoAlterado.getStatus());

		pedidoRepository.save(pedido);

		return new ResponseEntity<>("Pedido alterado com sucesso", HttpStatus.OK);
	}
}
