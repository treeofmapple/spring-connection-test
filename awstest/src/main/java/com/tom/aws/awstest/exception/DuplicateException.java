package com.tom.aws.awstest.exception;

import com.tom.aws.awstest.exception.global.CustomGlobalException;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class DuplicateException extends CustomGlobalException {
	
	public DuplicateException(String msg) {
		super(msg);
	}
	
	public DuplicateException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
