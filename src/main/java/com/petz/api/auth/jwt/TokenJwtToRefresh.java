package com.petz.api.auth.jwt;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;

import com.petz.api.auth.token.TokenResource;
import com.petz.api.core.exception.JwtTokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public final class TokenJwtToRefresh implements TokenResource {

	public static enum Privilege {
	    PROFILE_GET;
	}
	
	private Jws<Claims> claims;

	private TokenJwtToRefresh(final Jws<Claims> claims) {
		this.claims = claims;
	}

	/**
	 * Creates and validates Refresh token
	 * 
	 * @param token
	 * @param signingKey
	 * 
	 * @throws BadCredentialsException
	 * @throws JwtTokenExpiredException
	 * 
	 * @return
	 */
	public static Optional<TokenJwtToRefresh> of(final TokenJwtRaw token, final String signingKey) {
		final Jws<Claims> claims = token.parse(signingKey);

		@SuppressWarnings("unchecked")
		final List<String> privileges = claims.getBody().get("privileges", List.class);

		if (privileges == null || privileges.isEmpty() || !inRefreshScopes(privileges)) {
			return Optional.empty();
		}

		return Optional.of(new TokenJwtToRefresh(claims));
	}

	private static boolean inRefreshScopes(final List<String> scopes) {
		return scopes.contains(Privilege.PROFILE_GET.toString());
	}

	@Override
	public String getToken() {
		// To refresh don't return token
		return null;
	}

	public Jws<Claims> getClaims() {
		return claims;
	}

	public String getJti() {
		return claims.getBody().getId();
	}

	public String getSubject() {
		return claims.getBody().getSubject();
	}

}