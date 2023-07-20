package com.fastcampus.toy3.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String showJoin(Model model) {
        model.addAttribute("joinDTO", new UserRequest.JoinDTO());
        return "user/join";
    }

//    @PostMapping("/join")
//    public String join(@RequestBody @Valid UserRequest.JoinDTO joinDTO, Errors errors) {
//        if(errors.hasErrors()){
//            return "user/join";
//        }
//        UserResponse.JoinDTO responseBody = userService.join(joinDTO);
//        System.out.println("회원가입 완료");
//        // 회원가입 처리 로직
//        return "redirect:/login";
//    }

    @PostMapping("/join")
    public String join(@ModelAttribute("joinDTO") @Valid UserRequest.JoinDTO joinDTO, Errors errors, RedirectAttributes redirectAttributes) {
        UserResponse.JoinDTO responseBody = userService.join(joinDTO);
        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 성공적으로 완료되었습니다. 로그인 해주세요!");
        return "redirect:/login"; // 리다이렉트할 페이지로 변경해주세요.
    }

    @GetMapping({"/login", "/"})
    public String showLogin(Model model) {
        model.addAttribute("loginDTO", new UserRequest.LoginDTO());
        return "user/login";
    }

    @PostMapping({"/login", "/"})
    public String login(@ModelAttribute("loginDTO") @Valid UserRequest.LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/login";
        }

        String jwt = userService.login(loginDTO);
        // 로그인 처리 로직
        return "redirect:/home";
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

    @GetMapping("/home")
    public String getHomePage(Principal principal, Model model) {
        // 현재 로그인한 사용자의 이름을 가져와서 모델에 추가
        model.addAttribute("username", principal.getName());

        return "user/home";
    }

}
