package org.serratec.resource;

import java.util.List;

import org.serratec.dto.PedidoCadastroDTO;
import org.serratec.model.Pedido;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.PedidoRepository;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoResource {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@PostMapping("/pedido")
	public ResponseEntity<?> postPedido(@Validated @RequestBody PedidoCadastroDTO dto){
		
		try {
			Pedido pedido  = dto.toPedido(produtoRepository, clienteRepository);
			pedidoRepository.save(pedido);
			return new ResponseEntity<>("Pedido cadastrado com Sucesso", HttpStatus.OK);
			
		}catch(Exception e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping("/pedido")
	public ResponseEntity<?> getPedidos(){
		List<Pedido> pedidos = pedidoRepository.findAll();
		
		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}
}
