package org.serratec.dto.carrinho;

import java.util.ArrayList;
import java.util.List;

import org.serratec.model.Carrinho;
import org.serratec.model.CarrinhoProduto;
import org.serratec.model.Produto;

public class CarrinhoDTO {
	private String cliente;
	private String codigo;
	private List<Produto> produtos = new ArrayList<>();
	

	public CarrinhoDTO(Carrinho carrinho) {
		this.cliente = carrinho.getCliente().getNome();
		this.codigo = carrinho.getCodigo();
		
		for(CarrinhoProduto p : carrinho.getProdutos()) {
			produtos.add(p.getProduto());
		}
	}
	

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}



	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}
