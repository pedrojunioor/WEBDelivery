package com.ufc.br.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Prato {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;
	@NotBlank(message = "Campo nome obrigatorio")
	private String nome;
	@NotNull(message = "Campo time obrigatorio")
	private BigDecimal preco;
	
    @OneToMany(mappedBy = "prato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemSacola> itens;

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public Prato() {
		
	}
	
	public Prato(Long codigo, @NotBlank(message = "Campo nome obrigatorio") String nome,
			@NotNull(message = "Campo time obrigatorio") BigDecimal preco) {
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
	}
	
	
	
}
