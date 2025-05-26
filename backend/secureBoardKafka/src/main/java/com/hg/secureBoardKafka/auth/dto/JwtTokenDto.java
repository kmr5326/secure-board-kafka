package com.hg.secureBoardKafka.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class JwtTokenDto {
    private String grantType;   // Bearer
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresIn;
}
