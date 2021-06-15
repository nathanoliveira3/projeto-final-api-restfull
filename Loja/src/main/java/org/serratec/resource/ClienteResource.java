package org.serratec.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.serratec.dto.cliente.ClienteAlterarSenhaDTO;
import org.serratec.dto.cliente.ClienteCadastroDTO;
import org.serratec.dto.cliente.ClienteDetalheDTO;
import org.serratec.dto.cliente.ClienteSolicitarEnvioEmailDTO;
import org.serratec.dto.cliente.StatusClienteDTO;
import org.serratec.exceptions.ClienteException;
import org.serratec.model.Cliente;
import org.serratec.repository.ClienteRepository;
import org.serratec.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API - Cliente")
@RestController
public class ClienteResource {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	EmailService emailService;
	
	@ApiOperation(value = "Cadastro de cliente")
	@PostMapping("/cliente")
	public ResponseEntity<?> postCliente(@Validated @RequestBody ClienteCadastroDTO dto){
		
		Cliente cliente = dto.toCliente();
		clienteRepository.save(cliente);
		return new ResponseEntity<>("Cliente cadastrado com Sucesso", HttpStatus.OK);
	}
	
	@ApiOperation(value = "Pesquisa de clientes geral e por nome.")
	@GetMapping("/cliente")
	public ResponseEntity<?> getClientesPorNome(@RequestParam(required = false) String nome) throws ClienteException{
		
		if(nome == null) {
    		List<Cliente> produtos = clienteRepository.findAll();
    		List<ClienteDetalheDTO> todosDTO = produtos.stream().map(obj -> new ClienteDetalheDTO(obj)).collect(Collectors.toList());

            return new ResponseEntity<>(todosDTO, HttpStatus.OK);
    	}
    	
    	Cliente cliente = clienteRepository.findByNomeContainingIgnoreCase(nome)
    			.orElseThrow(() -> new ClienteException("Produto não cadastrado."));
        
    		return  new ResponseEntity<>(cliente, HttpStatus.OK);		
	}	
	
	@ApiOperation(value = "Alteração de clientes.")
	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> update(@PathVariable String cpf, @RequestBody ClienteCadastroDTO dto ) throws ClienteException{
		
		Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(() -> new ClienteException ("Cliente não encontrado"));
		
		cliente.setNome(dto.getNome());
		cliente.setDataNascimento(dto.getDataNascimento());
		cliente.setEmail(dto.getEmail());
		cliente.setEndereco(dto.getEndereco().toEndereco());
		cliente.setSenha(dto.getSenha());
		cliente.setTelefone(dto.getTelefone());
		cliente.setUsuario(dto.getUsuario());
		
		clienteRepository.save(cliente);
		
		return new  ResponseEntity<>("Cliente alterado com sucesso",HttpStatus.OK);		
	}
	
	@ApiOperation(value = "Alteração de status de cadastro do cliente.")
	@PutMapping("cliente/status")
	public ResponseEntity<?> alterarStatusCLiente(@RequestBody StatusClienteDTO dto){
		
		try {
			Cliente cliente = dto.toCliente(clienteRepository);
			return new ResponseEntity<>(cliente, HttpStatus.OK);
		} catch (ClienteException e) {			
			return new ResponseEntity<>("Cliente não cadastrado.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Envio de email para recuperação de senha.")
	@PostMapping("cliente/email")
	public ResponseEntity<?> sendEmail(@RequestBody ClienteSolicitarEnvioEmailDTO dto) throws ClienteException, MessagingException{
		
		Cliente cliente = dto.toCliente(clienteRepository);
		
		emailService.enviar("Olá, você solicitou a recuperação de email. Poderá ser feito pelo seguinte link: localhost:8080/cliente/recuperacao", cliente.getNome(),
				cliente.getEmail());
		
		return new ResponseEntity<>("As instruções para a recuperação da senha foram enviadas para o seu email", HttpStatus.OK);
	}
	
	@ApiOperation(value = "Recuperação de senha.")
	@PostMapping("cliente/recuperacao")
	public ResponseEntity<?> recuperarSenha(@RequestBody ClienteAlterarSenhaDTO dto) throws ClienteException{
		
		Cliente cliente = dto.toCliente(clienteRepository);
		
		clienteRepository.save(cliente);
		
		return new ResponseEntity<>("Senha alterada com sucesso.", HttpStatus.OK);
	}
}
