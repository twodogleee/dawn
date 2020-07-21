package com._54year.dawn.common.exception;

import com._54year.dawn.basic.excetion.DawnBasicRuntimeException;

public class DawnNoPermissionException extends DawnBasicRuntimeException {
	public DawnNoPermissionException() {
		super();
	}

	public DawnNoPermissionException(String message) {
		super(message);
	}

	public DawnNoPermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DawnNoPermissionException(Throwable cause) {
		super(cause);
	}

	protected DawnNoPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
