package org.serratec.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.dto.categoria.CategoriaAtualizarDTO;
import org.serratec.dto.categoria.CategoriaDTO;
import org.serratec.dto.categoria.CategoriaDeletarDTO;
import org.serratec.exceptions.CategoriaException;
import org.serratec.model.Categoria;
import org.serratec.repository.CategoriaRepository;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API - Categoria")
@RestController
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@ApiOperation(value = "Pesquisa de categorias geral e por nome.")
	@GetMapping("/categoria")
    public ResponseEntity<?> findByNome(@RequestParam(required = false) String nome) throws CategoriaException{
       
    	if(nome == null) {
    		List<Categoria> categorias = categoriaRepository.findAll();
    		List<CategoriaDTO> dto = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

            return new ResponseEntity<>(categorias, HttpStatus.OK);
    	}
    	
    	Categoria categoria = categoriaRepository.findByNomeContainingIgnoreCase(nome)
    			.orElseThrow(() -> new CategoriaException("Categoria não cadastrada."));
        
    		return  new ResponseEntity<>(categoria, HttpStatus.OK);
    }
	
	@GetMapping("/categoria/{id}")
	public ResponseEntity<?> getCategoriaPorId(@PathVariable Long id) throws CategoriaException{
		Optional<Categoria> optional = categoriaRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new CategoriaException("Categoria não cadastrada");
		}
				
		
		return new ResponseEntity<>(optional.get(), HttpStatus.OK);
	}
	
	@GetMapping("/categoria/por-nome/{nome}")
	public ResponseEntity<?> getCategoriaPorNome(@PathVariable String nome) throws CategoriaException{
		Categoria categoria = categoriaRepository.findByNomeContainingIgnoreCase(nome)
				.orElseThrow(() -> new CategoriaException("Categoria não cadastrada"));
		
		return new ResponseEntity<>(categoria, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Cadastro de categoria.")
    @PostMapping("/categoria")
    public ResponseEntity<?> postCategoria(@RequestBody Categoria nova) {
        categoriaRepository.save(nova);
        
        return new ResponseEntity<>("Categoria registrada com sucesso!", HttpStatus.OK);
    }

	@ApiOperation(value = "Modificação de categoria.")
    @PutMapping("/categoria/{id}")
    public ResponseEntity<?> putCategoria(@PathVariable Long id,@RequestBody CategoriaAtualizarDTO dto) throws CategoriaException {
    	Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaException("Categoria não encontrada!"));

		categoria.setNome(dto.getNome());
		categoria.setDescricao(dto.getDescricao());
        
        categoriaRepository.save(categoria);
        return new ResponseEntity<>("Categoria alterada com sucesso!", HttpStatus.OK);
    }

	@ApiOperation(value = "Exclusão de categoria.")
    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id) throws CategoriaException {
    	Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaException("Categoria não encontrada!"));    

        categoriaRepository.delete(categoria);
        
        return new ResponseEntity<>("Categoria deletada com sucesso!", HttpStatus.OK);
    }
}
