package com.fastcampus.toy3._core.security;

import com.fastcampus.toy3._core.erros.exception.Exception401;
import com.fastcampus.toy3.user.User;
import com.fastcampus.toy3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new Exception401("인증되지 않았습니다")
        );
        return new MyUserDetails(user);
    }
}
