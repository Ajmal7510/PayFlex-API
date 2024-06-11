package com.PayFlex.PayFlex.API.DTO;

import com.PayFlex.PayFlex.API.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String fullName;
    private String email;
    private String imageUrl;
    private Set<Role> authorities;


}
