package com.petz.api.auth;

import com.petz.api.auth.resource.LoggedInResource;
import com.petz.api.auth.resource.LoginResource;

public interface AuthService {

	LoginResource tokenRefresh(String token);

	LoginResource login(String username, String password);

	LoggedInResource loggedInToken(String token);

}
