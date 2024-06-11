package com.PayFlex.PayFlex.API.Service.auth;

import com.PayFlex.PayFlex.API.Entity.RefreshToken;
import com.PayFlex.PayFlex.API.Entity.User;
import com.PayFlex.PayFlex.API.Repository.RefreshTokenRepository;
import com.PayFlex.PayFlex.API.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TokenService {


    private JwtEncoder jwtEncoder;

    private JwtDecoder jwtDecoder;

    private UserRepository userRepository;

    private RefreshTokenRepository refreshTokenRepository;


    public String generateJwt(Authentication auth){
        Instant now=Instant.now();

        String scope=auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));


        JwtClaimsSet claims= JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.SECONDS)) // Access token validity
                .subject(auth.getName())
                .claim("roles", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public String generateRefreshToken(Authentication auth) {
        Instant now = Instant.now();
        String username = auth.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if the user already has a refresh token
        RefreshToken existingRefreshToken = refreshTokenRepository.findByUser(user);
        if (existingRefreshToken != null) {
            refreshTokenRepository.delete(existingRefreshToken);
        }

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS)) // Refresh token validity
                .subject(username)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(now.plus(30, ChronoUnit.DAYS));

        refreshTokenRepository.save(refreshToken);

        return token;
    }
}
