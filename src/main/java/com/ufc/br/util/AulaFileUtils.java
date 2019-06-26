package com.ufc.br.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class AulaFileUtils {

	public static void salvarImagem(String camingo, MultipartFile imagem) {
		File file = new File(camingo);
		
		try {
			FileUtils.writeByteArrayToFile(file, imagem.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	public static void deleteImagem(String caminho) {

        File file = new File(caminho);
        file.delete();

    }
	
}
