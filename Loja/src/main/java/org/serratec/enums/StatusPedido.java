package org.serratec.enums;

public enum StatusPedido {

	AGUARDANDO_PAGAMENTO(1, "Pagamento em processamento"), PAGO(2, "Pagamento Aprovado"),
	ENVIADO(3, "Produto enviado para o cliente"), FINALIZADO(4, "Pedido finalizado");

	private final Integer status;
	private final String descricao;

	private StatusPedido(Integer status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDescricao() {
		return descricao;
	}

}
