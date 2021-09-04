package br.com.siscnab.api.controller;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.siscnab.api.model.Cnab;
import br.com.siscnab.api.service.CnabService;

@RestController
public class CnabController {

	@Autowired
	private CnabService cnabService;

	@GetMapping("/listar")
	public List<Cnab> listar() {
		return cnabService.listar();
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> upload(@RequestParam("arquivo") MultipartFile arquivo) {
		
		String nomeArquivo = arquivo.getOriginalFilename();

		Path arquivoCnab = Path.of("/home/fabricio/Documents/workspace-spring-tool-suite-4-4.9.0.RELEASE/SistemaCnab/arquivos", nomeArquivo);

		try {
			arquivo.transferTo(arquivoCnab);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		List<Cnab> listaCnab = cnabService.lerArquivoCnab(nomeArquivo);
		if(listaCnab.size() > 0) {
			System.out.println("Dados processados");
			return new ResponseEntity<String>("Arquivo foi processado", HttpStatus.CREATED);
		}
		System.out.println("Dados não foram processados");
		return new ResponseEntity<String>("Arquivo não foi processado", HttpStatus.OK);

	}

}
