package com.socialWork.exceptions;

/**
 *  throw Exceptions when something invalid during register
 * 
 * @author HevinHsu
 */
public class RegisterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2740954545297219982L;
	public RegisterException() {
		super();
	}
	public RegisterException(String msg) {
		super(msg);
	}

}
