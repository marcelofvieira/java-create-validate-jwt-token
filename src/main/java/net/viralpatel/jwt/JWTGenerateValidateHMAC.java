package net.viralpatel.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JWTGenerateValidateHMAC {

    //private static final String SECRET = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
    private static final String SECRET = "a8eohyfcoeiudcpno48hylr4hcsdikfhweofo4yr987589yrcbleuh";

    public static void main(String[] args) {

        String jwt = createJwtSignedHMAC();

        System.out.println("JWT Token: " + jwt);

        Jws<Claims> token = parseJwt(jwt);

        System.out.println(token.getBody());
    }

    public static Jws<Claims> parseJwt(String jwtString) {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET+"1"), SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }


    public static String createJwtSignedHMAC() {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET), SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("name", "Nome da Pessoa")
                .claim("email", "pessoa@example.com")
                .setSubject("Pessoa")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();

        return jwtToken;
    }

}
