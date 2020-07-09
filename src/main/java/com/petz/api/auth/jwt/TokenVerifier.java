package com.petz.api.auth.jwt;

import java.util.Optional;

import com.petz.api.user.domain.User;

public interface TokenVerifier <T extends JwtToken> {

	public Optional<User> verify(T token);

}