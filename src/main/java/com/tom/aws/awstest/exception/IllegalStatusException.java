package com.tom.aws.awstest.exception;

import com.tom.aws.awstest.exception.global.IllegalGlobalException;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class IllegalStatusException extends IllegalGlobalException {

	public IllegalStatusException(String msg) {
		super(msg);
	}

	public IllegalStatusException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
