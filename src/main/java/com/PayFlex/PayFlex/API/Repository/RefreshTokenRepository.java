package com.PayFlex.PayFlex.API.Repository;

import com.PayFlex.PayFlex.API.Entity.RefreshToken;
import com.PayFlex.PayFlex.API.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    void deleteByToken(String token);
    void deleteByUser(User user);

    Optional<RefreshToken> findByToken(String refreshToken);

    RefreshToken findByUser(User user);
}
