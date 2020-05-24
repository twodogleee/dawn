package net.sucx.dawn.common.exception;

public class JwtServiceException extends Exception{
    public JwtServiceException() {
        super();
    }

    public JwtServiceException(String message) {
        super(message);
    }

    public JwtServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtServiceException(Throwable cause) {
        super(cause);
    }

    protected JwtServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
