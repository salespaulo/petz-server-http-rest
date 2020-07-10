package com.petz.api.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.petz.api.user.domain.User;

public interface UserService {

	Optional<User> criar(User user);

	Optional<User> atualizar(User user);

	Optional<User> excluirPorId(Integer id);

	Optional<User> excluirPorUsername(String username);

	Optional<User> buscarPorId(Integer id);

	Optional<User> buscarPorUsername(String username);

	Page<User> listar(Pageable pageable);

}
