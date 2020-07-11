package com.petz.api.core.exception;

import com.petz.api.auth.token.TokenResource;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtTokenExpiredException extends JwtTokenAuthenticationException {

    private static final String JWT_TOKEN_EXPIRED = "JWT Token expired";

	private static final long serialVersionUID = -5959543783324224864L;
    
    public JwtTokenExpiredException(final TokenResource token, final ExpiredJwtException cause) {
        super(token, JWT_TOKEN_EXPIRED, cause);
    }

}