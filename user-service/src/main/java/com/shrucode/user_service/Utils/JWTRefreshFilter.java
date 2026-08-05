package com.shrucode.user_service.Utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTRefreshFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public JWTRefreshFilter(AuthenticationManager authenticationManager , JWTUtil jwtUtil){
        this.authenticationManager = authenticationManager ;
        this.jwtUtil = jwtUtil ;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(!request.getServletPath().equals("/refresh-token")){
            filterChain.doFilter(request,response);
            return;
        }
        String refreshToken = extractJWTFromRequest(request);

        if(refreshToken == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        JWTAuthenticationToken jwtAuthenticationToken = new JWTAuthenticationToken(refreshToken);

        Authentication authentication =authenticationManager.authenticate(jwtAuthenticationToken);

        if(authentication.isAuthenticated()){
            String newToken = jwtUtil.generateToken(authentication.getName(),15L);//create access token again with 15 min exp time
            response.setHeader("Authorization" , "Bearer "+ newToken);
        }
    }

    public String extractJWTFromRequest(HttpServletRequest request){
        Cookie cookie[] = request.getCookies();
        if(cookie == null){
            return null ;
        }
        String refreshToken = null ;

        for(Cookie cookies : cookie){
            if("refreshToken".equals(cookies.getValue())){
                refreshToken =  cookies.getValue();
            }
        }
        return refreshToken;
    }
}
