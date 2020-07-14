package com.petz.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
	Token accessToken;
	Token refreshToken;
}