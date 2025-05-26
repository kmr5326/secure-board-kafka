package com.hg.secureBoardKafka.auth.dto.request;

public record LoginRequestDto (
        String userId,
        String password
){
}
