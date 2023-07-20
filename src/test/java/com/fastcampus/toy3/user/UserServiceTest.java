package com.fastcampus.toy3.user;

import com.fastcampus.toy3.domain.user.User;
import com.fastcampus.toy3.domain.user.dto.UserRequest;
import com.fastcampus.toy3.domain.user.dto.UserResponse;
import com.fastcampus.toy3.domain.user.repository.UserRepository;
import com.fastcampus.toy3.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


}
