package com.petz.api.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petz.api.user.domain.User;

@Repository
interface UserRepository extends JpaRepository<User, Integer> {

	void deleteById(Integer id);
	
	Optional<User> deleteOneByUsername(String username);
	
	Optional<User> findById(Integer id);
	
	Optional<User> findOneByUsername(String username);
	
}
