package com.PayFlex.PayFlex.API.Service.user;

import com.PayFlex.PayFlex.API.Entity.User;
import com.PayFlex.PayFlex.API.Exeption.UsernameNotExist;
import com.PayFlex.PayFlex.API.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {


    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotExist{
        Optional<User> user=userRepository.findByEmail(username);
        System.out.println(user.isEmpty());
        if(user.isEmpty()) {throw new UsernameNotExist("User Not Exist please Register");}
        return user.get();
    }
    public Authentication getAuthentication(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
