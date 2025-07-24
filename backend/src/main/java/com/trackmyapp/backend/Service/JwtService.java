package com.trackmyapp.backend.Service;

import com.trackmyapp.backend.Entity.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
    private static final String SECRECT_KEY="my-very-secret-key-for-jwt-signing-and-validation";

    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(SECRECT_KEY.getBytes());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public String generateToken(User user){
        Map<String,Object> claims=new HashMap<>();
        return buildToken(claims,user.getEmail());
    }

    private String buildToken(Map<String,Object> claims, String subject){
        long expirationMillis = 1000 * 60 * 60 * 24; // 24 hours
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token,User user){
        final String username=extractUsername(token);
        return username.equals(user.getEmail()) && !isTokenExpired(token);
    }
}
