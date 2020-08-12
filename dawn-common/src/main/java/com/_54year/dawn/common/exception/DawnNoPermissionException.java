package com._54year.dawn.common.exception;

import com._54year.dawn.core.excetion.DawnBusinessException;

public class DawnNoPermissionException extends DawnBusinessException {
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
