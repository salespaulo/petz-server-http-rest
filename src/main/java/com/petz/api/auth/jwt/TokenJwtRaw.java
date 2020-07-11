package com.petz.api.auth.jwt;

import static com.petz.api.core.exception.Exceptions.newBadCredentials;
import static com.petz.api.core.exception.Exceptions.newJwtTokenExpired;

import org.springframework.security.authentication.BadCredentialsException;

import com.petz.api.auth.token.TokenResource;
import com.petz.api.core.exception.JwtTokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenJwtRaw implements TokenResource {
            
    private final String token;
    
    public TokenJwtRaw(final String token) {
        this.token = token;
    }

    /**
     * Parses and validates JWT Token signature.
     * 
     * @throws BadCredentialsException
     * @throws JwtTokenExpiredException
     * 
     */
    public Jws<Claims> parse(final String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.error("Invalid JWT Token {}", ex);
            throw newBadCredentials(ex);
        } catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired {}", expiredEx);
            throw newJwtTokenExpired(this, expiredEx);
        }
    }

    @Override
    public String getToken() {
        return token;
    }
}