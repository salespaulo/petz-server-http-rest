package com.petz.api.cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.petz.api.user.domain.User;

@Component
@Repository
interface ClienteRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByUsername(final String username);
	
}
