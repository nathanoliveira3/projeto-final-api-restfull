package org.serratec.resource;

import java.util.List;

import org.serratec.model.Cliente;
import org.serratec.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteResource {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@PostMapping("/cliente")
	public ResponseEntity<?> postCliente(@Validated @RequestBody Cliente cliente){
		
		try {
			
			clienteRepository.save(cliente);
			return new ResponseEntity<>("Cliente cadastrado com Sucesso", HttpStatus.OK);
			
		}catch(Exception e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping("/cliente")
	public ResponseEntity<?> getClientes(){
		List<Cliente> clientes = clienteRepository.findAll();
		
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}
}
