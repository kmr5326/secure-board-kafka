package com.hg.secureBoardKafka.auth.service;

import com.hg.secureBoardKafka.auth.JwtTokenProvider;
import com.hg.secureBoardKafka.auth.dto.request.LoginRequestDto;
import com.hg.secureBoardKafka.auth.dto.response.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenDto login(LoginRequestDto loginRequestDto){
        UserDetails user = customUserDetailsService.loadUserByUsername(loginRequestDto.userId());

        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 틀렸습니다.");
        }


        Authentication authentication= authenticationManagerBuilder.getObject().authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.userId(),
                        loginRequestDto.password()
                )
        );
        return jwtTokenProvider.createToken(authentication);
    }
}
