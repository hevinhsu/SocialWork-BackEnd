package com.socialWork.Util;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Component
public class JWTTokenUtils  {
	private static final Logger log = LoggerFactory.getLogger("JWTTokenUtils.class");

	
    static final long EXPIRATIONTIME = 60 * 60;     // 1h
    static final String AUTHORITIES_KEY = "auth";
    static final String TOKEN_PREFIX = "Bearer";        // Token前缀
    static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
    static final Key key = MacProvider.generateKey();

    public String createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()  //獲取使用者的許可權字串，如 USER,ADMIN
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date validity = (new Date(System.currentTimeMillis() + EXPIRATIONTIME * 1000)); //存放過期時間

        return Jwts.builder()         //建立Token令牌
                .setSubject(authentication.getName())   //設定面向使用者
                .claim(AUTHORITIES_KEY,authorities)    //新增許可權屬性
                .setExpiration(validity)      //設定失效時間
                .signWith(key) //生成簽名
                .compact();
    }
    //獲取使用者許可權
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
    //驗證Token是否正確
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token); //通過金鑰驗證Token
            return true;
        } catch (MalformedJwtException e) {         //JWT格式錯誤
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {         //JWT過期
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {        //不支援該JWT
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {        //引數錯誤異常
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

}