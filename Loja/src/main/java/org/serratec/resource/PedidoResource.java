package org.serratec.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.serratec.dto.StatusPedidoAlterarDTO;
import org.serratec.dto.pedido.PedidoDetalheDTO;
import org.serratec.exceptions.PedidoException;
import org.serratec.model.Pedido;
import org.serratec.model.PedidoProduto;
import org.serratec.repository.PedidoRepository;
import org.serratec.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoResource {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping("/pedido")
	public ResponseEntity<?> getPedidos() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoDetalheDTO> dto = pedidos.stream().map(obj -> new PedidoDetalheDTO(obj)).collect(Collectors.toList());

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PutMapping("/pedido/statusPedido")
	public ResponseEntity<?> putStatus(@RequestBody StatusPedidoAlterarDTO dto) throws MessagingException{	
		
		
		
		Pedido pedido;
		try {
			
			pedido = dto.toPedido(pedidoRepository);
			pedidoRepository.save(pedido);
			
			List<String> produtos = new ArrayList<>();
			for(PedidoProduto p : pedido.getProdutos()) {
				produtos.add(p.getProduto().getNome()); 
			}			
		
			emailService.enviar("Olá, O status do seu pedido foi atualizado para " + pedido.getStatus(),
						"Data de Entrega: " + LocalDate.now().plusDays(15) + 
						"\nProdutos: " + produtos + " Valor Total = " + pedido.getValorTotal(), 
						pedido.getCliente().getEmail());
			
				
				
			
			return new ResponseEntity<>("Pedido alterado com sucesso", HttpStatus.OK);
		} catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}
}
