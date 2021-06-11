package org.serratec.resource;

import java.util.List;

import org.serratec.model.Categoria;
import org.serratec.repository.CategoriaRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepositry categoriaRepository;
	
	@PostMapping("/categoria")
	public ResponseEntity<?> postCategoria(@Validated @RequestBody Categoria categoria){
		
		try {
			
			categoriaRepository.save(categoria);
			return new ResponseEntity<>("Categoria cadastrado com Sucesso", HttpStatus.OK);
			
		}catch(Exception e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping("/categoria")
	public ResponseEntity<?> getCategorias(){
		List<Categoria> categorias = categoriaRepository.findAll();
		
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}
}
