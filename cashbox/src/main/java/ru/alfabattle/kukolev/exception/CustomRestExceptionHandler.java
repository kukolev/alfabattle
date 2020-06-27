package ru.alfabattle.kukolev.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.alfabattle.kukolev.dto.ApiInfoDto;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ApiInfoDto> handleConstraintViolation(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ApiInfoDto("branch not found"), HttpStatus.NOT_FOUND);
    }
}