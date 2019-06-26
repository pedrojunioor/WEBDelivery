package com.ufc.br.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufc.br.model.ItemSacola;
import com.ufc.br.model.Pedido;
import com.ufc.br.model.Prato;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long>{
	//List<Prato> findByPrato(Prato prato);
}
