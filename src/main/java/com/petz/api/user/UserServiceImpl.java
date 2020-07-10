package com.petz.api.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petz.api.user.domain.User;

@Service
@Transactional
class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> getByEmailOptional(final String username) {
		return userRepository.findOneByUsername(username);
	}

	@Override
	public User getByEmail(final String username) {
		return this.getByEmailOptional(username)
				.orElseThrow(() -> new RuntimeException("Username not found: " + username));
	}

	@Override
	public User save(final User user) {
		return userRepository.saveAndFlush(user);
	}

}
