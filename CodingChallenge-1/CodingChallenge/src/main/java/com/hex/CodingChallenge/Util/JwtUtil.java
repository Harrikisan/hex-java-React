package com.hex.CodingChallenge.Util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static final String secretKey = "LMS_HEX_MAY_7867486754876768463_SECRETKEY_FOR_JWT_UTIL_2025";
    private static final long expirationTimeInMills = 43200000; // 12 hrs

    private final Key key;

    public JwtUtil() {
        try {
            if (secretKey.getBytes().length < 32) {
                throw new IllegalArgumentException("JWT secret key must be at least 32 bytes.");
            }
            this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        } catch (Exception e) {
            System.err.println("Error in JwtUtil constructor: " + e.getMessage());
            throw e;
        }
    }

    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMills))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    private Key getSigningKey(){
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
    
    public boolean verifyToken(String token, String email) {
		String extractedEmail = Jwts.parserBuilder()
									.setSigningKey(getSigningKey())
									 .build()
									 .parseClaimsJws(token)
									 .getBody()
									 .getSubject();
		Date expirationDate = Jwts.parserBuilder()
									.setSigningKey(getSigningKey())
									 .build()
									 .parseClaimsJws(token)
									 .getBody()
									 .getExpiration();
		
		return extractedEmail.equals(email) && new Date().before(expirationDate); 			
	}
    
    public String extractUsername(String token) {
		 
		return  Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				 .build()
				 .parseClaimsJws(token)
				 .getBody()
				 .getSubject(); 
	}
}
