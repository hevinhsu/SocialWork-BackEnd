package com.socialWork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.socialWork.auth.pojo.User;
import com.socialWork.auth.repository.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //從資料庫中載入使用者物件
        Optional<User> user = userRepository.findByUsername(username);
        //若值不再則返回null
        return new MyUserDetails(user.orElse(null));
    }
}
