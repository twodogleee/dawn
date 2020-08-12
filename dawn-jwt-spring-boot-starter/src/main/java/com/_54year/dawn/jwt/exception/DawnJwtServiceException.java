package com._54year.dawn.jwt.exception;

import com._54year.dawn.core.excetion.DawnBusinessException;

/**
 * Jwt解析异常
 *
 * @author Andersen
 */
public class DawnJwtServiceException extends DawnBusinessException {
	public DawnJwtServiceException() {
		super();
	}

	public DawnJwtServiceException(String message) {
		super(message);
	}

	public DawnJwtServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DawnJwtServiceException(Throwable cause) {
		super(cause);
	}

	protected DawnJwtServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
