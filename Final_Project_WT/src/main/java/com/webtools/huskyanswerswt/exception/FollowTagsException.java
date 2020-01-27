package com.webtools.huskyanswerswt.exception;

public class FollowTagsException extends Exception{
	public FollowTagsException(String message)
	{
		super("FollowTagsException-"+message);
	}
	
	public FollowTagsException(String message, Throwable cause)
	{
		super("FollowTagsException-"+message,cause);
	}
}
