package com.petz.api.auth.resource;

import com.petz.api.auth.jwt.JwtToken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class LoginTokenResource {

	private JwtToken accessToken;
	
	private JwtToken refreshToken;
	
}
