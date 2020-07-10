package com.petz.api.pet;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petz.api.user.domain.User;

@Service
@Transactional
class PetServiceImpl implements PetService {

	@Autowired
	private PetRepository userRepository;

	@Override
	public Optional<User> getByUsernameOpt(final String username) {
		return userRepository.findOneByUsername(username);
	}

	@Override
	public User getByUsername(final String username) {
		return this.getByUsernameOpt(username)
				.orElseThrow(() -> new RuntimeException("Username not found: " + username));
	}

	@Override
	public User save(final User user) {
		return userRepository.saveAndFlush(user);
	}

}
