package com.petz.api.core.exception;

import org.springframework.security.core.AuthenticationException;

import com.petz.api.auth.token.TokenResource;

public class JwtTokenAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 3018461054095289405L;

	private final TokenResource token;
	
	protected JwtTokenAuthenticationException(TokenResource token, String msg) {
		super(msg);
		this.token = token;
	}
	
	protected JwtTokenAuthenticationException(TokenResource token, String msg, Throwable cause) {
		super(msg, cause);
		this.token = token;
	}
	
	public TokenResource getToken() {
		return token;
	}
	
}
