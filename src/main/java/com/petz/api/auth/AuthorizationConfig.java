package com.petz.api.auth;

import static com.petz.api.auth.AuthenticationEndpoint.LOGIN_PATH_ENDPOINT;
import static com.petz.api.auth.AuthenticationEndpoint.REFRESH_PATH_ENDPOINT;

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

import com.petz.api.auth.jwt.JwtAuthenticationProvider;
import com.petz.api.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.petz.api.auth.token.TokenExtractor;

@Configuration
public class AuthorizationConfig extends WebSecurityConfigurerAdapter {

	public static final String AUTHORIZATION_HEADER = "X-Authorization";

	public static final String TOKEN_PROTECTED_ENDPOINTS = "/api/**/";

	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private RestAuthenticationFailureHandler failureHandler;

	@Autowired
	private TokenExtractor tokenExtractor;

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
	public JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthFilter() throws Exception {
		final SecurityRequestMatcher matcher = new SecurityRequestMatcher(TOKEN_PROTECTED_ENDPOINTS,
				LOGIN_PATH_ENDPOINT);

		final JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(matcher,
				tokenExtractor, failureHandler);

		filter.setAuthenticationManager(this.authenticationManagerBean());

		return filter;

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // We don't need CSRF for JWT based authentication
				.exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(LOGIN_PATH_ENDPOINT).permitAll().antMatchers(REFRESH_PATH_ENDPOINT).permitAll().and()
				.authorizeRequests().antMatchers(TOKEN_PROTECTED_ENDPOINTS).authenticated().and()
				.addFilterBefore(buildJwtTokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
