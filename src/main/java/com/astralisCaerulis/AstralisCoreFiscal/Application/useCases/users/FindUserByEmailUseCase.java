package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindUserByEmailUseCase {
  private final UserRepository userRepository;

  public Optional<User> execute(String email) {
    return userRepository.findByEmail(email);
  }

}
