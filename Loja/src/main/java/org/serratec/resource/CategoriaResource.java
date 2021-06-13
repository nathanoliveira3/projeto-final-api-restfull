package org.serratec.resource;

import org.serratec.repository.CategoriaRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepositry categoriaRepository;
	/*@GetMapping("/categoria")
    public ResponseEntity<?> findByNome(@RequestParam(required = false) String nome) throws CategoriaException{
       
    	if(nome == null) {
    		List<Categoria> categorias = categoriaRepository.findAll();
    		List<CategoriaDTO> dto = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

            return new ResponseEntity<>(dto, HttpStatus.OK);
    	}
    	
    	Categoria categoria = categoriaRepository.findByNomeContainingIgnoreCase(nome)
    			.orElseThrow(() -> new CategoriaException("Categoria não cadastrada."));
        
    		return  new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @PostMapping("/categoria/add")
    public ResponseEntity<?> postCategoria(@RequestBody Categoria nova) {
        categoriaRepository.save(nova);
        
        return new ResponseEntity<>("Categoria registrada com sucesso!", HttpStatus.OK);
    }

    @PutMapping("/categoria/editar")
    public ResponseEntity<?> putCategoria(@RequestBody CategoriaAtualizarDTO dto) throws CategoriaException {
    	Categoria categoria = categoriaRepository.findByNomeContainingIgnoreCase(dto.getNome()).orElseThrow(() -> new CategoriaException("Categoria não encontrada!"));

		categoria.setNome(dto.getNome());
		categoria.setDescricao(dto.getDescricao());
        
        categoriaRepository.save(categoria);
        return new ResponseEntity<>("Categoria alterada com sucesso!", HttpStatus.OK);
    }

    @DeleteMapping("/categoria/delete")
    public ResponseEntity<?> deleteCategoria(@RequestBody CategoriaDeletarDTO dto) throws CategoriaException {
    	Categoria categoria = categoriaRepository.findByNomeContainingIgnoreCase(dto.getNome()).orElseThrow(() -> new CategoriaException("Categoria não encontrada!"));    

        categoriaRepository.delete(categoria);
        return new ResponseEntity<>("Categoria deletada com sucesso!", HttpStatus.OK);
    }*/
}
