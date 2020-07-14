package com.petz.api.core.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {

	private static final String JWT_TOKEN_MISSING = "Jwt Token Missing.";

	private static final long serialVersionUID = 6896955983482502090L;

	public JwtTokenMissingException() {
		super(JWT_TOKEN_MISSING);
	}

}
