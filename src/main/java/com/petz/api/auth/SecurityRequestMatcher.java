package com.petz.api.auth;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SecurityRequestMatcher implements RequestMatcher {

	private RequestMatcher matcher;
	private OrRequestMatcher skips;

	public SecurityRequestMatcher(String pathToMatch, String... pathToSkip) {
		this.matcher = new AntPathRequestMatcher(pathToMatch);
		this.skips = new OrRequestMatcher(Arrays.stream(pathToSkip)
				.map(AntPathRequestMatcher::new)
				.collect(Collectors.toList()));
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		return skips.matches(request) ? false : matcher.matches(request);
	}
}