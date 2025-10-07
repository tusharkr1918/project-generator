package dev.tushar.projectgen.exception;

public class ProjectGenerationException extends RuntimeException {
    public ProjectGenerationException(String message) {
        super(message);
    }

    public ProjectGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}