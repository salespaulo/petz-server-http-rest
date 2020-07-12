package com.petz.api.auth.jwt.spring;

import static com.petz.api.core.exception.Exceptions.supplierJwtTokenMissing;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.petz.api.auth.jwt.TokenJwtRaw;
import com.petz.api.auth.token.TokenStringExtractor;

class AuthJwtFilter extends AbstractAuthenticationProcessingFilter {

	public static final String URL_BASE = "/api/**";
	public static final String URL_AUTH = "/auth/**";

	private final AuthenticationFailureHandler failureHandler;
	private final TokenStringExtractor tokenExtractor;

	@Autowired
	public AuthJwtFilter(final TokenStringExtractor tokenExtractor, final AuthenticationFailureHandler failureHandler) {
		super(new AntPathRequestMatcher(URL_BASE));

		this.failureHandler = failureHandler;
		this.tokenExtractor = tokenExtractor;
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		final String xheader = request.getHeader(AuthJwtConfiguration.AUTHORIZATION_HEADER);
		final Optional<String> securityHeader = Optional.ofNullable(xheader);

		return getAuthenticationManager().authenticate(securityHeader
				.map(tokenExtractor::extract)
				.map(TokenJwtRaw::new)
				.map(AuthJwtResource::new)
				.orElseThrow(supplierJwtTokenMissing()));
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, 
			final FilterChain chain, final Authentication authResult) throws IOException, ServletException {

		final SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		SecurityContextHolder.clearContext();
		failureHandler.onAuthenticationFailure(request, response, failed);
	}
}