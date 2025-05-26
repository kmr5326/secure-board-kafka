package com.hg.secureBoardKafka.auth.controller;

import com.hg.secureBoardKafka.auth.dto.request.LoginRequestDto;
import com.hg.secureBoardKafka.auth.dto.request.RefreshTokenRequestDto;
import com.hg.secureBoardKafka.auth.dto.response.JwtTokenDto;
import com.hg.secureBoardKafka.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginRequestDto loginRequest) {
        JwtTokenDto tokenDto = authService.login(loginRequest);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtTokenDto> refresh(@RequestBody RefreshTokenRequestDto requestDto) {
        JwtTokenDto token = authService.refreshToken(requestDto.refreshToken());
        return ResponseEntity.ok(token);
    }

}
