package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
import com.ufc.br.repository.PessoaRepository;
import com.ufc.br.service.PessoaService;
import com.ufc.br.service.PratoServiceImpl;


@Controller
@RequestMapping("/prato")
public class PratoController {
	
	@Autowired
	private PratoServiceImpl pratoService;
	
		
	@RequestMapping("/formularioprato")
	public ModelAndView form(Prato prato) {
		ModelAndView mv = new ModelAndView("FormularioPrato");
		//mv.addObject("prato", new Prato());
		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Prato prato, BindingResult result, @RequestParam(value="imagem") MultipartFile imagem)  {
		ModelAndView mv = new ModelAndView("FormularioPrato");//redirect:/pessoa/listar
		
		if(result.hasErrors()) {
			return form(prato);
		}
		
		mv.addObject("mensagem", "Prato Cadastrada com sucesso");
		pratoService.cadastrar(prato,imagem);
		
		return mv;
}
	@GetMapping("/listar")
	public ModelAndView listar() {
		List<Prato> pratos = pratoService.listarTodos();
		ModelAndView mv = new ModelAndView("Cardapio");
		mv.addObject("listaPratos",pratos);
		return mv;
	}
	
	@RequestMapping("/excluir/{codigo}")
	public ModelAndView deletar(@PathVariable Long codigo) {
		pratoService.excluirPrato(codigo);
		ModelAndView mv = new ModelAndView("redirect:/prato/listar");
		return mv;
	}
	
	@RequestMapping("/atualizar/{codigo}")
	public ModelAndView atualizarPrato(@PathVariable long codigo) {
		Prato prato = pratoService.buscarporId(codigo);
		ModelAndView mv = new ModelAndView("FormularioPrato");
		mv.addObject("prato", prato);
		return mv;
	}
	
	@RequestMapping("/logar")
	public ModelAndView formLogin() {
		ModelAndView mv = new ModelAndView("Login");
		return mv;
	}
}
