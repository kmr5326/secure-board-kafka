package com.hg.secureBoardKafka.user.service;

import com.hg.secureBoardKafka.auth.JwtTokenProvider;
import com.hg.secureBoardKafka.auth.dto.response.JwtTokenDto;
import com.hg.secureBoardKafka.user.dto.request.SignUpRequestDto;
import com.hg.secureBoardKafka.user.entity.User;
import com.hg.secureBoardKafka.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenDto signUp(SignUpRequestDto requestDto){
        if(userRepository.existsByUserId(requestDto.userId())){
            throw new IllegalArgumentException("이미 존재하는 uesrId입니다.");
        }

        String encodedPassword= passwordEncoder.encode(requestDto.password());

        log.info("encodedPw: {}",encodedPassword);

        User user= User.builder()
                .userId(requestDto.userId())
                .password(encodedPassword)
                .role("ROLE_USER")
                .build();

        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUserId(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        return jwtTokenProvider.createToken(authentication);
    }
}
