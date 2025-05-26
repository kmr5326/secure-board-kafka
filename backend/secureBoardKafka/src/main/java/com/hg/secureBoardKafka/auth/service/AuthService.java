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
    private final RefreshTokenService refreshTokenService;

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

        JwtTokenDto jwtTokenDto=jwtTokenProvider.createToken(authentication);

        refreshTokenService.save(
                authentication.getName(), // userId
                jwtTokenDto.getRefreshToken(),
                7 * 24 * 60 * 60 * 1000L  // 7일 in ms
        );
        return jwtTokenDto;
    }

    public JwtTokenDto refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("유효하지 않은 토큰");
        }

        String userId = jwtTokenProvider.getUserId(refreshToken);

        if (!refreshTokenService.validate(userId, refreshToken)) {
            throw new BadCredentialsException("리프레시 토큰 불일치");
        }

        // 새로운 Access Token 발급
//        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        JwtTokenDto newToken = jwtTokenProvider.createToken(authentication);

        // Redis에 새 Refresh Token 저장
        refreshTokenService.save(userId, newToken.getRefreshToken(), 7 * 24 * 60 * 60 * 1000L);

        return newToken;
    }

}
