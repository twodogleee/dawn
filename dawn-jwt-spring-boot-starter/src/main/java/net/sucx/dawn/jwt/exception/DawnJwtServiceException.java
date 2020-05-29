package net.sucx.dawn.jwt.exception;

public class DawnJwtServiceException extends Exception{
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
