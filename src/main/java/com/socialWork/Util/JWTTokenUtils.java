package com.socialWork.Util;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Component
public class JWTTokenUtils  {
	private static final Logger log = LoggerFactory.getLogger("JWTTokenUtils.class");

	
    @Value("${token.timeout.jwt}")
    private long EXPIRATIONTIME;
    static final String AUTHORITIES_KEY = "auth";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    static final Key key = MacProvider.generateKey();
    
    public String createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()  //獲取使用者的許可權字串，如 USER,ADMIN
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        System.out.println("expiration time = "+EXPIRATIONTIME);
        Date validity = (new Date(System.currentTimeMillis() + EXPIRATIONTIME * 1000*60)); //存放過期時間
        System.out.println(validity);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            String user = claims.getSubject();
            return user != null ? new UsernamePasswordAuthenticationToken(user, "", authorities): null;

        }catch (Exception e){
            log.trace("getAuthentication error", e);
        }
        return null;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token); //通過金鑰驗證Token
            return true;
        } catch (MalformedJwtException e) {         //JWT格式錯誤
            log.info("Invalid JWT token.");
        } catch (UnsupportedJwtException e) {        //不支援該JWT
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {        //引數錯誤異常
            log.info("JWT token compact of handler are invalid.");
        }
        return false;
    }
    
    public static String createRefreshToken() {  
        final String token = UUID.randomUUID().toString();
    	final byte[] tokenByte = token.getBytes();
        final String encodedText = Base64.getEncoder().encodeToString(tokenByte);
        return encodedText;  
    }  
}