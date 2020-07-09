package com.petz.api.auth.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class JwtSettings {

    @Value("${security.jwt.tokenSigningKey}")
    private String tokenSigningKey;

    @Value("${security.jwt.tokenIssuer}")
    private String tokenIssuer;

    @Value("${security.jwt.tokenExpirationTime}")
    private Long tokenExpirationTime;

    @Value("${security.jwt.refreshTokenExpTime}")
    private Long refreshTokenExpTime;
    
}
