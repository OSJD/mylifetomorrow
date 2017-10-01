package com.hg.mylifetomorrow.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username,password, enabled from HG_USER where username=?")
		.authoritiesByUsernameQuery(
			"select username, role from HG_USER_ROLES where username=?");
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  
	  http
	  	.httpBasic()
	  	.and()
	  		.authorizeRequests()
	  		.antMatchers("/home","/userHome","/dependents","/loggedInUser","/profile/**").authenticated()
	  		.antMatchers("/registerUser","/authentication","/forgotpassword/**").permitAll()
	  		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
	  	.and()
	  		.logout().permitAll()
	  	.and().csrf()
	  		.disable();
	}

}

