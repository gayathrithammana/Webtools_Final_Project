package com.webtools.huskyanswerswt.exception;

public class AnswerException extends Exception{
	public AnswerException(String message)
	{
		super("AnswerException-"+message);
	}
	
	public AnswerException(String message, Throwable cause)
	{
		super("AnswerException-"+message,cause);
	}
}
