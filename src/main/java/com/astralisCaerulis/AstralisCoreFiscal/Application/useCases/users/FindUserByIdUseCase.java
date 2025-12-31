package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users;

import java.lang.StackWalker.Option;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase {
  private final UserRepository userRepository;

  public Optional<User> execute(UUID id) {
    return userRepository.findById(id);
  }
}
