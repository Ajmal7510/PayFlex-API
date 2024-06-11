package com.PayFlex.PayFlex.API.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenDTO {
    private String accessToken;
    private String refreshToken;
}
