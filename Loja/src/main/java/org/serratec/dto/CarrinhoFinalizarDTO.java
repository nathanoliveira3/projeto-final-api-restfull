package org.serratec.dto;

import org.serratec.exceptions.CarrinhoException;
import org.serratec.exceptions.ClienteException;
import org.serratec.model.Carrinho;
import org.serratec.model.Cliente;
import org.serratec.repository.CarrinhoRepository;
import org.serratec.repository.ClienteRepository;

public class CarrinhoFinalizarDTO {

	private String cpf;
	
	public Carrinho toCarrinho(ClienteRepository clienteRepository, CarrinhoRepository carrinhoRepository)
			throws ClienteException, CarrinhoException {

		Cliente cliente = clienteRepository.findByCpf(this.cpf)
				.orElseThrow(() -> new ClienteException("E-mail não encontrado!"));

		Carrinho carrinho = carrinhoRepository.findByCliente(cliente)
				.orElseThrow(() -> new CarrinhoException ("Carrinho não encontado"));
		
		return carrinho;
	}
}
