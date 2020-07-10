package com.petz.api.user;

import java.util.Optional;

import com.petz.api.user.domain.User;

public interface UserService {

	Optional<User> getByUsernameOpt(final String username);

	User getByUsername(final String username);

	User save(final User user);

}
