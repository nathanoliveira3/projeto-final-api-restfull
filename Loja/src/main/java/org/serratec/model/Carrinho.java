package org.serratec.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Cliente cliente;

	@ManyToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CarrinhoProduto> produtos = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CarrinhoProduto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<CarrinhoProduto> produtos) {
		this.produtos = produtos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Double getValorTotal() {
		Double valorTotal = 0.00;
		for (CarrinhoProduto p : produtos) {
			valorTotal += p.getPreco();
		}

		return valorTotal;
	}

}
