package com.shrucode.user_service.Utils;


import com.shrucode.user_service.Entity.UserRegisterEntity;
import com.shrucode.user_service.Repository.UserRegisterRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


//add into response ID_TOKEN
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private final UserRegisterRepository userRegisterRepository;


    public CustomOAuth2SuccessHandler(OAuth2AuthorizedClientService auth2AuthorizedClientService, UserRegisterRepository userRegisterRepository) {
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
        this.userRegisterRepository = userRegisterRepository;

    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client = auth2AuthorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());

        if(client!=null){
            String idToken = null ;
            if(authToken.getPrincipal() instanceof OidcUser){
                OidcUser oidcUser = (OidcUser) authToken.getPrincipal();

                //fetch the id_token
                idToken = oidcUser.getIdToken().getTokenValue();

                // --- NEW: provision the user locally ---
                String email = oidcUser.getEmail();
                String username = oidcUser.getPreferredUsername() != null
                        ? oidcUser.getPreferredUsername()
                        : oidcUser.getSubject(); // fallback to sub if provider doesn't map nickname

                userRegisterRepository.findByUserName(username)
                        .orElseGet(() -> {
                            UserRegisterEntity newUser = new UserRegisterEntity();
                            newUser.setEmail(email);
                            newUser.setUserName(username);
                            newUser.setRole("USER");
                            newUser.setProvider(authToken.getAuthorizedClientRegistrationId()); // "gitlab" / "auth0"
                            return userRegisterRepository.save(newUser);
                        });
                // ------------------------------------------

            }

            //SEND THE ID_TOKEN IN RESPONSE JSON

            response.setContentType("application/json");
            response.getWriter().write("{ \"id_token\":\""+idToken+"\"}");
            response.getWriter().flush();
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Authorization failed");
        }


    }
}
