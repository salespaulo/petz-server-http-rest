package com.petz.api.auth.resource;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.petz.api.user.domain.User;

public final class LoggedInResource {

	private final String username;
	private List<GrantedAuthority> authorities = Collections.emptyList();

	public LoggedInResource(final User user) {
		this(user.getUsername(), user.getPrivileges());
	}
	
	public LoggedInResource(final String username, String...privileges) {
		this(username, Sets.newHashSet(privileges));
	}
	
	public LoggedInResource(String username, Set<String> privileges) {
		Preconditions.checkArgument(StringUtils.isNotBlank(username), "Username is empty.");
		Preconditions.checkArgument(! privileges.isEmpty(), "Privileges is empty.");

		this.username = username;
		this.authorities = privileges.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
	}

	public String getUsername() {
		return username;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

}