package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User execute(UUID userId, User updatedUser) {
    User existingUser = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    // Atualizar campos
    if (updatedUser.getName() != null) {
      existingUser.setName(updatedUser.getName());
    }

    if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(existingUser.getEmail())) {
      // Verificar se email já está em uso
      userRepository.findByEmail(updatedUser.getEmail())
          .ifPresent(user -> {
            throw new RuntimeException("Email already in use: " + updatedUser.getEmail());
          });
      existingUser.setEmail(updatedUser.getEmail());
    }

    if (updatedUser.getPhone() != null) {
      existingUser.setPhone(updatedUser.getPhone());
    }

    if (updatedUser.getPasswordHash() != null && !updatedUser.getPasswordHash().isEmpty()) {
      existingUser.setPasswordHash(passwordEncoder.encode(updatedUser.getPasswordHash()));
    }

    return userRepository.save(existingUser);
  }
}
