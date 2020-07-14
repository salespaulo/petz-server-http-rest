package com.petz.api.auth.token;

import java.util.Optional;

import com.petz.api.user.domain.User;

public interface TokenResourceVerifier <T extends TokenResource> {

	public Optional<User> verify(T token);

}