package com.jobportal.Job.Portal.exception;

public class JobPortalException extends Exception {
    // Recommended for serializable exceptions
    private static final long serialVersionUID = 1L;

    // Constructor that takes only a message
    public JobPortalException(String message) {
        super(message);
    }

}