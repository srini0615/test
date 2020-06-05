package com.tcs.fresco;

public class UserAuthenticator {

	private UserAuthenticatorInterface authenticator;

	public void setUserAuthenticator(UserAuthenticatorInterface authenticator) {
		this.authenticator = authenticator;
	}

	public boolean authenticateUser(String username, String password) throws FailedToAuthenticateException {
		return(this.authenticator.authenticateUser(username, password));
	}

}
