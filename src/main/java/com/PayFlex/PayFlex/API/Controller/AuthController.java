package com.PayFlex.PayFlex.API.Controller;

import com.PayFlex.PayFlex.API.DTO.LoginDTO;
import com.PayFlex.PayFlex.API.DTO.LoginResponseDTO;
import com.PayFlex.PayFlex.API.DTO.RefreshTokenDTO;
import com.PayFlex.PayFlex.API.DTO.RegistrationDTO;
import com.PayFlex.PayFlex.API.Service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> RegisterUser(@RequestBody RegistrationDTO registrationDTO){
        System.out.println(registrationDTO);
        return authService.register(registrationDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO){
        System.out.println("work 1");
        return authService.login(loginDTO);
    }



    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenDTO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        System.out.println("its work");
        return authService.refreshToken(refreshToken);
    }

//    @PostMapping("/refresh-token")
//    public String refreshToken(@RequestParam("refreshToken") String refreshToken) {
//        System.out.println(refreshToken);
//
//        System.out.println("its work");
//       return "its work";
//    }


}
