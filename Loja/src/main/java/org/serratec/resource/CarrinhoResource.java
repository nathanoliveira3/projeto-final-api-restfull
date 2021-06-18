package org.serratec.resource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.serratec.dto.carrinho.CarrinhoAtualizarItemDTO;
import org.serratec.dto.carrinho.CarrinhoFinalizarDTO;
import org.serratec.enums.StatusPedido;
import org.serratec.exceptions.CarrinhoException;
import org.serratec.exceptions.ClienteException;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Carrinho;
import org.serratec.model.CarrinhoProduto;
import org.serratec.model.Pedido;
import org.serratec.model.PedidoProduto;
import org.serratec.repository.CarrinhoRepository;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.PedidoRepository;
import org.serratec.repository.ProdutoRepository;
import org.serratec.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API - Carrinho de Produtos")
@RestController
public class CarrinhoResource {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	CarrinhoRepository carrinhoRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	EmailService emailService;

	@ApiOperation(value = "Cadastro e Atualização de produtos no carrinho")
	@PutMapping("/carrinho")
	public ResponseEntity<?> atualizarPedido(@RequestBody CarrinhoAtualizarItemDTO dto)
			throws ClienteException, ProdutoException {

		try {
			Carrinho carrinho = dto.toCarrinho(clienteRepository, carrinhoRepository);

			for (CarrinhoProduto i : carrinho.getProdutos()) {
				if (i.getProduto().getCodigo().equals(dto.getCodigoProduto())) {
					if (dto.getQuantidade() == 0) {
						carrinho.getProdutos().remove(i);
					} else {
						i.setQuantidade(dto.getQuantidade());
					}

					if (carrinho.getProdutos().isEmpty()) {
						carrinhoRepository.delete(carrinho);
						return new ResponseEntity<>("Pedido vazio.", HttpStatus.OK);
					} else {
						carrinhoRepository.save(carrinho);
						return new ResponseEntity<>("Item atualizado com sucesso!", HttpStatus.OK);
					}
				}
			}

			CarrinhoProduto carrinhoProduto = dto.toProduto(produtoRepository);
			carrinhoProduto.setCarrinho(carrinho);

			carrinho.getProdutos().add(carrinhoProduto);
			carrinhoRepository.save(carrinho);

			return new ResponseEntity<>("Adicionado com sucesso.", HttpStatus.OK);
		} catch (ClienteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ProdutoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Pesquisa por todos os carrinhos.")
	@GetMapping("/carrinho")
	public ResponseEntity<?> getDetalhes() {

		List<Carrinho> carrinhos = carrinhoRepository.findAll();

		return new ResponseEntity<>(carrinhos, HttpStatus.OK);

	}
	
	@ApiOperation(value = "Pesquisa de carrinhos por código.")
	@GetMapping("/carrinho/por-codigo/{codigo}")
	public ResponseEntity<?> getCarrinhoPorCodigo(@PathVariable String codigo) throws CarrinhoException{
		Optional<Carrinho> carrinho = carrinhoRepository.findByCodigo(codigo);
				
		if(carrinho.isEmpty())
			return new ResponseEntity<>("Carrinho não cadastrado", HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(carrinho.get(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Finalizar carrinho e fechar pedido.")
	@PostMapping("/carrinho/finalizar")
	public ResponseEntity<?> finalizarCarrinho(@RequestBody CarrinhoFinalizarDTO dto) {

		try {
			Carrinho carrinho = dto.toCarrinho(carrinhoRepository);

			Pedido pedido = new Pedido();
			pedido.setCliente(carrinho.getCliente());
			pedido.setCodigo(pedido.gerarProtocolo());
			pedido.setDataPedido(LocalDate.now());
			pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
			pedido.setValor(carrinho.getValorTotal());

			for (CarrinhoProduto c : carrinho.getProdutos()) {
				PedidoProduto pedidoProduto = new PedidoProduto();
				pedidoProduto.setPedido(pedido);
				pedidoProduto.setProduto(c.getProduto());
				pedidoProduto.setPreco(c.getPreco());
				pedidoProduto.setQuantidade(c.getQuantidade());

				pedido.getProdutos().add(pedidoProduto);
			}

			pedidoRepository.save(pedido);

			carrinhoRepository.delete(carrinho);
			
			try {
				emailService.enviar("Olá, acabamos de receber o seu pedido. " + pedido.getStatus(),
						"Data de Entrega: " + LocalDate.now().plusDays(15) +" Valor: "+ pedido.getValorTotal(),
						pedido.getCliente().getEmail());
			} catch (MessagingException e) {				
				e.printStackTrace();
			}

			return new ResponseEntity<>(pedido, HttpStatus.OK);

		} catch (CarrinhoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
