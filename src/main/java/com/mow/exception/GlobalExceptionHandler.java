package com.mow.exception;

import com.mow.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.Date;

@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    private ResponseEntity<Object> exceptionResponse(Exception exception, HttpStatus httpStatus, String httpReason) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        exceptionResponse.setResponse(exception.getMessage());
        exceptionResponse.setStatus(httpStatus.value());
        exceptionResponse.setError(httpReason);
        exceptionResponse.setEndpoint(this.request.getRequestURI());
        exceptionResponse.setTimestamp(new Date());

        if(httpStatus.equals(HttpStatus.INTERNAL_SERVER_ERROR) || httpStatus.equals(HttpStatus.BAD_REQUEST)) {
            exceptionResponse.setResponse("Contact the developer to find out more");
        }

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), httpStatus);
    }

    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return this.exceptionResponse(exception, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    // 401
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> unAuthorized(AuthenticationException authenticationException) {
        return this.exceptionResponse(authenticationException, HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return this.exceptionResponse(exception, HttpStatus.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }

    // 500
    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<?> handleAnyException(RuntimeException exception) {
        return this.exceptionResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    // 400
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleHttpClient(HttpClientErrorException httpClientErrorException) {
        return this.exceptionResponse(httpClientErrorException, (HttpStatus) httpClientErrorException.getStatusCode(), httpClientErrorException.getStatusText());
    }

}
