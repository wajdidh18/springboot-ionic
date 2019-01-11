package com.amandalima.cursospringbootionic.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="categorias")
public class CategoriaResource {
	
	@GetMapping   //ou então @RequestMapping(method=RequestMethod.GET)
	public String testeRest() {
		return "REST está funcionando";
	}

}
