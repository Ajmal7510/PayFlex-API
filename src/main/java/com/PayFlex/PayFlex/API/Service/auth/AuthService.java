package com.PayFlex.PayFlex.API.Service.auth;

import com.PayFlex.PayFlex.API.DTO.*;
import com.PayFlex.PayFlex.API.Entity.RefreshToken;
import com.PayFlex.PayFlex.API.Entity.Role;
import com.PayFlex.PayFlex.API.Entity.User;
import com.PayFlex.PayFlex.API.Exeption.InternalErrorCustom;
import com.PayFlex.PayFlex.API.Exeption.UserAlreadyExistsException;
import com.PayFlex.PayFlex.API.Repository.RefreshTokenRepository;
import com.PayFlex.PayFlex.API.Repository.RoleRepository;
import com.PayFlex.PayFlex.API.Repository.UserRepository;
import com.PayFlex.PayFlex.API.Service.user.UserDetailsServiceImp;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.rmi.ServerError;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private JwtDecoder jwtDecoder;
    private UserDetailsServiceImp userDetailsServiceImpl;
    private RefreshTokenRepository refreshTokenRepository;
    public ResponseEntity<String> register(RegistrationDTO registrationDTO) {
        // Check if a user with the given email already exists
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email already exists");
        }

        try {
            // Create a new user entity
            User user = new User();
            user.setFullName(registrationDTO.getFullName());
            user.setEmail(registrationDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
            user.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAd5avdba8EiOZH8lmV3XshrXx7dKRZvhx-A&s");

            // Set user role
            Role userRole = roleRepository.findByAuthority("USER");
            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);
            user.setAuthorities(authorities);

            // Save the user entity
            userRepository.save(user);
        } catch (Exception e) {
            // Log and handle the exception
            throw new InternalErrorCustom("ServerError");
        }

        return ResponseEntity.ok("Registration successful");
    }



    public ResponseEntity<LoginResponseDTO> login(LoginDTO loginDTO) {

//        try {
        System.out.println("work 2");
        System.out.println(loginDTO);
            Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
        System.out.println("work 3");
            String token=tokenService.generateJwt(auth);
        System.out.println("work 4 ");
            User user=userRepository.findByEmail(loginDTO.getEmail()).get();
            UserDTO userDTO=new UserDTO(user.getUserId(),user.getFullName(),user.getEmail(),user.getImageUrl(), (Set<Role>) user.getAuthorities());
            String accessToken = tokenService.generateJwt(auth);
            String refreshToken = tokenService.generateRefreshToken(auth);
            return ResponseEntity.ok(new  LoginResponseDTO(userDTO,accessToken,refreshToken));

//        }catch (Exception e){
//
//
//            throw new InternalErrorCustom("Iternal error ");
//        }

    }

    @Transactional
    public ResponseEntity<RefreshTokenDTO> refreshToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(refreshToken);
        if (refreshTokenOpt.isEmpty() || refreshTokenOpt.get().getExpiryDate().isBefore(Instant.now())) {
            throw new InternalErrorCustom("Invalid or expired refresh token");
        }

        User user = refreshTokenOpt.get().getUser();
        Authentication auth = userDetailsServiceImpl.getAuthentication(user.getEmail());

        String newAccessToken = tokenService.generateJwt(auth);
        String newRefreshToken = tokenService.generateRefreshToken(auth);




        return ResponseEntity.ok(new RefreshTokenDTO(newAccessToken, newRefreshToken));
    }
}
