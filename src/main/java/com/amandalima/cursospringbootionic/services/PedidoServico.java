package com.amandalima.cursospringbootionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amandalima.cursospringbootionic.domain.Pedido;
import com.amandalima.cursospringbootionic.repositories.PedidoRepository;
import com.amandalima.cursospringbootionic.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoServico {

	@Autowired
	PedidoRepository pedidoRepository;
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()
		));
	}
}
