package com._54year.dawn.core.excetion;

/**
 * dawn-基础业务异常
 *
 * @author Andersen
 */
public class DawnBusinessException extends RuntimeException {
	public DawnBusinessException() {
		super();
	}

	public DawnBusinessException(String message) {
		super(message);
	}

	public DawnBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DawnBusinessException(Throwable cause) {
		super(cause);
	}

	protected DawnBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
