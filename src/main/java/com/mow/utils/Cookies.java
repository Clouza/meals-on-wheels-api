package com.mow.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cookies {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    public String getCookie(String key) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getName();
                }
            }
        }

        return null;
    }

    public void getCookies() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + "-" + cookie.getValue());
            }
        }
    }

    public void setCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        response.addCookie(cookie);
    }

    public void deleteCookie(String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
