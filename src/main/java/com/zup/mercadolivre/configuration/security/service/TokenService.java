package com.zup.mercadolivre.configuration.security.service;

import com.zup.mercadolivre.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generate a JWT token from the user Id
     *
     * @param authentication - Contains the current user
     * @return Token
     */
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Pokemon Battle Simulator")
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Validate a jwt token
     *
     * @param token
     * @return boolean
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    /**
     * Get the user id on the jwt token
     *
     * @param token
     * @return userId
     */
    public String getUserId(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

        return body.getSubject();
    }

}
