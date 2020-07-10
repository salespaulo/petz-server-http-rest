package com.petz.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petz.api.auth.jwt.JwtSettings;
import com.petz.api.auth.jwt.RawJwtToken;
import com.petz.api.auth.jwt.RefreshAccessJwtToken;
import com.petz.api.auth.resource.LoginTokenResource;

import static com.petz.api.core.exception.Exceptions.supplierJwtTokenInvalid;

@RestController
@RequestMapping("/auth")
public class AuthenticationEndpoint {

	public static final String LOGIN_PATH_ENDPOINT = "/auth/login";
	public static final String REFRESH_PATH_ENDPOINT = "/auth/token";
	
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private JwtSettings settings;

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody LoginTokenResource login(
    		@RequestHeader("username") final String username, 
    		@RequestHeader("password") final String password) {

		return authenticationService.login(username, password);
    }

    @Secured("USER_SAVE")
	@RequestMapping(value="/token", method=RequestMethod.POST)
    public @ResponseBody LoginTokenResource refresh(
    		@RequestHeader("refresh-token") final String token) {

    	final RefreshAccessJwtToken refreshToken = getRefreshAccessToken(token);
        return authenticationService.tokenRefresh(refreshToken);
    }

	private RefreshAccessJwtToken getRefreshAccessToken(final String token) {
		final String signingKey = settings.getTokenSigningKey();
        final RawJwtToken rawToken = new RawJwtToken(token);

		return RefreshAccessJwtToken
				.of(rawToken, signingKey)
        		.orElseThrow(supplierJwtTokenInvalid(rawToken));
	}

}