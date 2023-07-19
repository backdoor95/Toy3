package com.fastcampus.toy3.user;

import com.fastcampus.toy3._core.security.JwtTokenProvider;
import com.fastcampus.toy3._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String showJoin(Model model) {
        model.addAttribute("joinDTO", new UserRequest.JoinDTO());
        System.out.println("getMapping user/join start");
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@RequestBody @Valid UserRequest.JoinDTO joinDTO, Errors errors) {
        UserResponse.JoinDTO responseBody = userService.join(joinDTO);
        return "user/join";
    }

    @PostMapping({"/login", "/"})
    public String login(@RequestBody @Valid UserRequest.LoginDTO loginDTO, Errors errors) {
        String jwt = userService.login(loginDTO);
        return "redirect:user/home";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 현재 세션을 무효화하고 로그아웃 처리
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

    @GetMapping("user/home")
    public String getHomePage(Principal principal, Model model) {
        // 현재 로그인한 사용자의 이름을 가져와서 모델에 추가
        model.addAttribute("username", principal.getName());

        return "home";
    }

}
