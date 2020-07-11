package com.petz.api.auth;

import static com.petz.api.core.exception.Exceptions.supplierJwtTokenInvalid;
import static com.petz.api.core.exception.Exceptions.supplierUsernamePasswordInvalid;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petz.api.auth.resource.LoginResource;
import com.petz.api.auth.jwt.JwtSettings;
import com.petz.api.auth.jwt.TokenJwtRaw;
import com.petz.api.auth.jwt.TokenJwtToRefresh;
import com.petz.api.auth.jwt.TokenJwtToRefreshVerifier;
import com.petz.api.auth.resource.LoggedInResource;
import com.petz.api.auth.resource.mapper.LoginTokenMapper;
import com.petz.api.user.UserService;
import com.petz.api.user.domain.User;

@Service
@Transactional
class AuthJwtService implements AuthService {

	private final UserService userService;

	private final LoginTokenMapper loginTokenMapper;

	private final TokenJwtToRefreshVerifier tokenVerifier;

	private final PasswordEncoder encoder;

	private JwtSettings settings;

	@Autowired
	public AuthJwtService(
			final UserService userService,
			final LoginTokenMapper loginTokenMapper,
			final TokenJwtToRefreshVerifier tokenVerifier,
			final PasswordEncoder encoder,
			final JwtSettings settings) {
		this.loginTokenMapper = loginTokenMapper;
		this.userService = userService;
		this.tokenVerifier = tokenVerifier;
		this.encoder = encoder;
		this.settings = settings;
	}
	
	@Override
	public LoginResource login(String username, String password) {
		final Optional<User> userOpt = userService.buscarPorUsername(username);

		final LoginResource loginToken = userOpt
				.filter(authenticated(password))
				.map(LoggedInResource::new)
				.map(loginTokenMapper.map())
				.orElseThrow(supplierUsernamePasswordInvalid());

		return userOpt
			.map(updateRefreshToken(loginToken))
			.orElseThrow(supplierUsernamePasswordInvalid());

	}

	@Override
	public LoginResource tokenRefresh(final String token) {
		final TokenJwtToRefresh refreshToken = getRefreshAccessToken(token);

		return tokenVerifier.verify(refreshToken)
			.map(LoggedInResource::new)
			.map(loginTokenMapper.map())
			.orElseThrow(supplierJwtTokenInvalid(refreshToken));
	}
	
    
    public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("Test"));
	}

    private Predicate<User> authenticated(final String password) {
    	return user -> encoder.matches(password, user.getPassword()) && withPrivileges(user);
    }
    
    private Function<User, LoginResource> updateRefreshToken(final LoginResource loginToken) {
    	return user -> {
				user.setRefreshToken(loginToken.getRefreshToken().getToken());
				userService.atualizar(user);
				return loginToken;
    	};
    }

    private boolean withPrivileges(final User user) {
		return ! user.getPrivileges().isEmpty();
	}
    
	private TokenJwtToRefresh getRefreshAccessToken(final String token) {
		final String signingKey = settings.getTokenSigningKey();
        final TokenJwtRaw rawToken = new TokenJwtRaw(token);

		return TokenJwtToRefresh
				.of(rawToken, signingKey)
        		.orElseThrow(supplierJwtTokenInvalid(rawToken));
	}

}
