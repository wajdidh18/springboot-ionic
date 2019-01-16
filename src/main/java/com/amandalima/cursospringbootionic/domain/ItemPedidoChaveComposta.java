package com.amandalima.cursospringbootionic.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable  //essa annotation informa que essa classe é um subtipo
public class ItemPedidoChaveComposta implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Essa classe é auxiliar, que fará a ligação entre Pedido e Produto.
	 * Foi criada a classe ItemPedido, mas como ela é uma classe de associação, não tem id próprio.
	 * Quam identifica ela, serão os ids associados a ela (no caso, id do produto e id do pedido)
	 * 
	 * A forma mais geral de implementar essa associação, é criar uma chave composta da classe ItemPedido
	 * Para isso, foi criada essa classe auxiliar, que será a chave composta de produto e pedido
	 * 
	 * Por fim, nessa classe haverá a referência para pedido e para produto, que 
	 * 
	 * Essa classe, obrigatoriamente, precisa ter a implementação do Serializable, get/set e hashCode/equals, 
	 * com as duas referências (pedido e produto)
	 */
	
	@ManyToOne
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	private Produto produto;
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedidoChaveComposta other = (ItemPedidoChaveComposta) obj;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}
	
	
}
