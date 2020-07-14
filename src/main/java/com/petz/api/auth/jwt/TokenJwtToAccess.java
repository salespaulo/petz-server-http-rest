package com.petz.api.auth.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petz.api.auth.token.TokenResource;

import io.jsonwebtoken.Claims;

public final class TokenJwtToAccess implements TokenResource {

	private final String rawToken;

	@JsonIgnore
	private final Claims claims;

	protected TokenJwtToAccess(final String token, final Claims claims) {
		this.rawToken = token;
		this.claims = claims;
	}

	public String getToken() {
		return this.rawToken;
	}

	public Claims getClaims() {
		return this.claims;
	}
}