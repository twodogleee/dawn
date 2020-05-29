package net.sucx.dawn.common.exception;

public class DawnNoPermissionException extends RuntimeException{
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
