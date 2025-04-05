package com.example.springsecurityjwt.JWT;


import com.example.springsecurityjwt.Service.UserDetailsImpl;
import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component

public class JwtUtils {
    private static final Logger logger=   LoggerFactory.getLogger(JwtUtils.class);
    @Value("${fekher.app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${fekher.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        return generateTokenFromUsername(userPrincipal.getUsername());
    }
    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).
                setExpiration(new Date((new Date()).getTime()+jwtExpirationMs)).signWith(SignatureAlgorithm.HS512,jwtSecretKey).compact();
    }
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT signature:{}",e.getMessage());
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT token:{}",e.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("JWT token is expired:{}",e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("JWT token is unsupported:{}",e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty:{}",e.getMessage());
        }
        return false;
    }


}
