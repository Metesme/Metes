package com.eternal.exception;

/**
 * @author jiajunmei
 */
public class UploadGeneralException extends AbstractUploadException{

    public UploadGeneralException(Throwable cause) {
        super("上传出现了异常", cause);
    }

    public UploadGeneralException(String message) {
        super(message);
    }

    public UploadGeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}
