package org.serratec.resource;

import java.util.List;

import org.serratec.model.Produto;
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
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping("/produto")
	public ResponseEntity<?> postProduto(@Validated @RequestBody Produto produto){
		
		try {
			
			produtoRepository.save(produto);
			return new ResponseEntity<>("Produto cadastrado com Sucesso", HttpStatus.OK);
			
		}catch(Exception e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping("/produto")
	public ResponseEntity<?> getProdutos(){
		List<Produto> produtos = produtoRepository.findAll();
		
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}
}
