package com.petz.api.auth.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.Claims;

public final class AccessJwtToken implements JwtToken {

	private final String rawToken;

	@JsonIgnore
	private final Claims claims;

	protected AccessJwtToken(final String token, final Claims claims) {
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