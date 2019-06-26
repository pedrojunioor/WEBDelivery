package com.ufc.br.service;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Pedido;
import com.ufc.br.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl {

    @Autowired
    private PedidoRepository pedidoRepository;


    public void cadastrar(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

 
    public List<Pedido> listarPorPessoa(Pessoa pessoa) {
        return pedidoRepository.findByPessoaOrderByDataPedidoDesc(pessoa);
    }

    
    public List<Pedido> listarPedidos() {
    	return pedidoRepository.findAll();
    }

/*
    public List<Pedido> listarEnviados() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<Pedido> pedidosEnviados = new ArrayList<Pedido>();

        for(int i = 0; i < pedidos.size(); i++) {

            if(pedidos.get(i).getStatus() == 1) {
                pedidosEnviados.add(pedidos.get(i));
            }

        }

        return pedidosEnviados;
    }
    */
  
    public Pedido buscarPorCodigo(Long codigo) {

        return pedidoRepository.getOne(codigo);
    }


}
