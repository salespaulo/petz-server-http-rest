package com.petz.api.auth.resource.mapper;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.petz.api.auth.jwt.TokenJwtFactory;
import com.petz.api.auth.resource.LoginResource;
import com.petz.api.auth.resource.LoggedInResource;
import com.petz.api.auth.token.TokenResource;

@Component
public class LoginTokenMapper {

	private final TokenJwtFactory tokenFactory;

	@Autowired
	public LoginTokenMapper(final TokenJwtFactory tokenFactory) {
		super();
		this.tokenFactory = tokenFactory;
	}

	public Function<LoggedInResource, LoginResource> map() {
		return this::build;
	}
	
	private LoginResource build(final LoggedInResource userLoggedIn) {
		final TokenResource accessToken = tokenFactory.createAccessJwtToken(userLoggedIn);
		final TokenResource refreshAccessToken = tokenFactory.createRefreshAccessJwtToken(userLoggedIn);

		return new LoginResource(accessToken, refreshAccessToken);
	}
	
}
