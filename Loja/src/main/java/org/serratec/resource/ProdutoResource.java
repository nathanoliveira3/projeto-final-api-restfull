package org.serratec.resource;

import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	/*@PostMapping("/produto")
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
    }*/
    
}
