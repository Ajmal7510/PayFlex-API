package com.PayFlex.PayFlex.API.Controller;

import com.PayFlex.PayFlex.API.DTO.UserDTO;
import com.PayFlex.PayFlex.API.Entity.User;
import com.PayFlex.PayFlex.API.Service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/home")
    public String user(){
        return "user home";
    }

    @GetMapping("/fetch-user")
    public ResponseEntity<UserDTO> fetchUser(Principal principal){
        return userService.fetchUserData(principal);
    }


}
