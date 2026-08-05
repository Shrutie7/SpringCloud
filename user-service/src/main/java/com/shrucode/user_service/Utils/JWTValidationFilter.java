package com.shrucode.user_service.Utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTValidationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public JWTValidationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = extractJWTFromRequest(request);

        if(token != null) {
            JWTAuthenticationToken jwtAuthenticationToken = new JWTAuthenticationToken(token);

            Authentication authenticate = authenticationManager.authenticate(jwtAuthenticationToken);

            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);

            }
        }
        filterChain.doFilter(request,response);
    }
    private String extractJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken!=null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;

    }

}
