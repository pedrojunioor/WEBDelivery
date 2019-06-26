package com.ufc.br.repository;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByPessoaOrderByDataPedidoDesc(Pessoa pessoa);

}
