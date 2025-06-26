package com.tom.aws.awstest.exception;

import com.tom.aws.awstest.exception.global.CustomGlobalException;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class AlreadyExistsException extends CustomGlobalException {
	
	public AlreadyExistsException(String msg) {
		super(msg);
	}
	
	public AlreadyExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
