package org.generation.blogPessoal.repository;


import static org.junit.jupiter.api.Assertions.*;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositorioTest {

	@Autowired
	private UsuarioRepository repositorio;
	
	@BeforeAll
	void start() {
		Usuario usuario = new Usuario("Yukito", "134652");
		if(repositorio.findByUsuario(usuario.getUsuario())!=null)
			repositorio.save(usuario);
		
		usuario = new Usuario("Kero", "134652");
		if(repositorio.findByUsuario(usuario.getUsuario())!=null)
			repositorio.save(usuario);
		
		usuario = new Usuario("Touya", "134652");
		if(repositorio.findByUsuario(usuario.getUsuario())!=null)
			repositorio.save(usuario);
	}
	
	@Test
	public void findByUsuarioRetornaUsuario() throws Exception {

		Usuario usuario = repositorio.findByUsuario("Yukito").get();
		assertTrue(usuario.getUsuario().equals("Yukito"));
	}
	
	@AfterAll
	public void end() {
		repositorio.deleteAll();
	}

}