package com.petz.api.user;

import java.util.Optional;

import com.petz.api.user.domain.User;

public interface UserService {

	Optional<User> getByEmailOptional(final String username);

	User getByEmail(final String username);

	User save(final User user);

}
