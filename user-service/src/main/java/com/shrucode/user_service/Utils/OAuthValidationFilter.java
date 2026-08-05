package com.shrucode.user_service.Utils;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class OAuthValidationFilter extends OncePerRequestFilter {

    private final OAuthTokenValidatorUtil tokenValidatorUtil;

    public OAuthValidationFilter(OAuthTokenValidatorUtil tokenValidatorUtil) {
        this.tokenValidatorUtil = tokenValidatorUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. getting ID_TOKEN FROM AUTHORIZATION HEADER BEARER
        String token = extractJWTFromRequest(request);

        //2. After extracting token validating internally use jwks url in application.prop file to fetch public key
        if (token!=null){
            String username = tokenValidatorUtil.isTokenValid(token);

            if(StringUtil.isNullOrEmpty(username)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid or expired token");
                return;
            }
        //3. storing the authentication object in SecurityContextHolder bcoz without this login not successfull
            Authentication auth = new UsernamePasswordAuthenticationToken(username,null, List.of());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }

    public String extractJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
