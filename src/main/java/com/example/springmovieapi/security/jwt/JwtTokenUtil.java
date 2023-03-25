package com.example.springmovieapi.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for working with JWT tokens. Provides methods for generating, validating, and extracting information from JWT tokens.
 */
@Component
public class JwtTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

     /*
        When the app is deployed this key must be stored in an environment variable, this is how to do it with Elastic Beanstalk:
        1. String keyString = Encoders.BASE64.encode(key.getEncoded()); (encode the key created above)
        2. Add the key as an EnvironmentProperty and enter its name (JWT_SECRET) and its value (keyString)
        3. Retrieve it when needed:
           String jwtSecret = System.getenv("JWT_SECRET");
           SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
     */

    /**
     * Generates a JWT token based on the authentication information provided.
     * @param authentication the authentication object containing the user's details
     * @return a JWT token as a string
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the username from the provided JWT token.
     * @param token the JWT token from which to extract the username
     * @return the username extracted from the JWT token
     */
    public String getUserNameFromJwtToken(String token) {
        Claims claims = (Claims) Jwts.parserBuilder().setSigningKey(key).build().parse(token).getBody();
        return claims.getSubject();
    }

    /**
     * Validates the provided JWT token.
     * @param authToken the JWT token to validate
     * @return {@code true} if the JWT token is valid, {@code false} otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
