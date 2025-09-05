package lab.springboot.springbboothttps.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.security.Key;
import java.util.Date;

public class JWTUtils {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String SUBJECT = "test";
    private static final long TOKEN_VALIDITY = 864_000_000;

    public static String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(SUBJECT)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String getAuthentication(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }
}
