package com.mow.exception;

import com.mow.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.Date;

@Slf4j
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

        if(httpStatus.equals(HttpStatus.INTERNAL_SERVER_ERROR) || exception instanceof HttpClientErrorException) {
            exceptionResponse.setResponse("Contact the developer to find out more");
            log.error(String.format("%s (%s)", exception.getMessage(), exception.getClass()));
        }

        if(exception instanceof HttpMessageNotReadableException) {
            exceptionResponse.setResponse("Request body should not be empty");
            log.error(String.format("%s (%s)", exception.getMessage(), exception.getClass()));
        }

        if(exception instanceof MaxUploadSizeExceededException) {
            exceptionResponse.setResponse("Image size should be less than 2MB");
            log.error(String.format("%s (%s)", exception.getMessage(), exception.getClass()));
        }

        if(exception instanceof HttpMediaTypeNotSupportedException) {
            exceptionResponse.setResponse("Media Type not supported");
            log.error(String.format("%s (%s)", exception.getMessage(), exception.getClass()));
        }

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), httpStatus);
    }

    // 400
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return this.exceptionResponse(exception, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return this.exceptionResponse(exception, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return this.exceptionResponse(exception, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return this.exceptionResponse(exception, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
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

    // 415
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return this.exceptionResponse(exception, HttpStatus.UNSUPPORTED_MEDIA_TYPE, HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
    }
        // 500
    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    protected ResponseEntity<?> handleAnyException(RuntimeException exception) {
        return this.exceptionResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    // Http Client
    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<?> handleHttpClient(HttpClientErrorException httpClientErrorException) {
        return this.exceptionResponse(httpClientErrorException, (HttpStatus) httpClientErrorException.getStatusCode(), httpClientErrorException.getStatusText());
    }

    // Upload size
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<?> handleMaxSize(MaxUploadSizeExceededException exception) {
        return this.exceptionResponse(exception, HttpStatus.NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
    }

}
