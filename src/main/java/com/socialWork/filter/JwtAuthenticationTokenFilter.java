package com.socialWork.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialWork.Util.JWTTokenUtils;
import com.socialWork.auth.service.AuthService;
import com.socialWork.config.WebSecurityConfig;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtAuthenticationTokenFilter extends GenericFilterBean {
    @Autowired
    private JWTTokenUtils tokenProvider;
    @Autowired
    private AuthService authService;
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
            String jwt = resolveToken(httpReq);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (ExpiredJwtException e){
            String token = authService.createRefreshToken();
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("refreshToken", token);
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(tokenMap));
        }
    }
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(WebSecurityConfig.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
        	return bearerToken.substring(7, bearerToken.length());
        }
        String jwt = request.getParameter(WebSecurityConfig.AUTHORIZATION_TOKEN);
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }
}
