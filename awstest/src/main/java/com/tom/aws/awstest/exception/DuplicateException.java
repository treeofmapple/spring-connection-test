package com.tom.aws.awstest.exception;

import org.springframework.dao.DataIntegrityViolationException;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class DuplicateException extends DataIntegrityViolationException {
	
	public DuplicateException(String msg) {
		super(msg);
	}
	
	public DuplicateException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
