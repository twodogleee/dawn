package net.sucx.dawn.basic.excetion;

public class DawnBasicRuntimeException extends RuntimeException{
	public DawnBasicRuntimeException() {
		super();
	}

	public DawnBasicRuntimeException(String message) {
		super(message);
	}

	public DawnBasicRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DawnBasicRuntimeException(Throwable cause) {
		super(cause);
	}

	protected DawnBasicRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
