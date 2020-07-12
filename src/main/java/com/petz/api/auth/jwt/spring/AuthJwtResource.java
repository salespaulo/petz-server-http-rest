package com.petz.api.auth.jwt.spring;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.petz.api.auth.jwt.TokenJwtRaw;
import com.petz.api.auth.resource.LoggedInResource;

public class AuthJwtResource extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2877954820905567501L;

    private TokenJwtRaw rawAccessToken;
    private LoggedInResource userLoggedIn;

    public AuthJwtResource(final TokenJwtRaw unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public AuthJwtResource(final LoggedInResource userLoggedIn) {
        super(userLoggedIn.getAuthorities());

        this.userLoggedIn = userLoggedIn;

        super.setAuthenticated(true);
        this.eraseCredentials();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userLoggedIn;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
    
    public LoggedInResource getUserLoggedIn() {
    	return (LoggedInResource) this.getPrincipal();
    }
}