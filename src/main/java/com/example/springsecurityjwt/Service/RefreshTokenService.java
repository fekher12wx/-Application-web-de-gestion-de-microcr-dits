package com.example.springsecurityjwt.Service;

import com.example.springsecurityjwt.Models.RefreshToken;
import com.example.springsecurityjwt.Repository.RefreshTokenRepository;
import com.example.springsecurityjwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service

public class RefreshTokenService {
    @Value("${fekher.app.jwtRefreshExpirationMs}")
    private long jwtRefreshExpirationMs;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken=refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
}
