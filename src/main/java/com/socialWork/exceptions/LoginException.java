package com.socialWork.exceptions;

/**
 * throws exceptions when something invalid during login
 * 
 * @author HevinHsu
 *
 */
public class LoginException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2724583681987947506L;

	public LoginException() {
		super();
	}
	public LoginException(String msg) {
		super(msg);
	}
	
	
}
