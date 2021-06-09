package org.generation.blogPessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean // Indica p/o spring onde localizar um método que é componente do spring, neste caso, o PasswordEncoder
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll() //Ex: usuarios/login
		.antMatchers("/usuarios/cadastrar").permitAll() // .antMatchers(HttpMethod.Post,"/usuarios" - Não colocar verbos na url, segundo boas práticas Ex:(/usuarios/cadastro)
		.anyRequest().authenticated() //precisa de autenticação
		.and().httpBasic()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Não precisa guardar essa sessão
		.and().cors() //gerencia o acesso externo (outros servidores) a API, através do CrossOrigin (Lá é possivel informar qual servidor terá acesso). 
		.and().csrf().disable();  //sigla p/ ataque hacker através de usuário comum. Neste caso, será acessado por outro sistema (front), não precisa ativá-lo e fazer configurações.
	}
}