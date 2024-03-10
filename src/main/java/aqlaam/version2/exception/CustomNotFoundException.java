package aqlaam.version2.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
/**
 * This class is used to create a custom exception.
 * It contains the status and the error message.
 */
@Getter
public class CustomNotFoundException extends RuntimeException {

    private final HttpStatus status;
    public CustomNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}