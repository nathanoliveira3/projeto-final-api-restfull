package org.serratec.resource;

import java.util.List;

import org.serratec.dto.cliente.ClienteCadastroDTO;
import org.serratec.dto.cliente.StatusClienteDTO;
import org.serratec.exceptions.ClienteException;
import org.serratec.model.Cliente;
import org.serratec.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteResource {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@PostMapping("/cliente")
	public ResponseEntity<?> postCliente(@Validated @RequestBody ClienteCadastroDTO dto){
		
		Cliente cliente = dto.toCliente();
		clienteRepository.save(cliente);
		return new ResponseEntity<>("Cliente cadastrado com Sucesso", HttpStatus.OK);
	}
	
	@GetMapping("/cliente")
	public ResponseEntity<?> getClientes(){
		List<Cliente> clientes = clienteRepository.findAll();
		
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}
	
	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> update(@PathVariable String cpf, @RequestBody ClienteCadastroDTO dto ) throws ClienteException{
		
		Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(() -> new ClienteException ("Cliente não encontrado"));
		
		cliente.setNome(dto.getNome());
		cliente.setDataNascimento(dto.getDataNascimento());
		cliente.setEmail(dto.getEmail());
		cliente.setEndereco(dto.getEndereco().toEndereco());
		cliente.setSenha(dto.getSenha()); // realizar alteração
		cliente.setTelefone(dto.getTelefone());
		cliente.setUsuario(dto.getUsuario());
		
		clienteRepository.save(cliente);
		
		return new  ResponseEntity<>("Cliente alterado com sucesso",HttpStatus.OK);		
	}
	
	@PutMapping("cliente/status")
	public ResponseEntity<?> alterarStatusCLiente(@RequestBody StatusClienteDTO dto){
		
		try {
			Cliente cliente = dto.toCliente(clienteRepository);
			return new ResponseEntity<>(cliente, HttpStatus.OK);
		} catch (ClienteException e) {			
			return new ResponseEntity<>("Cliente não cadastrado.", HttpStatus.BAD_REQUEST);
		}
	}
}
