package com.ufc.br.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.ItemSacola;
import com.ufc.br.service.ItemSacolaServiceImpl;

@Controller
@RequestMapping(value = "sacola")
public class SacolaController {

	@Autowired
	private ItemSacolaServiceImpl itemPedidoServiceImpl;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		return "sacola/index";
	}

	@RequestMapping(value = "/add/{codigo}", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("codigo") long codigo, HttpSession session) {

		ModelAndView mv = new ModelAndView("redirect:/prato/listar");

		Double total = 0.0;

		if (session.getAttribute("sacola") == null) {

			List<ItemSacola> sacola = new ArrayList<ItemSacola>();
			ItemSacola item = itemPedidoServiceImpl.criaItem(codigo);

			Double precoPrato = item.getPrato().getPreco().doubleValue();
			int qtd = item.getQuantidade();
			Double subtotal = precoPrato * qtd;

			item.setValor(BigDecimal.valueOf(subtotal));

			sacola.add(item);

			total = item.getValor().doubleValue();

			session.setAttribute("sacola", sacola);
			session.setAttribute("total", total);

		} else {

			List<ItemSacola> sacola = (List<ItemSacola>) session.getAttribute("sacola");

			int index = this.exists(codigo, sacola);

			if (index == -1) {

				ItemSacola item = itemPedidoServiceImpl.criaItem(codigo);

				Double precoPrato = item.getPrato().getPreco().doubleValue();
				int qtd = item.getQuantidade();
				Double subtotal = precoPrato * qtd;

				item.setValor(BigDecimal.valueOf(subtotal));

				sacola.add(item);

			} else {

				int quantidade = sacola.get(index).getQuantidade() + 1;
				sacola.get(index).setQuantidade(quantidade);

				Double precoPrato = sacola.get(index).getPrato().getPreco().doubleValue();
				Double subtotal = precoPrato * quantidade;
				sacola.get(index).setValor(BigDecimal.valueOf(subtotal));

			}

			for (ItemSacola item : sacola) {
				total += item.getQuantidade() * item.getPrato().getPreco().doubleValue();
			}

			session.setAttribute("sacola", sacola);
			session.setAttribute("total", total);

		}

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
        return "redirect:/sacola/index";
    }

	private int exists(long codigo, List<ItemSacola> sacola) {

		for (int i = 0; i < sacola.size(); i++) {

			if (sacola.get(i).getPrato().getCodigo() == codigo) {
				return i;
			}
		}

		return -1;

	}

}