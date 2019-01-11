package com.amandalima.cursospringbootionic.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amandalima.cursospringbootionic.domain.Categoria;

@RestController
@RequestMapping(value="categorias")
public class CategoriaResource {
	
	@GetMapping
	public List<Categoria> listar() {
		
		Categoria cat = new Categoria(1, "Informática");
		Categoria cat2 = new Categoria(1, "Escritório");
		
		List<Categoria> lista = new ArrayList<>();
		lista.add(cat);
		lista.add(cat2);
		
		return lista;
	}

}
