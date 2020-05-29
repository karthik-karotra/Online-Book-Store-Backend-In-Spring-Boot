package com.bridgelabz.onlinebookstore.utils.implementation;

import com.bridgelabz.onlinebookstore.exceptions.JWTException;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JWTToken implements ITokenGenerator {

    @Autowired
    ApplicationProperties applicationProperties;

    @Override
    public String generateToken(int id, int expirationTime) {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        return Jwts.builder()
                .setId(Integer.toString(id))
                .setIssuedAt(date)
                .setExpiration(new Date(currentTime + expirationTime))
                .signWith(SignatureAlgorithm.HS256, applicationProperties.getJwtSecret())
                .compact();
    }

    @Override
    public int getId(String token) {
        int id = 0;
        try {
            Claims claims = Jwts.parser().setSigningKey(applicationProperties.getJwtSecret()).parseClaimsJws(token).getBody();
            id = Integer.parseInt(claims.getId());
        } catch (ExpiredJwtException ex) {
            throw new JWTException("Session Time Out", JWTException.ExceptionType.SESSION_TIMEOUT);
        }
        return id;
    }
}
