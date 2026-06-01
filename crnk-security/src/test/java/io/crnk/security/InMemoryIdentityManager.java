package io.crnk.security;

import org.eclipse.jetty.security.Constraint;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.UserStore;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.util.security.Credential;

/**
 * A simple identity manager for tests, using Jetty's in-memory user store
 * with basic authentication. Not suitable for production use.
 */
public class InMemoryIdentityManager {

	private SecurityHandler.PathMapped securityHandler;

	private HashLoginService loginService;

	private UserStore userStore;

	private final String realm = "myrealm";

	public InMemoryIdentityManager() {
		userStore = new UserStore();

		loginService = new HashLoginService();
		loginService.setName(realm);
		loginService.setUserStore(userStore);

		BasicAuthenticator authenticator = new BasicAuthenticator();

		securityHandler = new SecurityHandler.PathMapped();
		securityHandler.setAuthenticator(authenticator);
		securityHandler.setRealmName(realm);
		securityHandler.setLoginService(loginService);

		securityHandler.put("/*", Constraint.ANY_USER);
	}

	public void addUser(String userId, String password, String... roles) {
		userStore.addUser(userId, Credential.getCredential(password), roles);
		loginService.setUserStore(userStore);
	}

	public SecurityHandler getSecurityHandler() {
		return securityHandler;
	}
}
