package com.petz.api.auth.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.petz.api.auth.token.TokenResourceVerifier;
import com.petz.api.user.UserService;
import com.petz.api.user.domain.User;

@Component
public class TokenJwtToRefreshVerifier implements TokenResourceVerifier<TokenJwtToRefresh> {

	private final UserService userService;

	private final JwtSettings settings;

	@Autowired
	public TokenJwtToRefreshVerifier(final UserService userService, JwtSettings settings) {
		this.userService = userService;
		this.settings = settings;
	}
	
    @Override
    public Optional<User> verify(final TokenJwtToRefresh token) {
    	final String subject = token.getSubject();
    	final String tokenJti = token.getJti();
    	final String signingKey = settings.getTokenSigningKey();

    	final Optional<User> user = userService.buscarPorUsername(subject);

    	final boolean isValid = user.map(User::getRefreshToken)
				.map(TokenJwtRaw::new)
				.flatMap(raw -> TokenJwtToRefresh.of(raw, signingKey))
				.map(TokenJwtToRefresh::getJti)
				.map(userJti -> userJti.equals(tokenJti))
				.orElse(false);

    	return isValid ? user : Optional.empty();
    }

}
