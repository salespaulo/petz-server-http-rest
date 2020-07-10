package com.petz.api.core.exception;

import java.io.Serializable;
import java.util.function.Supplier;

import org.springframework.security.authentication.BadCredentialsException;

import com.petz.api.auth.jwt.JwtToken;
import com.petz.api.auth.jwt.RawJwtToken;
import com.petz.api.user.domain.User;

import io.jsonwebtoken.ExpiredJwtException;

public final class Exceptions {
	
	private Exceptions() {
		throw new AssertionError();
	}

	public static Supplier<ResourceNotFoundException> supplierUserNotFound(final Serializable id) {
		return supplierResourceNotFound(User.class.getSimpleName(), id);
	}
	
	public static Supplier<ResourceNotFoundException> supplierResourceNotFound(final String name, final Serializable id) {
		return ResourceNotFoundException.supplier(name, id);
	}

	public static Supplier<UsernamePasswordAuthenticationException> supplierUsernamePasswordInvalid() {
		return () -> new UsernamePasswordAuthenticationException();
	}
	
	public static Supplier<JwtTokenMissingException> supplierJwtTokenMissing() {
		return () -> new JwtTokenMissingException();
	}

	public static Supplier<JwtTokenInvalidException> supplierJwtTokenInvalid(final JwtToken refreshToken) {
		return () -> new JwtTokenInvalidException(refreshToken);
	}

	public static BadCredentialsException newBadCredentials(final Throwable cause) {
		return new BadCredentialsException("Bad Credentials Authorization.", cause);
	}

	public static JwtTokenExpiredException newJwtTokenExpired(final RawJwtToken token, final ExpiredJwtException cause) {
		return new JwtTokenExpiredException(token, cause);
	}

}
