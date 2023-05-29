package com.vti.security.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vti.entity.RefreshToken;
import com.vti.exceptions.TokenRefreshException;
import com.vti.repository.RefreshTokenRepository;
import com.vti.repository.UserRepository;

@Service
public class RefreshTokenService {
	@Value("${GenuineDignity.app.jwtRefreshExpirationMs}")
	private int refreshTokenDurationMs;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	public Optional<RefreshToken> findByRefreshToken(String token) {
		return refreshTokenRepository.findByRefreshToken(token);
	}

	public RefreshToken createRefreshToken(int userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findById(userId).get());

		// Convert Instant to Date
		Date expiryDate = Date.from(Instant.now().plusMillis(refreshTokenDurationMs));

		refreshToken.setExpiryDate(expiryDate);
		refreshToken.setRefreshToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		Date expiryDate = token.getExpiryDate();
		Instant expiryInstant = expiryDate.toInstant();

		if (expiryInstant.compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getRefreshToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	@Transactional
	public int deleteByUserId(int userId) {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}
}