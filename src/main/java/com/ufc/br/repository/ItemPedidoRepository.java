package com.ufc.br.repository;

import com.ufc.br.model.ItemSacola;
import com.ufc.br.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemSacola, Long> {

    List<ItemSacola> findByPedido(Pedido pedido);

}
