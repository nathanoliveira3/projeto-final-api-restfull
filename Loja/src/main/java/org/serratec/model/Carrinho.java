package org.serratec.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Cliente cliente;

	private String codigo;

	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String gerarProtocolo() {
		if (this.codigo == null || this.codigo.isBlank()) {

			LocalDateTime agora = LocalDateTime.now();
			Random randomico = new Random();
			String codigo = "v";
			codigo += agora.getYear();
			codigo += agora.getMonth();
			codigo += agora.getDayOfMonth();
			codigo += agora.getHour();
			codigo += agora.getMinute();
			codigo += agora.getSecond();

			for (int i = 0; i < 10; i++) {
				codigo += randomico.nextInt(10);
			}
			this.codigo = codigo;
		}
		return this.codigo;
	}
	
	public Double getValorTotal() {
		Double valor = 0.00;
		for(CarrinhoProduto p : produtos) {
			valor += (p.getPreco() * p.getQuantidade());
		}
		return valor;		
	}
}
