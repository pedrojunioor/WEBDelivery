package com.ufc.br.model;


import com.ufc.br.model.Pessoa;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String rolePessoa;
	
	@ManyToMany(mappedBy = "roles")
	private List<Pessoa> pessoas;
	
	
    public String getRolePessoa() {
		return rolePessoa;
	}

	public void setRolePessoa(String rolePessoa) {
		this.rolePessoa = rolePessoa;
	}

	public List<Pessoa> getPessoa() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.rolePessoa;
	}
	
}