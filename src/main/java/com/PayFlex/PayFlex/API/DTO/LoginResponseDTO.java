package com.PayFlex.PayFlex.API.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class LoginResponseDTO {

    private UserDTO user;
    private String accessToken;
    private String refreshToken;
}
