package com.petz.api.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petz.api.user.domain.User;

@Repository
interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByUsername(final String username);
	
}
