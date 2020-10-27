package com.socialWork.security;

import com.socialWork.user.pojo.User;
import com.socialWork.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //從資料庫中載入使用者物件
        Optional<User> user = userRepository.findByUsername(username);
        //除錯用，如果值存在則輸出下使用者名稱與密碼
        user.ifPresent((value)->System.out.println("使用者名稱:" + value.getUsername() +  " 使用者密碼：" + value.getPassword()));
        //若值不再則返回null
        return new MyUserDetails(user.orElse(null));
    }
}
