package org.generation.blogPessoal.repository;

import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	public Optional<Usuario> findByUsuario(String usuario); //optional pode ou não vir preenchido, um espaço p/2 respostas, uma com informação (preenchida) e uma nula (não preenchida), sendo necessário if/else ou lambda p/ utilizar.
}
