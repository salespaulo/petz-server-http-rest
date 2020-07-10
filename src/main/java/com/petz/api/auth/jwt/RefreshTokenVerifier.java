package com.petz.api.auth.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.petz.api.user.UserService;
import com.petz.api.user.domain.User;

@Component
public class RefreshTokenVerifier implements TokenVerifier<RefreshAccessJwtToken> {

	private final UserService userService;

	private final JwtSettings settings;

	@Autowired
	public RefreshTokenVerifier(final UserService userService, JwtSettings settings) {
		this.userService = userService;
		this.settings = settings;
	}
	
    @Override
    public Optional<User> verify(final RefreshAccessJwtToken token) {
    	final String subject = token.getSubject();
    	final String tokenJti = token.getJti();
    	final String signingKey = settings.getTokenSigningKey();

    	final Optional<User> user = userService.getByUsernameOpt(subject);

    	final boolean isValid = user.map(User::getRefreshToken)
				.map(RawJwtToken::new)
				.flatMap(raw -> RefreshAccessJwtToken.of(raw, signingKey))
				.map(RefreshAccessJwtToken::getJti)
				.map(userJti -> userJti.equals(tokenJti))
				.orElse(false);

    	return isValid ? user : Optional.empty();
    }

}
