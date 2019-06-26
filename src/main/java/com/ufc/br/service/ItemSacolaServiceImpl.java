package com.ufc.br.service;

import com.ufc.br.model.ItemSacola;
import com.ufc.br.model.Pedido;
import com.ufc.br.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemSacolaServiceImpl{

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PratoServiceImpl pratoServiceImpl;

 
    public void salvar(Iterable<ItemSacola> itens) {

        itemPedidoRepository.saveAll(itens);
    }

    
    public List<ItemSacola> listarItensPorPedido(Pedido pedido) {
        List<ItemSacola> itens = itemPedidoRepository.findByPedido(pedido);
        return itens;
    }

    public ItemSacola criaItem(Long codigoPrato) {

        ItemSacola item = new ItemSacola();
        item.setPrato(pratoServiceImpl.buscarPorCodigo(codigoPrato));
        item.setQuantidade(1);
        return item;
    }

}
