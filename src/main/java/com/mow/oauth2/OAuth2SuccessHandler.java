package com.mow.oauth2;

import com.mow.entity.Users;
import com.mow.enums.Providers;
import com.mow.enums.Roles;
import com.mow.jwt.JWTService;
import com.mow.service.UsersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private OAuth2AuthorizedClientService oauth2AuthorizedClientService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        Users user = usersService.findByUsername(oauth2User.getName());

        // create new record
        if(user == null) {
            this.saveToDatabase(oauth2User);
        }

        String token = jwtService.generateToken(oauth2User.getName());
        response.sendRedirect("http://localhost:3000/oauth2/callback/?token=" + token);
    }

    private void saveToDatabase(CustomOAuth2User oauth2User) {
        Users user = new Users();
        user.setUsername(oauth2User.getName());
        user.setProviderId(oauth2User.getAttribute("id"));
        user.setEmail(oauth2User.getAttribute("email"));
        user.setRole(Roles.MEMBER);

        if(oauth2User.getProvider().equalsIgnoreCase("Facebook")) {
            user.setProvider(Providers.FACEBOOK);
        }

        if(oauth2User.getProvider().equalsIgnoreCase("Google")) {
            user.setProvider(Providers.GOOGLE);
        }

        System.out.println(oauth2User.getProvider());

        usersService.save(user);
    }

}
