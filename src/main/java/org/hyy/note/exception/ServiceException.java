package org.hyy.note.exception;

/**
 * Common service-layer exception
 * 
 * @author huyanyan
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -1603048637032593628L;

	public ServiceException(Throwable cause) {
		super(cause);
		System.out.println(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		System.out.println(message+" "+cause);
	}

	public ServiceException(String message) {
		super(message);
		System.out.println(message);
	}

}
