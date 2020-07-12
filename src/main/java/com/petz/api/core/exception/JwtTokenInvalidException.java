package com.petz.api.core.exception;

import com.petz.api.auth.token.TokenResource;

public class JwtTokenInvalidException extends AuthJwtTokenException {

	private static final String JWT_TOKEN_INVALID = "Jwt Token Invalid.";

	private static final long serialVersionUID = -294671188037098603L;

    public JwtTokenInvalidException(final TokenResource token) {
		super(token, JWT_TOKEN_INVALID);
	}
    
}