package com.shrucode.user_service.Utils;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    private static final String SECRET_KEY = "your-secure-secret-key-min-32bytes";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username, Long expiryTime){
       return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiryTime*60*1000))//in milli seconds
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateAndExtractUsername(String token){
        try{
            return Jwts.parser()//parse jwt token
                    .setSigningKey(key) // put signing key
                    .build()
                    .parseClaimsJws(token)//parse claims (payload) take out body
                    .getBody()
                    .getSubject();// from body take out subject in subject put username and extract username
        }catch(JwtException e){
            return null;
        }
    }
}
