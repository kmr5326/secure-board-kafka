package com.hg.secureBoardKafka.user.controller;

import com.hg.secureBoardKafka.auth.dto.JwtTokenDto;
import com.hg.secureBoardKafka.user.dto.request.SignUpRequestDto;
import com.hg.secureBoardKafka.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    ResponseEntity<JwtTokenDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        log.info("sign up request: {}",signUpRequestDto);
        JwtTokenDto jwtTokenDto=userService.signUp(signUpRequestDto);
        return ResponseEntity.ok(jwtTokenDto);
    }
}
