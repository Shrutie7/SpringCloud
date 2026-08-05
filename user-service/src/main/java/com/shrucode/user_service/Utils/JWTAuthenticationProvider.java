package com.shrucode.user_service.Utils;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JWTAuthenticationProvider implements AuthenticationProvider {

    private JWTUtil jwtUtil ;
    private UserDetailsService userDetailsService ;

    public JWTAuthenticationProvider(JWTUtil jwtUtil , UserDetailsService userDetailsService){
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;

    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = ((JWTAuthenticationToken)authentication).getToken();


        String userName = jwtUtil.validateAndExtractUsername(token);

        if(userName == null ){
            throw new BadCredentialsException("Invalid JWT token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        //use UsernamePasswordAuthenticationToken or JWTAuthentication token automatically make to authenticated true & return to filter
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
