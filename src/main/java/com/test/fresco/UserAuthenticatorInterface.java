package com.tcs.fresco;

public interface UserAuthenticatorInterface {

	public boolean authenticateUser(String username, String password) throws FailedToAuthenticateException;

}
