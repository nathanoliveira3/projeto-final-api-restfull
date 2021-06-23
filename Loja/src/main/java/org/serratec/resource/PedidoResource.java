package org.serratec.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

//import org.serratec.dto.pedido.PedidoDeletarDTO;
import org.serratec.dto.pedido.PedidoDetalheDTO;
import org.serratec.dto.pedido.StatusPedidoAlterarDTO;
import org.serratec.exceptions.CarrinhoException;
import org.serratec.exceptions.PedidoException;
import org.serratec.model.Pedido;
import org.serratec.model.PedidoProduto;
import org.serratec.repository.PedidoRepository;
import org.serratec.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API - Pedido")
@RestController
public class PedidoResource {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	EmailService emailService;

	@ApiOperation(value = "Pesquisa de pedidos")
	@GetMapping("/pedido")
	public ResponseEntity<?> getPedidos() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoDetalheDTO> dto = pedidos.stream().map(obj -> new PedidoDetalheDTO(obj))
				.collect(Collectors.toList());

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@ApiOperation(value = "Pesquisa de pedidos por código")
	@GetMapping("/pedido/por-codigo/{codigo}")
	public ResponseEntity<?> getPedidoPorCodigo(@PathVariable String codigo) throws CarrinhoException {
		Optional<Pedido> pedido = pedidoRepository.findByCodigo(codigo);

		if (pedido.isEmpty())
			return new ResponseEntity<>("Pedido não cadastrado", HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(new PedidoDetalheDTO(pedido.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Alteração de status do pedido.")
	@PutMapping("/pedido/statusPedido")
	public ResponseEntity<?> putStatus(@RequestBody StatusPedidoAlterarDTO dto) throws MessagingException {

		Pedido pedido;
		try {

			pedido = dto.toPedido(pedidoRepository);
			pedidoRepository.save(pedido);

			List<String> produtos = new ArrayList<>();
			for (PedidoProduto p : pedido.getProdutos()) {
				produtos.add(p.getProduto().getNome());
			}

			List<Integer> quantidades = new ArrayList<>();
			for (PedidoProduto pp : pedido.getProdutos()) {
				quantidades.add(pp.getQuantidade());
			}

			emailService.enviar("Olá, O status do seu pedido foi atualizado para " + pedido.getStatus(),
					"Data de Entrega: " + LocalDate.now().plusDays(15) + "\nProdutos: " + produtos + "\nQuantidade: "
							+ quantidades + "\nValor Total = " + pedido.getValorTotal(),
					pedido.getCliente().getEmail());

			return new ResponseEntity<>("Pedido alterado com sucesso", HttpStatus.OK);
		} catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/*@ApiOperation(value = "Exclusão de pedido")
	@DeleteMapping("/pedido")
	public ResponseEntity<?> deletePedido(@RequestBody PedidoDeletarDTO dto) throws PedidoException{
		Pedido pedido = pedidoRepository.findByCodigo(dto.getCodigo()).orElseThrow(() -> new PedidoException("Pedido não encontrado!"));
		
		pedidoRepository.delete(pedido);
		return new ResponseEntity<>("Pedido deletado com sucesso!", HttpStatus.OK);
	}*/
}
