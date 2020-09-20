package com.socialWork.security;

import com.socialWork.user.pojo.Role;
import com.socialWork.user.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetail implements UserDetails {
    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (roles.size()>=1){
            for (  Role role : roles){
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return authorities;
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList("");
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
