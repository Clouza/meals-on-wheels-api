package com.mow.exception;

import com.mow.response.ExceptionResponse;
import com.mow.utils.JSONBuilder;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @Autowired
    JSONBuilder jsonBuilder;

    private String jwtException = null;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        if(jsonBuilder.getCollections().get("class") != null && jsonBuilder.getCollections().get("class").equals(SignatureException.class)) {
            jwtException = (String) jsonBuilder.getCollections().get("response");
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(
                ExceptionResponse.builder()
                        .response(jwtException != null ? jwtException : authenticationException.getMessage())
                        .status(HttpStatus.FORBIDDEN.value())
                        .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .endpoint(request.getRequestURI())
                        .timestamp(new Date())
                        .build().stringify()
        );
        log.error(String.format("%s (%s)", authenticationException.getMessage(), authenticationException.getClass()));
        jsonBuilder.clear();
    }

}
