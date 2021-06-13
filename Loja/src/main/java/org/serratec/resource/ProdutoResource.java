package org.serratec.resource;

import java.time.LocalDate;
import java.util.List;

import org.serratec.dto.produto.ProdutoAtualizarDTO;
import org.serratec.dto.produto.ProdutoCadastrarDTO;
import org.serratec.dto.produto.ProdutoDeletarDTO;
import org.serratec.dto.produto.ProdutoDetalheDTO;
import org.serratec.exceptions.ProdutoException;
import org.serratec.model.Categoria;
import org.serratec.model.Produto;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<?> postProduto(@Validated @RequestBody ProdutoCadastrarDTO dto) throws ProdutoException{
		
		Produto produto = dto.toProduto();
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
	
	@GetMapping("/produto/por-codigo")
	public ResponseEntity<?> getProdutoPorCodigo(@Validated @RequestBody ProdutoDetalheDTO dto) throws ProdutoException{
		
		Produto produto = produtoRepository.findByCodigo(dto.getCodigo())
				.orElseThrow(() -> new ProdutoException("Produto não cadastrado"));
		
		return new ResponseEntity<>(produto, HttpStatus.OK);
	}
	
   
    @PutMapping("/produto/editar")
    public ResponseEntity<?> putProduto(@RequestBody ProdutoAtualizarDTO dto) throws ProdutoException {
		
		Produto produto = produtoRepository.findByCodigo(dto.getCodigo()).orElseThrow(() -> new ProdutoException("Produto não encontrado."));
		
		produto.setDataCadastro(LocalDate.now());
		produto.setNome(dto.getNome());
		produto.setPreco(dto.getPreco());
		produto.setEstoque(dto.getQuantidade());
		produto.setCategoria(new Categoria());
		
		produtoRepository.save(produto);
		
		return new ResponseEntity<>("Produto alterado com sucesso!", HttpStatus.OK);
    } 
    
    @DeleteMapping("/produto/delete")
    public ResponseEntity<?> deleteProduto(@RequestBody ProdutoDeletarDTO dto) throws ProdutoException {
        Produto produto = produtoRepository.findByCodigo(dto.getCodigo())
        		.orElseThrow(() -> new ProdutoException("Produto não cadastrado."));    

        produtoRepository.delete(produto);
        
       return new ResponseEntity<>("Produto deletado com sucesso!", HttpStatus.OK);
    }
    
}
