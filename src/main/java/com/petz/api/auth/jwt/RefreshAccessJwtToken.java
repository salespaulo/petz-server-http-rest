package com.petz.api.auth.jwt;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public final class RefreshAccessJwtToken implements JwtToken {

	public static enum Privilege {
	    ROLE_REFRESH_TOKEN;
	}
	
	private Jws<Claims> claims;

	private RefreshAccessJwtToken(final Jws<Claims> claims) {
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
	public static Optional<RefreshAccessJwtToken> of(final RawJwtToken token, final String signingKey) {
		final Jws<Claims> claims = token.parse(signingKey);

		@SuppressWarnings("unchecked")
		final List<String> privileges = claims.getBody().get("privileges", List.class);

		if (privileges == null || privileges.isEmpty() || !inRefreshScopes(privileges)) {
			return Optional.empty();
		}

		return Optional.of(new RefreshAccessJwtToken(claims));
	}

	private static boolean inRefreshScopes(final List<String> scopes) {
		return scopes.contains(Privilege.ROLE_REFRESH_TOKEN.toString());
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