package org.serratec.resource;

import java.time.LocalDateTime;
import java.util.List;

import org.serratec.dto.CarrinhoAtualizarItemDTO;
import org.serratec.dto.CarrinhoFinalizarDTO;
import org.serratec.enums.StatusPedido;
import org.serratec.exceptions.ClienteException;
import org.serratec.exceptions.PedidoException;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Carrinho;
import org.serratec.model.CarrinhoProduto;
import org.serratec.model.Pedido;
import org.serratec.repository.CarrinhoRepository;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarrinhoResource {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	CarrinhoRepository carrinhoRepository;

	@Autowired
	ProdutoRepository produtoRepository;

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
						return new ResponseEntity<>("Pedido esvaziado", HttpStatus.OK);
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

			return new ResponseEntity<>("Pedido adicionado com sucesso.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/carrinho/finalizar")
	public ResponseEntity<?> finalizarCarrinho(@RequestBody CarrinhoFinalizarDTO dto) {
		
		try {
			Carrinho carrinho = dto.toCarrinho(clienteRepository, carrinhoRepository);
			Pedido pedido = new Pedido();
			pedido.setLeitor(carrinho.getLeitor());
			pedido.gerarProtocolo();
			pedido.setCupom(dto.getCupom());
			pedido.setDataPedido(LocalDateTime.now());
			pedido.setFormaDePagamento(dto.getPagamento());
			
			for (CarrinhoProduto c : carrinho.getProdutos()) {
				CarrinhoProduto carrinhoProduto = new CarrinhoProduto();
				carrinhoProduto.setPedido(pedido);
				carrinhoProduto.setProduto(c.getProduto());
				carrinhoProduto.setQuantidade(c.getQuantidade());
				carrinhoProduto.setPreco(c.getPreco());
				pedido.getItens().add(pedidoItem);
			}
			
			StatusPedido nova = new StatusPedido();
			nova.setVenda(pedido);
			nova.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
			nova.setDataStatus(LocalDateTime.now());
			
			pedido.StatusPedido().add(nova);
			
			pedidoRepository.save(pedido);
			return new ResponseEntity<>("Venda efetuada com sucesso. Protocolo: " + pedido.getProtocolo() , HttpStatus.OK);
				
		} catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	
	@GetMapping("/carrinho")
	public ResponseEntity<?> getDetalhes (){
	
		List<Carrinho> carrinhos = carrinhoRepository.findAll();
		
		return new ResponseEntity<>(carrinhos, HttpStatus.OK);
		
	}
	
}
