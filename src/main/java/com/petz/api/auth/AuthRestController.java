package com.petz.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petz.api.auth.resource.LoginResource;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

	public static final String LOGIN_PATH_ENDPOINT = "/auth/login";
	public static final String REFRESH_PATH_ENDPOINT = "/auth/token";
	
	@Autowired
	private AuthService authenticationService;

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody LoginResource login(
    		@RequestHeader("username") final String username, 
    		@RequestHeader("password") final String password) {

		return authenticationService.login(username, password);
    }

	@RequestMapping(value="/token", method=RequestMethod.POST)
    public @ResponseBody LoginResource refresh(
    		@RequestHeader("refresh-token") final String token) {

        return authenticationService.tokenRefresh(token);
    }

    @Secured("PROFILE_GET")
	@RequestMapping(value="/profile", method=RequestMethod.GET)
    public @ResponseBody String profile() {
    	return "FUNCIONOU!";
    }

}