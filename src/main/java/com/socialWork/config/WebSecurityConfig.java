package com.socialWork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import com.socialWork.filter.JwtAuthenticationTokenFilter;
import com.socialWork.security.JwtAccessDeniedHandler;
import com.socialWork.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_TOKEN = "access_token";
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	//自定義獲取使用者資訊
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
        //由於使用Token，所以不需要Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        //驗證Http請求
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/login").permitAll()
<<<<<<< HEAD

        //使用者頁面需要使用者許可權
                .antMatchers("/test").hasAnyRole("USER")
                        //其它任何請求都要經過認證通過
//                        .anyRequest().authenticated()
=======
                .antMatchers("/auth/test").hasRole("ADMIN")
                .antMatchers("/test").hasAnyRole("USER")
                .anyRequest().authenticated()
>>>>>>> origin/hevinlocal
                .and()
                .logout().permitAll();
        
        //新增JWT filter 
                http
                .addFilterBefore(genericFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(JwtAuthenticationEntryPoint())
                .accessDeniedHandler(JwtAccessDeniedHandler());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public GenericFilterBean genericFilterBean() {
        return new JwtAuthenticationTokenFilter();
    }
<<<<<<< HEAD

=======
    
    @Bean
    public JwtAuthenticationEntryPoint JwtAuthenticationEntryPoint() {
    	return new JwtAuthenticationEntryPoint();
    }
    
    @Bean
    public JwtAccessDeniedHandler JwtAccessDeniedHandler() {
    	return new JwtAccessDeniedHandler();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
>>>>>>> origin/hevinlocal
}