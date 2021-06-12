package org.serratec.resource;

import java.util.List;

import org.serratec.exceptions.CategoriaException;
import org.serratec.model.Categoria;
import org.serratec.repository.CategoriaRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepositry categoriaRepository;
	
	@GetMapping("/categoria")
    public ResponseEntity<?> findByNome(@RequestParam(required = false) String nome) throws CategoriaException{
       
    	if(nome == null) {
    		List<Categoria> categorias = categoriaRepository.findAll();

            return new ResponseEntity<>(categorias, HttpStatus.OK);
    	}
    	
    	Categoria categoria = categoriaRepository.findByNome(nome)
    			.orElseThrow(() -> new CategoriaException("Categoria não cadastrada."));
        
    		return  new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @PostMapping("/categoria/add")
    public void postCategoria(@RequestBody Categoria nova) {
        categoriaRepository.save(nova);
    }

    @PutMapping("/categoria/editar/{nome}")
    public void putCategoria(@PathVariable String nome, @RequestBody Categoria categoriaModificado) throws CategoriaException {
        Categoria categoria = categoriaRepository.findByNome(nome)
        		.orElseThrow(() -> new CategoriaException("Categoria não cadastrada."));        

        categoria.setNome(categoriaModificado.getNome());
        categoria.setDescricao(categoriaModificado.getDescricao());
        
        categoriaRepository.save(categoria);
    }

    @DeleteMapping("/categoria/delete/{nome}")
    public void deleteCategoria(@PathVariable String nome) throws CategoriaException {
        Categoria categoria = categoriaRepository.findByNome(nome)
        		.orElseThrow(() -> new CategoriaException("Categoria não cadastrada."));    

        categoriaRepository.delete(categoria);
    }
}
