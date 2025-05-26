package com.hg.secureBoardKafka.user.dto.request;

import lombok.Data;

public record SignUpRequestDto (
        String userId,
        String password
){

}
