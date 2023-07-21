package com.fastcampus.toy3.domain.user.service;


import com.fastcampus.toy3._core.erros.exception.Exception401;
import com.fastcampus.toy3._core.security.JwtTokenProvider;
import com.fastcampus.toy3._core.security.MyUserDetails;
import com.fastcampus.toy3.domain.user.User;
import com.fastcampus.toy3.domain.user.dto.UserRequest;
import com.fastcampus.toy3.domain.user.dto.UserResponse;
import com.fastcampus.toy3.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional // Springframework Transaction (주의)
    public UserResponse.JoinDTO join(UserRequest.JoinDTO joinDTO) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinDTO.getUsername());
        if (userOP.isPresent()) {
            //throw new Exception400("username", "동일한 유저네임이 존재합니다");
            System.out.println("동일한 유저네임이 존재합니다");
        }

        // 2. 회원가입
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        User userPS = userRepository.save(joinDTO.toEntity());

        // 3. DTO 응답
        return new UserResponse.JoinDTO(userPS);
    }

    @Transactional // 변경감지 하지 않음
    public User login(UserRequest.LoginDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            // myUserDetails에서 사용자 이름을 가져와 해당 사용자를 데이터베이스에서 찾습니다.
            return userRepository.findByUsername(myUserDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("유저를 찾지 못했습니다.!"));

        }catch (Exception e){
            throw new Exception401("인증되지 않았습니다");
        }
    }
}
