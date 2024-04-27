package com.cashrich.CashRichAssessment.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {

    @Value("${app.jwt.secret:check12345check12345gfghghghhggyjgyjgyjgdfththfhfthfthfthfthfhfhfhfthffdgbgh6tuhjyj7u6545yt45y56y6yyh5h6h6565y45y5yh65h55y545y56y45t64y56yh56y6uy5g45y56y6y4}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:1800000}")
    private int jwtExpirationInMs = 1800000;

    public String generateToken(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.getSubject());
    }
}
