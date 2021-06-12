package org.serratec.resource;

import org.serratec.dto.CarrinhoAtualizarItemDTO;
import org.serratec.exceptions.ClienteException;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Carrinho;
import org.serratec.model.CarrinhoProduto;
import org.serratec.repository.CarrinhoRepository;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
