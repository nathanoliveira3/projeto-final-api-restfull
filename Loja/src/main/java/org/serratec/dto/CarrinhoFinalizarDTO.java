package org.serratec.dto;

import org.serratec.exceptions.CarrinhoException;
import org.serratec.exceptions.ClienteException;
import org.serratec.model.Carrinho;
import org.serratec.repository.CarrinhoRepository;

public class CarrinhoFinalizarDTO {

	private String codigo;

	public Carrinho toCarrinho(CarrinhoRepository carrinhoRepository)
			throws ClienteException, CarrinhoException {
		
		Carrinho carrinho = carrinhoRepository.findByCodigo(this.codigo)
				.orElseThrow(() -> new CarrinhoException("Carrinho não encontado"));

		return carrinho;
	}

	public String getCpf() {
		return codigo;
	}

}
