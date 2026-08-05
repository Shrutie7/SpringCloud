package com.shrucode.user_service.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class OAuthTokenValidatorUtil {
    public String isTokenValid(String token){
        String issu  = getIssuerIdFromToken(token);
        JwtDecoder decoder = JwtDecoders.fromIssuerLocation(issu); // jwks gitlab/auth0
        Jwt jwt = decoder.decode(token);
        if (jwt!=null){
            return (String) jwt.getClaims().get("sub");
        }
        return null;
    }
    public static String getIssuerIdFromToken(String jwtToken){
        try{
            String [] parts = jwtToken.split("\\.");

            if(parts.length<2){
                throw new IllegalArgumentException("Invalid jwt token");
            }

            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> payloadMap = mapper.readValue(payloadJson, Map.class);
            String iss = (String) payloadMap.get("iss"); // gitlab or auth0 is issuer
            return iss;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
