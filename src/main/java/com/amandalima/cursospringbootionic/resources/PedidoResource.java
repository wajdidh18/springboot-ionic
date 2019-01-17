package com.amandalima.cursospringbootionic.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amandalima.cursospringbootionic.domain.Pedido;
import com.amandalima.cursospringbootionic.services.PedidoServico;

@RestController
@RequestMapping(value="pedidos")
public class PedidoResource {

	@Autowired
	PedidoServico pedidoServico;
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> findById(@PathVariable Integer id) {
		Pedido obj = pedidoServico.findById(id);
		return ResponseEntity.ok(obj);
	}
}
