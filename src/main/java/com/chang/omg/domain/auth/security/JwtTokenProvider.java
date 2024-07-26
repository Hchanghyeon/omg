package com.chang.omg.domain.auth.security;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chang.omg.global.config.property.AuthProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AuthProperties authProperties;
    private SecretKey secretKey;

    @PostConstruct
    public void initialize() {
        final byte[] keyBytes = Decoders.BASE64.decode(authProperties.getSecretKey());
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createRegisterToken(final String email) {
        final Map<String, String> claims = Map.of("email", email);

        return generateToken(TokenType.REGISTER.toString(), claims, authProperties.getRegisterTokenExpirationTime());
    }

    private String generateToken(final String subject, final Map<String, String> claims, final Long expirationTime) {
        final Date now = new Date();

        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public String getSubject(final String token) {
        return parseToken(token)
                .getPayload()
                .getSubject();
    }

    public String getClaim(final String token, final String claim) {
        return (String)parseToken(token)
                .getPayload()
                .get(claim);
    }

    private Jws<Claims> parseToken(final String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}
