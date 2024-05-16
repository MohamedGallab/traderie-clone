package com.massivelyflammableapps.User_Service.Configuration;

import com.nimbusds.openid.connect.sdk.claims.CommonClaimsSet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.*;
@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String base64SecretBytes;

    private static final String BLACKLIST_KEY = "blacklistedTokens";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Map<String, Date> tokenBlacklist = new ConcurrentHashMap<>();

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public Boolean hasClaim(String token, String claimName) {
       final Claims claims= extractAllClaims(token);
       return claims.get(claimName)!=null;
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails,Map<String,Object> claims){
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        System.out.println(base64SecretBytes);

        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, base64SecretBytes).compact();
    }

    public Boolean isTokenValid(String token,UserDetails userDetails) {
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenBlacklisted(token));
    }

    public void invalidateToken(String token) {
        Date expiration = Date.from(Instant.now());
        tokenBlacklist.put(token, expiration);
        redisTemplate.opsForSet().add(BLACKLIST_KEY, token);
    }

    private boolean isTokenBlacklisted(String token) {
        Date expiration = tokenBlacklist.getOrDefault(token,null);
        if(expiration==null){
            return false;
        }
        return redisTemplate.opsForSet().isMember(BLACKLIST_KEY, token);
        //return expiration.before(new Date());
    }
}
