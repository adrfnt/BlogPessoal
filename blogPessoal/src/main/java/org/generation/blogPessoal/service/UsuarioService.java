package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.model.UsuarioLogin;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service 
public class UsuarioService { //Aplicar regra de negócio nessa camada

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		return repository.save(usuario);
	}
	
	public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> usuarioLogin){ 
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); ////Comparar o usuário que foi informado no login com o salvo no Banco de Dados, por isso o nome usuarioLogin e usuarioBD - SEMPRE USAR camelCase!
		Optional<Usuario> usuarioBD = repository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(usuarioBD.isPresent()) {
			if(encoder.matches(usuarioLogin.get().getSenha(), usuarioBD.get().getSenha())) { //matches: Verifica se a senha codificada obtida do armazenamento corresponde à senha enviada depois que ela também for codificada. Retorna verdadeiro se as senhas corresponderem, falso se não corresponderem. A senha armazenada em si NUNCA é decodificada.
				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha(); 
				byte[] /*tokenBase64 =*/encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII"))); //Lê a String, transforma em bytes e o enconde gera o token no formato Base64
				String /*tokenCompleto =*/authHeader = "Basic"+ new String(encodedAuth); //String codificada em base64
				
				usuarioLogin.get().setToken(authHeader);
				usuarioLogin.get().setNome(usuarioBD.get().getNome());
				usuarioLogin.get().setSenha(usuarioBD.get().getSenha()); //Não tem essa parte no vídeo
				
				return usuarioLogin;
			}
		}
		return null;
	}
}