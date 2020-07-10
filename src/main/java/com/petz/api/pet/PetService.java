package com.petz.api.pet;

import java.util.Optional;

import com.petz.api.user.domain.User;

public interface PetService {

	Optional<User> getByUsernameOpt(final String username);

	User getByUsername(final String username);

	User save(final User user);

}
