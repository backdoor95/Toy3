package com.fastcampus.toy3._core.utils;

import com.fastcampus.toy3.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class DummyObjectUtils {

    protected static User newUser(String username, String nickName) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .username(username)
                .password(encPassword)
                .email(username + "@nate.com")
                .nickName(nickName)
                .status(true)
                .roles("ROLE_USER")
                .build();
    }

}
