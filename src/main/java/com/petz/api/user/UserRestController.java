package com.petz.api.user;

import static com.petz.api.core.exception.Exceptions.supplierUserNotFound;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petz.api.auth.resource.LoggedInResource;
import com.petz.api.user.domain.User;
import com.petz.api.user.resource.UserResource;

@RestController
@RequestMapping("/api")
public class UserRestController {

	public final UserService userService;
	
	@Autowired
	public UserRestController(final UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public @ResponseBody UserResource criar(@Valid @RequestBody User user) {
		return userService
				.criar(user)
				.map(UserResource.map())
				.get();
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public @ResponseBody UserResource atualizar(@PathVariable Integer id, @Valid @RequestBody User user) {
		user.setId(id);
		return userService
				.atualizar(user)
				.map(UserResource.map())
				.orElseThrow(supplierUserNotFound(id));
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public @ResponseBody UserResource excluir(@PathVariable Integer id) {
		return userService
				.excluirPorId(id)
				.map(UserResource.map())
				.orElseThrow(supplierUserNotFound(id));
	}

	@RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
	public @ResponseBody UserResource excluir(@PathVariable String username) {
		return userService
				.excluirPorUsername(username)
				.map(UserResource.map())
				.orElseThrow(supplierUserNotFound(username));
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public @ResponseBody UserResource buscarPorId(@PathVariable Integer id) {
		return userService
				.buscarPorId(id)
				.map(UserResource.map())
				.orElseThrow(supplierUserNotFound(id));
	}

	@RequestMapping(value = "/users/username/{username}", method = RequestMethod.GET)
	public @ResponseBody UserResource buscarPorUsername(@PathVariable String username) {
		return userService
				.buscarPorUsername(username)
				.map(UserResource.map())
				.orElseThrow(supplierUserNotFound(username));
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody Page<UserResource> listar(Pageable pageable) {
		return userService
				.listar(pageable)
				.map(UserResource.map());
	}

	@Secured("PROFILE_GET")
	@RequestMapping(value = "/users/profile", method = RequestMethod.GET)
	public @ResponseBody LoggedInResource profile() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();	
		return (LoggedInResource) principal;
	}

}
