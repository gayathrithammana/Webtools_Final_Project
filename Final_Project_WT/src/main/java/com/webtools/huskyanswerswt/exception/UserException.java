package com.webtools.huskyanswerswt.exception;

public class UserException extends Exception{
	public UserException(String message)
	{
		super("UserException-"+message);
	}
	
	public UserException(String message, Throwable cause)
	{
		super("UserException-"+message,cause);
	}
}
