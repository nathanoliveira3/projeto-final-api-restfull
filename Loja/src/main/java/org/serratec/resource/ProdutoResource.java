package org.serratec.resource;

import java.util.List;

import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Produto;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping("/produto")
	public ResponseEntity<?> postProduto(@Validated @RequestBody Produto produto){
		
		produtoRepository.save(produto);
		return new ResponseEntity<>("Produto cadastrado com Sucesso", HttpStatus.OK);
	}
	
	@GetMapping("/produto")
    public ResponseEntity<?> findByNome(@RequestParam(required = false) String nome) throws ProdutoException{
       
    	if(nome == null) {
    		List<Produto> produtos = produtoRepository.findAll();

            return new ResponseEntity<>(produtos, HttpStatus.OK);
    	}
    	
    	Produto produto = produtoRepository.findByNome(nome)
    			.orElseThrow(() -> new ProdutoException("Produto não cadastrada."));
        
    		return  new ResponseEntity<>(produto, HttpStatus.OK);
    }
	
	@GetMapping("/produto/por-codigo/{codigo}")
	public ResponseEntity<?> getProdutoPorCodigo(@PathVariable String codigo) throws ProdutoException{
		
		Produto produto = produtoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new ProdutoException("Produto não cadastrado"));
		
		return new ResponseEntity<>(produto, HttpStatus.OK);
	}
	
   
    @PutMapping("/produto/editar/{codigo}")
    public void putProduto(@PathVariable String codigo, @RequestBody Produto produtoModificado) throws ProdutoException {
        Produto produto = produtoRepository.findByCodigo(codigo)
        		.orElseThrow(() -> new ProdutoException("Produto não cadastrada."));        

        produto.setNome(produtoModificado.getNome());
        produto.setDescricao(produtoModificado.getDescricao());
        
        produtoRepository.save(produto);
    } 
    
    @DeleteMapping("/produto/delete/{codigo}")
    public void deleteProduto(@PathVariable String codigo) throws ProdutoException {
        Produto produto = produtoRepository.findByCodigo(codigo)
        		.orElseThrow(() -> new ProdutoException("Produto não cadastrada."));    

        produtoRepository.delete(produto);
    }
}
