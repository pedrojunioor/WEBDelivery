package com.ufc.br.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ufc.br.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImplementacao userDetaisServiceImple;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		
		//hasRole("USER")
		
		.antMatchers("/index").permitAll()
		.antMatchers("/contato").permitAll()
		.antMatchers("/sobre").permitAll()
		.antMatchers("/pessoa/formulario").permitAll()
		.antMatchers("/pessoa/salvar").permitAll()
		.antMatchers("/pessoa/listar").hasRole("ADMIN")
		.antMatchers("/prato/listar").permitAll()
		
		.anyRequest().authenticated()
		
		.and()
		.formLogin()
		.loginPage("/pessoa/logar").defaultSuccessUrl("/index").permitAll()
		.permitAll()

		.and()
		.logout()
		.logoutSuccessUrl("/index?logout")
		
		.permitAll();
	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetaisServiceImple).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/js/**","/img/**", "/images/**");
	}


}