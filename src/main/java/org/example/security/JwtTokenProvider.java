package org.example.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.exception.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    // generate JWT token
    public String generateToken(Authentication authentication) {
        String usernameOrEmail = authentication.getName();

        Date currentDate = new Date();

        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(usernameOrEmail)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from Jwt token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return username;
    }

    // validate Jwt token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);

            return true;
        } catch (MalformedJwtException exception) {
            throw new JwtException("Invalid JWT token");
        } catch (ExpiredJwtException exception) {
            throw new JwtException("Expired JWT token");
        } catch (UnsupportedJwtException exception) {
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException exception) {
            throw new JwtException("JWT claims string is empty");
        }
    }
}