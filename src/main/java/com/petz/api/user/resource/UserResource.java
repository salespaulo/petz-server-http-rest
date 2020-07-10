package com.petz.api.user.resource;

import java.util.function.Function;

import com.petz.api.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class UserResource {

	private String name;
	
	private String username;
	
	private String refreshToken;
	
	public static final Function<User, UserResource> map() {
		return user -> new UserResource(user.getName(), user.getUsername(), user.getRefreshToken());
	}
	
}
