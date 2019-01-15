package com.amandalima.cursospringbootionic.domain;

import javax.persistence.Entity;

import com.amandalima.cursospringbootionic.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento{
	/*
	 * não precisa colocar o implements do Serializable e o hashCode e Equals nas subclasses, pq a classe pai já tem
	 * nas subclasses coloca apenas o serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	public PagamentoComCartao() {
		super();
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
	
	
}
