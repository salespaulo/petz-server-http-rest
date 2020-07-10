package com.petz.api.auth;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petz.api.auth.jwt.RefreshAccessJwtToken;
import com.petz.api.auth.jwt.RefreshTokenVerifier;
import com.petz.api.auth.resource.LoginTokenResource;
import com.petz.api.auth.resource.UserLoggedIn;
import com.petz.api.auth.resource.mapper.LoginTokenMapper;
import com.petz.api.core.exception.JwtTokenInvalidException;
import com.petz.api.core.exception.UsernamePasswordAuthenticationException;
import com.petz.api.user.UserService;
import com.petz.api.user.domain.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class AuthenticationService {

	private final UserService userService;

	private final LoginTokenMapper loginTokenMapper;

	private final RefreshTokenVerifier tokenVerifier;

	private final PasswordEncoder encoder;

	@Autowired
	public AuthenticationService(final UserService userService, final LoginTokenMapper loginTokenMapper,
			final RefreshTokenVerifier tokenVerifier, final PasswordEncoder encoder) {
		this.loginTokenMapper = loginTokenMapper;
		this.userService= userService;
		this.tokenVerifier = tokenVerifier;
		this.encoder = encoder;
	}
	
	public LoginTokenResource login(String username, String password) {
		final Optional<User> userOpt = userService.getByEmailOptional(username);

		// set user.authenticate
		final LoginTokenResource loginToken = userOpt
				.filter(authenticated(password))
				.map(UserLoggedIn::new)
				.map(loginTokenMapper.map())
				.orElseThrow(() -> new UsernamePasswordAuthenticationException());

		// set user.refreshToken
		return userOpt
			.map(user -> {
				user.setRefreshToken(loginToken.getRefreshToken().getToken());
				userService.save(user);
				return loginToken;

			}).orElseThrow(() -> new UsernamePasswordAuthenticationException());

	}

	public LoginTokenResource tokenRefresh(final RefreshAccessJwtToken refreshToken) {
		return tokenVerifier.verify(refreshToken)
			.map(UserLoggedIn::new)
			.map(loginTokenMapper.map())
			.orElseThrow(() -> new JwtTokenInvalidException(refreshToken));
	}

    private Predicate<User> authenticated(final String password) {
    	return user -> encoder.matches(password, user.getPassword()) && withPrivileges(user);
    }

    private boolean withPrivileges(final User user) {
		return ! user.getPrivileges().isEmpty();
	}
    
    public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("Test"));
	}
    
}
