package org.serratec.resource;

import java.util.List;

import org.serratec.dto.EnderecoCadastroDTO;
import org.serratec.model.Endereco;
import org.serratec.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnderecoResource {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@PostMapping("/endereco")
	public ResponseEntity<?> postEndereco(@Validated @RequestBody EnderecoCadastroDTO dto){
		
		try {
			Endereco endereco = dto.toEndereco();
			enderecoRepository.save(endereco);
			return new ResponseEntity<>("Endereco cadastrado com Sucesso", HttpStatus.OK);
			
		}catch(Exception e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping("/endereco")
	public ResponseEntity<?> getEnderecos(){
		List<Endereco> enderecos = enderecoRepository.findAll();
		
		return new ResponseEntity<>(enderecos, HttpStatus.OK);
	}
}
