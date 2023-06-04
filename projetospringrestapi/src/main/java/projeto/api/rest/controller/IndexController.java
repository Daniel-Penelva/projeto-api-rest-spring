package projeto.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* Arquitetura RestFull */
@RestController
@RequestMapping(value = "/usuario")
public class IndexController {

	/* Serviço RestFull */
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity init(@RequestParam(value = "nome", required = true, defaultValue = "com nome não informado") String nome) {
		
		System.out.println("Parâmetro sendo recebido " + nome);
		return new ResponseEntity("Olá usuário " + nome + " - Bem vindo ao Rest Spring Boot!", HttpStatus.OK);
	}

}
