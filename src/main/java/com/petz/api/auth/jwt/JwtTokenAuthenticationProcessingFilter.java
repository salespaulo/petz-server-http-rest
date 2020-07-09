package com.petz.api.auth.jwt;

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

import com.petz.api.auth.AuthorizationConfig;
import com.petz.api.auth.SecurityRequestMatcher;
import com.petz.api.auth.resource.AuthenticationTokenResource;
import com.petz.api.auth.token.TokenExtractor;

public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private final AuthenticationFailureHandler failureHandler;
	private final TokenExtractor tokenExtractor;

	@Autowired
	public JwtTokenAuthenticationProcessingFilter(final SecurityRequestMatcher matcher, 
			final TokenExtractor tokenExtractor, final AuthenticationFailureHandler failureHandler) {
		super(matcher);
		this.failureHandler = failureHandler;
		this.tokenExtractor = tokenExtractor;
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		final String securityHeader = request.getHeader(AuthorizationConfig.AUTHORIZATION_HEADER);

		return getAuthenticationManager().authenticate(Optional.ofNullable(securityHeader)
				.map(tokenExtractor::extract)
				.map(RawJwtToken::new)
				.map(AuthenticationTokenResource::new)
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