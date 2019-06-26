package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.ufc.br.model.ItemSacola;
import com.ufc.br.model.Pedido;
import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Role;
import com.ufc.br.repository.PessoaRepository;
import com.ufc.br.service.ItemSacolaServiceImpl;
import com.ufc.br.service.PedidoServiceImpl;
import com.ufc.br.service.PessoaService;


@Controller
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
    @Autowired
    private PedidoServiceImpl pedidoService;
    
    @Autowired
    private ItemSacolaServiceImpl itemPedidoService;
	
	@RequestMapping("/formulario")
	public ModelAndView form(Pessoa pessoa) {
		ModelAndView mv = new ModelAndView("Formulario");
		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Pessoa pessoa, BindingResult result)  {
		ModelAndView mv = new ModelAndView("Formulario");//redirect:/pessoa/listar
		
		if(result.hasErrors()) {
			return form(pessoa);
		}
		
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setRolePessoa("ROLE_USER");
		roles.add(role);
		pessoa.setRoles(roles);
		
		mv.addObject("mensagem", "Pessoa Cadastrada com sucesso");
		pessoaService.cadastrar(pessoa);
		
		return mv;
}
	@GetMapping("/listar")
	public ModelAndView listar() {
		List<Pessoa> pessoas = pessoaService.listarTodos();
		ModelAndView mv = new ModelAndView("PaginaListagem");
		mv.addObject("listaPessoas",pessoas);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{codigo}")
	public ModelAndView deletar(@PathVariable Long codigo) {
		pessoaService.excluirPessoa(codigo);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/listar");
		return mv;
	}
	
	@RequestMapping("/atualizar/{codigo}")
	public ModelAndView atualizarPessoa(@PathVariable long codigo) {
		Pessoa pessoa = pessoaService.buscarporId(codigo);
		ModelAndView mv = new ModelAndView("Formulario");
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	
	@RequestMapping("/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("Login");
		return mv;
	}


	
    @RequestMapping(value = "/pedidosPessoa")
    public ModelAndView listarPedidosDaPessoa() {
    	   	
        ModelAndView mv = new ModelAndView("list-pedidos");

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Pessoa pessoa = pessoaService.buscarPorLogin(user.getUsername());

        List<Pedido> pedidosPessoa = pedidoService.listarPorPessoa(pessoa);

        for(Pedido p : pedidosPessoa){

            List<ItemSacola> itens = new ArrayList<>();

            itens = itemPedidoService.listarItensPorPedido(p);
            p.setItens(itens);

        }
        mv.addObject("pedidosPessoa", pedidosPessoa);
        return mv;
    }
    
}
