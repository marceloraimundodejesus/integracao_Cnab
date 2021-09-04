package br.com.siscnab.api.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.siscnab.api.model.Cnab;
import br.com.siscnab.api.repository.CnabRepository;

@Service
public class CnabService {
	
	@Autowired
	private CnabRepository cnabRepository;
	
	public List<Cnab> listar() {
		List<Cnab> listaCnab = cnabRepository.findAll();
		return listaCnab;
	}
	
	
	public List<Cnab> lerArquivoCnab(String nomeArquivo) {
		
		try {
			lerArquivo("arquivos/" + nomeArquivo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Cnab> listaCnab = cnabRepository.findAll();
		return listaCnab;
		
		
	}

	public List<Cnab> lerArquivoCnab() {
		
		try {
			lerArquivo("arquivos/cnab.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Cnab> listaCnab = cnabRepository.findAll();
		return listaCnab;
		
		
	}
	
	private void lerArquivo(String arquivo) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(arquivo));
		String linha = "";
		while (true) {
			linha = buffRead.readLine();
			if (linha == null) {
				break;
			}
			Cnab cnab = getCnab(linha);
			inserir(cnab);
		}
		buffRead.close();		
	}
	
	
	
	private Cnab getCnab(String linhaDoArquivo) {
		
		String tipo = linhaDoArquivo.substring(0,1);
		String data = linhaDoArquivo.substring(1,9);
		String valor = linhaDoArquivo.substring(9,19);
		String cpf = linhaDoArquivo.substring(19,30);
		String cartao = linhaDoArquivo.substring(30,42);
		String hora = linhaDoArquivo.substring(42,48);
		String donoLoja = linhaDoArquivo.substring(48,62);
		String nomeLoja = linhaDoArquivo.substring(62,81);		
		
		Cnab cnab = new Cnab();
		cnab.setTipo(Integer.parseInt(tipo));
		
		String dia = data.substring(0,2); 
		String mes = data.substring(2,4); 
		String ano = data.substring(4);
		String dataTransacao = dia + "/" + mes + "/" + ano;
        LocalDate dt = LocalDate.parse(dataTransacao, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		cnab.setData(dt);
		
		cnab.setValor(Double.parseDouble(valor)/100);
		cnab.setCpf(cpf);
		cnab.setCartao(cartao);
		cnab.setHora(hora);
		cnab.setDonoLoja(donoLoja);
		cnab.setNomeLoja(nomeLoja);
		
		return cnab;
	}
	
	private Cnab inserir(Cnab cnab) {
		Cnab cnabSalvo = cnabRepository.save(cnab);
		return cnabSalvo;
	}
	

}
