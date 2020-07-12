package com.petz.api.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.petz.api.user.domain.User;

@Service
@Transactional
class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Secured("USER_SAVE")
	@Override
	public Optional<User> criar(final User user) {
		return Optional.of(userRepository.save(user));
	}

	@Secured("USER_SAVE")
	@Override
	public Optional<User> atualizar(final User user) {
		return userRepository
				.findById(user.getId())
				.map(userById -> {
					userById.setName(user.getName());
					userById.setPassword(user.getPassword());
					return userById;
				})
				.map(userRepository::save);
	}

	@Secured("USER_DELETE")
	@Override
	public Optional<User> excluirPorId(final Integer id) {
		return userRepository.findById(id)
					.map(user-> {
						userRepository.deleteById(id);
						return user;
					});
	}

	@Secured("USER_DELETE")
	@Override
	public Optional<User> excluirPorUsername(final String username) {
		return userRepository.deleteOneByUsername(username);
	}

	@Secured("USER_GET")
	@Override
	public Optional<User> buscarPorId(final Integer id) {
		return userRepository.findById(id);
	}

	@Secured("USER_GET")
	@Override
	public Optional<User> buscarPorUsername(final String username) {
		return userRepository.findOneByUsername(username);
	}

	@Secured("USER_GET")
	@Override
	public Page<User> listar(final Pageable pageable) {
		return userRepository.findAll(pageable);
	}

}
