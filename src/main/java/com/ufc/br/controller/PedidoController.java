package com.ufc.br.controller;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.ItemSacola;
import com.ufc.br.model.Pedido;
import com.ufc.br.service.PessoaService;
import com.ufc.br.service.ItemSacolaServiceImpl;
import com.ufc.br.service.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoServiceImpl pedidoServiceImpl;

    @Autowired
    private PessoaService pessoaServiceImpl;

    @Autowired
    private ItemSacolaServiceImpl itemPedidoServiceImpl;

    @RequestMapping(value = "/finalizar", method = RequestMethod.GET)
    public ModelAndView finalizar(HttpSession session) {

        //ModelAndView mv = new ModelAndView("redirect:/cliente/pedidosCliente");
        ModelAndView mv = new ModelAndView("redirect:/index");

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Pessoa pessoa = pessoaServiceImpl.buscarPorLogin(user.getUsername());

       // List<Endereco> enderecosCliente = enderecoServiceImpl.listarTodosPorCliente(cliente.getCodigo());

        Pedido pedido = new Pedido();
        pedido.setPessoa(pessoa);

        /*
        for(Endereco endereco : enderecosCliente){
            if(endereco.getStatus() == 1){
                pedido.setEnderecoDeEntrega(endereco);
            }
        }

         */
        
        pedido.setTotal(BigDecimal.valueOf(0));
        pedidoServiceImpl.cadastrar(pedido);

        Iterable<ItemSacola> sacola = (Iterable<ItemSacola>) session.getAttribute("sacola");

        Double total = 0.0;

        for(ItemSacola item : sacola) {
            item.setPedido(pedido);
            total += item.getValor().doubleValue();
        }

        itemPedidoServiceImpl.salvar(sacola);

        total = (Double) session.getAttribute("total");

        pedido.setTotal(BigDecimal.valueOf(total));

        pedidoServiceImpl.cadastrar(pedido);

        session.removeAttribute("sacola");
        session.removeAttribute("total");

        return mv;
    }

    /*
    @RequestMapping(value = "/pedir")
    public ModelAndView pedirProduto(){

        //ModelAndView mv = new ModelAndView("sacola/endereco-entrega");
        ModelAndView mv = new ModelAndView("sacola/tela-pedido");
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Pessoa pessoa = pessoaServiceImpl.buscarPorLogin(user.getUsername());

        //mv.addObject("enderecosCliente",enderecoServiceImpl.listarTodosPorCliente(cliente.getCodigo()));

        mv.addObject("pessoa",pessoa);
        return mv;

    }
     */
    /*
    @RequestMapping(value = "/pedir", method = RequestMethod.POST)
    public ModelAndView selecionarEnderecoAtualDoCliente(@RequestParam(value="id") long id) {

        ModelAndView mv = new ModelAndView("redirect:/pedido/telaPedido");

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Pessoa pessoa = pessoaServiceImpl.buscarPorLogin(user.getUsername());


       
        List<Endereco> enderecosDoCliente = enderecoServiceImpl.listarTodosPorCliente(cliente.getCodigo());

        Endereco endereco = enderecoServiceImpl.buscarPorId(id);

        for (Endereco end : enderecosDoCliente) {

            if (end.getId() == endereco.getId()) {
                enderecoServiceImpl.salvar(end, cliente.getCodigo(), 1);
            } else {
                enderecoServiceImpl.salvar(end, cliente.getCodigo(), 0);
            }
        }
         
        return mv;
    }
    */
    
    @RequestMapping(value = "/telaPedido", method = RequestMethod.GET)
    public ModelAndView telaDoPedido(HttpSession session){

        ModelAndView mv = new ModelAndView("sacola/tela-pedido");

        session.getAttribute("sacola");

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Pessoa pessoa = pessoaServiceImpl.buscarPorLogin(user.getUsername());

        //Endereco endereco = enderecoServiceImpl.buscarPorClienteStatus(cliente,1);

       // mv.addObject("endereco", endereco);

        return mv;

    }
    

    @RequestMapping(value = "/telaPedido", method = RequestMethod.POST)
    public ModelAndView atualizar(HttpSession session, @RequestParam(value="qtdItem") int qtdItem, @RequestParam(value="codigo") long codigo){

        ModelAndView mv = new ModelAndView("redirect:/pedido/telaPedido");

        List<ItemSacola> sacola = (List<ItemSacola>) session.getAttribute("sacola");

        int index = this.exists(codigo,sacola);

        sacola.get(index).setQuantidade(qtdItem);

        session.setAttribute("total",sumTotal(session));
        session.setAttribute("sacola", sacola);

        return mv;

    }

    @RequestMapping(value = "/remove/{codigo}", method = RequestMethod.GET)
    public String remove(@PathVariable("codigo") long codigo, HttpSession session) {

        Double total = 0.0;

        List<ItemSacola> sacola = (List<ItemSacola>) session.getAttribute("sacola");
        int index = this.exists(codigo, sacola);

        if(sacola.get(index).getQuantidade() > 1){
            int qtd = sacola.get(index).getQuantidade();
            sacola.get(index).setQuantidade(qtd-1);

        }else {
            sacola.remove(index);
        }

        for(ItemSacola item : sacola) {
            total += item.getQuantidade() * item.getPrato().getPreco().doubleValue();
        }

        session.setAttribute("sacola", sacola);
        session.setAttribute("total", total);
        return "redirect:/pedido/telaPedido";
    }

    private int exists(long codigo, List<ItemSacola> sacola) {

        for(int i = 0; i < sacola.size(); i++) {

            if(sacola.get(i).getPrato().getCodigo() == codigo) {
                return i;
            }
        }

        return -1;

    }

    private double sumTotal(HttpSession session){

        List<ItemSacola> sacola = (List<ItemSacola>) session.getAttribute("sacola");
        double s = 0;

        for(ItemSacola item : sacola){
            s += item.getQuantidade() * item.getPrato().getPreco().doubleValue();
        }

        return s;

    }
	
}
