package org.serratec.resource;

import java.util.List;

import org.serratec.dto.PedidoAtualizarItemDTO;
import org.serratec.dto.PedidoCadastroDTO;
import org.serratec.enums.StatusPedido;
import org.serratec.exceptions.ClienteException;
import org.serratec.exceptions.PedidoException;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Pedido;
import org.serratec.model.ProdutoPedido;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.PedidoRepository;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<?> postPedido(@Validated @RequestBody PedidoCadastroDTO dto) {

		try {
			Pedido pedido = dto.toPedido(produtoRepository, clienteRepository);
			pedidoRepository.save(pedido);
			return new ResponseEntity<>("Pedido cadastrado com Sucesso", HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping("/pedido")
	public ResponseEntity<?> getPedidos() {
		List<Pedido> pedidos = pedidoRepository.findAll();

		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}

	@PutMapping("/pedido")
	public ResponseEntity<?> atualizarPedido(@RequestBody PedidoAtualizarItemDTO dto)
			throws ClienteException, ProdutoException {

		try {
			Pedido pedido = dto.toPedido(clienteRepository, pedidoRepository);

			if (!(pedido.getStatus() == StatusPedido.FINALIZADO)) {

				for (ProdutoPedido i : pedido.getProdutos()) {
					if (i.getProduto().getCodigo().equals(dto.getCodigoProduto())) {
						if (dto.getQuantidade() == 0) {
							pedido.getProdutos().remove(i);
						} else {
							i.setQuantidade(dto.getQuantidade());
						}

						if (pedido.getProdutos().isEmpty()) {
							pedidoRepository.delete(pedido);
							return new ResponseEntity<>("Pedido esvaziado", HttpStatus.OK);
						} else {
							pedidoRepository.save(pedido);
							return new ResponseEntity<>("Item atualizado com sucesso!", HttpStatus.OK);
						}
					}
				}

				ProdutoPedido produtoPedido = dto.toProduto(produtoRepository);
				produtoPedido.setPedido(pedido);

				pedido.getProdutos().add(produtoPedido);
				pedidoRepository.save(pedido);

				return new ResponseEntity<>("Pedido adicionado com sucesso.", HttpStatus.OK);
			}
		} catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return null;
	}
	
	@PutMapping("/pedido/status/{codigo}")
	public ResponseEntity<?> putStatus(@RequestBody Pedido pedidoAlterado, @PathVariable String codigo) throws PedidoException{
		Pedido pedido = pedidoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new PedidoException("Pedido não encontrado."));
		
		pedido.setStatus(pedidoAlterado.getStatus());
				
		pedidoRepository.save(pedido);
		
		return new ResponseEntity<>("Pedido alterado com sucesso", HttpStatus.OK);
	}
}
