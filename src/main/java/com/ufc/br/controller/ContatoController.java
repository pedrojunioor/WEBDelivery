package com.ufc.br.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContatoController {
	
	@RequestMapping("/contato")
	public String olaMundoContato() {
		return "Contato";
	}
}
