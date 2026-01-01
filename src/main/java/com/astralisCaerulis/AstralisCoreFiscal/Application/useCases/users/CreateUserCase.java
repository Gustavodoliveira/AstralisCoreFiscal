package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.user.EmailAlreadyExistsException;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User execute(User user) {
    // Verificar se email já está em uso
    userRepository.findByEmail(user.getEmail())
        .ifPresent(existingUser -> {
          throw new EmailAlreadyExistsException("Email Already exists: " + user.getEmail());
        });

    String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
    User userToCreate = new User(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPhone(),
        hashedPassword);

    return userRepository.save(userToCreate);
  }
}
