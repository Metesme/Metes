package com.eternal.exception;

/**
 * @author jiajunmei
 */
public abstract class AbstractUploadException extends RuntimeException{

    protected AbstractUploadException(String message) {
        super(message);
    }

    protected AbstractUploadException(String message, Throwable cause) {
        super(message, cause);
    }


}
