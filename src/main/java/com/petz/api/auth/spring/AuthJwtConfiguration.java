package com.petz.api.auth.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.petz.api.auth.token.TokenStringExtractor;

@Configuration
public class AuthJwtConfiguration extends WebSecurityConfigurerAdapter {

	public static final String AUTHORIZATION_HEADER = "X-Authorization";

	@Autowired
	private AuthJwtProvider jwtAuthenticationProvider;

	@Autowired
	private UnauthorizedEntryPoint authenticationEntryPoint;

	@Autowired
	private AuthFailureHandler failureHandler;

	@Autowired
	private TokenStringExtractor tokenExtractor;

	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthJwtFilter buildJwtTokenAuthFilter() throws Exception {
		final AuthJwtFilter filter = new AuthJwtFilter(tokenExtractor, failureHandler);
		filter.setAuthenticationManager(this.authenticationManagerBean());
		return filter;

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() 
				.exceptionHandling()
				.authenticationEntryPoint(this.authenticationEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(AuthJwtFilter.URL_AUTH).permitAll()
				.antMatchers(AuthJwtFilter.URL_BASE).authenticated()
				.and()
				.addFilterBefore(buildJwtTokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
