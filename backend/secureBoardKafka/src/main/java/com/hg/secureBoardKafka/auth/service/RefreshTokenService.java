package com.hg.secureBoardKafka.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "RT:";

    public void save(String userId, String refreshToken, long expirationMs) {
        redisTemplate.opsForValue().set(PREFIX + userId, refreshToken, expirationMs, TimeUnit.MILLISECONDS);
    }

    public String get(String userId) {
        return redisTemplate.opsForValue().get(PREFIX + userId);
    }

    public void delete(String userId) {
        redisTemplate.delete(PREFIX + userId);
    }

    public boolean validate(String userId, String requestToken) {
        String storedToken = get(userId);
        return storedToken != null && storedToken.equals(requestToken);
    }
}
