package com.example.Debate.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {

    @Value("${app.jwt.secret}")
    private String secretKey;
    @Value("${app.jwt.duration}")
    private long timeLiveOfCookie;

    public String generateToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                    .setSubject(userPrincipal.getUsername())
                    .claim("roles", String.valueOf(userPrincipal.getAuthorities()))
                    .setIssuedAt(new Date(currentTime))
                    .setExpiration(new Date(currentTime + timeLiveOfCookie))
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        }

        public String getLoginFromToken(String token){
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }
}
