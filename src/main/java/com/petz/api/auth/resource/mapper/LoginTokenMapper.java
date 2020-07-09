package com.petz.api.auth.resource.mapper;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.petz.api.auth.jwt.AccessJwtTokenFactory;
import com.petz.api.auth.jwt.JwtToken;
import com.petz.api.auth.resource.LoginTokenResource;
import com.petz.api.auth.resource.UserLoggedIn;

@Component
public class LoginTokenMapper {

	private final AccessJwtTokenFactory tokenFactory;

	@Autowired
	public LoginTokenMapper(final AccessJwtTokenFactory tokenFactory) {
		super();
		this.tokenFactory = tokenFactory;
	}

	public Function<UserLoggedIn, LoginTokenResource> map() {
		return this::build;
	}
	
	private LoginTokenResource build(final UserLoggedIn userLoggedIn) {
		final JwtToken accessToken = tokenFactory.createAccessJwtToken(userLoggedIn);
		final JwtToken refreshAccessToken = tokenFactory.createRefreshAccessJwtToken(userLoggedIn);

		return new LoginTokenResource(accessToken, refreshAccessToken);
	}
	
}
