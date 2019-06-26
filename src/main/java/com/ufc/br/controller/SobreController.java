package com.ufc.br.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SobreController {
	
	@RequestMapping("/sobre")
	public String olaMundoSobre() {
		return "Sobre";
	}
}
