package com.petz.api.cliente;

import java.util.Optional;

import com.petz.api.user.domain.User;

public interface ClienteService {

	Optional<User> getByUsernameOpt(final String username);

	User getByUsername(final String username);

	User save(final User user);

}