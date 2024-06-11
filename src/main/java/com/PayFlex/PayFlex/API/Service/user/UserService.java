package com.PayFlex.PayFlex.API.Service.user;

import com.PayFlex.PayFlex.API.DTO.UserDTO;
import com.PayFlex.PayFlex.API.Entity.Role;
import com.PayFlex.PayFlex.API.Entity.User;
import com.PayFlex.PayFlex.API.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    public ResponseEntity<UserDTO> fetchUserData(Principal principal) {

        try {
            String username=principal.getName();
            User user=userRepository.findByEmail(username).get();
            UserDTO userDTO=new UserDTO(user.getUserId(),user.getFullName(),user.getEmail(),user.getImageUrl(), (Set<Role>) user.getAuthorities());
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){

            return ResponseEntity.badRequest().body(null);
        }



    }
}
