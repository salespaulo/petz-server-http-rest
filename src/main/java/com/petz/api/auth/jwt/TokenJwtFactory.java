package com.petz.api.auth.jwt;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.petz.api.auth.resource.LoggedInResource;
import com.petz.api.auth.token.TokenResource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenJwtFactory {

	public static final String CLAIM_PRIVILEGES = "privileges";

    private static final String PRIV_PROFILE_GET = "PROFILE_GET";

    private final JwtSettings settings;

    @Autowired
    public TokenJwtFactory(final JwtSettings settings) {
		this.settings = settings;
	}

	public TokenResource createAccessJwtToken(LoggedInResource userLoggedIn) {
		Preconditions.checkArgument(StringUtils.isNotBlank(userLoggedIn.getUsername()), "Cannot create JWT Token without username");
		Preconditions.checkArgument(notEmptyAuthorities(userLoggedIn), "User doesn't have any privileges");

		final String tokenSigningKey = settings.getTokenSigningKey();
		final String tokenIssuer = settings.getTokenIssuer();
		final Long tokenExpiration = getTokenTimeout();

        final ZonedDateTime currentTime = ZonedDateTime.now();
        final Date issueAt = Date.from(currentTime.toInstant());
		final Date expiration = Date.from(currentTime.plusMinutes(tokenExpiration).toInstant());

        final Claims claims = Jwts.claims().setSubject(userLoggedIn.getUsername());

        claims.put(CLAIM_PRIVILEGES, getPrivileges(userLoggedIn));

		String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(tokenIssuer)
          .setIssuedAt(issueAt)
          .setExpiration(expiration)
          .signWith(SignatureAlgorithm.HS512, tokenSigningKey)
        .compact();

        return new TokenJwtToAccess(token, claims);
    }

    public TokenResource createRefreshAccessJwtToken(LoggedInResource userLoggedIn) {

        if (StringUtils.isBlank(userLoggedIn.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }
        
		final String tokenSigningKey = settings.getTokenSigningKey();
		final String tokenIssuer = settings.getTokenIssuer();
		final Long tokenExpiration = getTokenRefreshTimeout();

        final ZonedDateTime currentTime = ZonedDateTime.now();
        final Date issueAt = Date.from(currentTime.toInstant());
		final Date expiration = Date.from(currentTime.plusMinutes(tokenExpiration).toInstant());

        final Claims claims = Jwts.claims().setSubject(userLoggedIn.getUsername());
        claims.put(CLAIM_PRIVILEGES, Arrays.asList(PRIV_PROFILE_GET));
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(tokenIssuer)
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(issueAt)
          .setExpiration(expiration)
          .signWith(SignatureAlgorithm.HS512, tokenSigningKey)
        .compact();

        return new TokenJwtToAccess(token, claims);
    }

	private List<String> getPrivileges(final LoggedInResource userLoggedIn) {
		return userLoggedIn.getAuthorities().stream()
				.map(GrantedAuthority::toString)
				.collect(Collectors.toList());
	}

	private boolean notEmptyAuthorities(final LoggedInResource userLoggedIn) {
		return ! userLoggedIn.getAuthorities().isEmpty();
	}

	private Long getTokenTimeout() {
		return settings.getTokenExpirationTime();
	}

	private Long getTokenRefreshTimeout() {
		return settings.getRefreshTokenExpTime();
	}

}