package com.amandalima.cursospringbootionic.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.amandalima.cursospringbootionic.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PagamentoComBoleto extends Pagamento{
	/*
	 * não precisa colocar o implements do Serializable e o hashCode e Equals nas subclasses, pq a classe pai já tem
	 * nas subclasses coloca apenas o serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataVencimento;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
		super();
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}


}
