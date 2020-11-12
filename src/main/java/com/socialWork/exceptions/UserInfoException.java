package com.socialWork.exceptions;

/**
 *  throw Exceptions when something invalid during register
 * 
 * @author HevinHsu
 */
public class UserInfoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2740954545297219982L;
	public UserInfoException() {
		super();
	}
	public UserInfoException(String msg) {
		super(msg);
	}

}
