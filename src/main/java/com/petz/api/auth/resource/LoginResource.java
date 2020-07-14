package com.petz.api.auth.resource;

import com.petz.api.auth.token.TokenResource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResource {

	private TokenResource accessToken;
	
	private TokenResource refreshToken;
	
}
