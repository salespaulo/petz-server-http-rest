package com.petz.api.auth.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.petz.api.auth.jwt.JwtSettings;
import com.petz.api.auth.jwt.TokenJwtRaw;
import com.petz.api.auth.resource.LoggedInResource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class AuthJwtProvider implements AuthenticationProvider {

	private final JwtSettings settings;
	
	@Autowired
	public AuthJwtProvider(final JwtSettings settings) {
		this.settings = settings;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final AuthJwtResource authToken = (AuthJwtResource) authentication;
		final TokenJwtRaw rawToken = (TokenJwtRaw) authToken.getCredentials();

		final Jws<Claims> jwsClaims = rawToken.parse(settings.getTokenSigningKey());

		final String subject = jwsClaims.getBody().getSubject();

		@SuppressWarnings("unchecked")
		final List<String> privileges = jwsClaims.getBody().get("privileges", List.class);

		return new AuthJwtResource(new LoggedInResource(subject, Sets.newHashSet(privileges)));
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return AuthJwtResource.class.isAssignableFrom(authentication);
	}
	
}