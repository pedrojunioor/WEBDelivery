package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.util.AulaFileUtils;

@Service
public class PratoServiceImpl {

	@Autowired
	private PratoRepository pratoRepo;
	private List<Prato> pratos;
		
	public void cadastrar(Prato prato, MultipartFile imagem) {
		String camingo = "images/" + prato.getNome() + ".png";
		AulaFileUtils.salvarImagem(camingo,imagem);
		pratoRepo.save(prato);
	}

	public List<Prato> listarTodos(){
		return pratoRepo.findAll();
	}
	
	public void excluirPrato(Long codigo) {
		pratoRepo.deleteById(codigo);
	}
	
	public Prato buscarporId(long codigo) {
		return pratoRepo.getOne(codigo);
	}
	
	public List<Prato> buscarPratos(){
		List<Prato> result = new ArrayList<Prato>();
		for (Prato prato : pratos) {
			result.add(prato);
		}
		return result;
	}
	
    public Prato buscarPorCodigo(Long codigo) {
        return pratoRepo.getOne(codigo);
    }
    
    public void remover(Long codigo) {

        Prato prato = pratoRepo.getOne(codigo);

        String caminho = "images/" + prato.getCodigo() + ".png";
        AulaFileUtils.deleteImagem(caminho);

        pratoRepo.save(prato);
    }
	
}
