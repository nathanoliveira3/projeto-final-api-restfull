package org.serratec.dto.carrinho;

import org.serratec.exceptions.CarrinhoException;
import org.serratec.model.Carrinho;
import org.serratec.repository.CarrinhoRepository;

public class CarrinhoFinalizarDTO {

	private String codigo;

	public Carrinho toCarrinho(CarrinhoRepository carrinhoRepository)
			throws CarrinhoException {
		
		Carrinho carrinho = carrinhoRepository.findByCodigo(this.codigo)
				.orElseThrow(() -> new CarrinhoException("Carrinho não encontado"));

		return carrinho;
	}

	public String getCodigo() {
		return codigo;
	}

}
