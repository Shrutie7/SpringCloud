package com.shrucode.user_service.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,JWTUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getServletPath().equals("/generate-token")){
            filterChain.doFilter(request,response);
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();

        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),LoginRequest.class);

        UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        if(authentication.isAuthenticated()){
            String token = jwtUtil.generateToken(authentication.getName(),15L); // 15 mins of expiry time
            response.setHeader("Authorization" , "Bearer " + token);

            String refreshToken = jwtUtil.generateToken(authentication.getName(), (long) (7*24*60));

            //set refreshToken in HTTPonly cookie // we can also send in response body but then client has to store it in local storage or in memory
            Cookie refreshCookie= new Cookie("refreshToken",refreshToken);

            //with these 3 making refresh token secure
            refreshCookie.setHttpOnly(true); // prevent javascript from accessing it
            refreshCookie.setSecure(true); // sent only over HTTPS
            refreshCookie.setPath("/refresh-token"); // Cookie available only for refreshToken endpoint

            refreshCookie.setMaxAge(7*24*60*60); // 7 days expiry

            response.addCookie(refreshCookie);
        }

    }
}
