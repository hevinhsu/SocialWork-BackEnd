package com.socialWork.filter;

import com.socialWork.Util.JWTTokenUtils;
import com.socialWork.config.WebSecurityConfig;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationTokenFilter extends GenericFilterBean {
    @Autowired
    private JWTTokenUtils tokenProvider;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
            String jwt = resolveToken(httpReq);
            if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {   //驗證JWT是否正確
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);  //獲取使用者認證資訊
                SecurityContextHolder.getContext().setAuthentication(authentication);   //將使用者儲存到SecurityContext
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (ExpiredJwtException e){          //JWT失效
            log.info("Security exception for user {} - {}",
                    e.getClaims().getSubject(), e.getMessage());
            log.trace("Security exception trace: {}", e);
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(WebSecurityConfig.AUTHORIZATION_HEADER);   //從HTTP頭部獲取TOKEN
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());        //返回Token字串，去除Bearer
        }
        String jwt = request.getParameter(WebSecurityConfig.AUTHORIZATION_TOKEN);    //從請求引數中獲取TOKEN
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }
}
