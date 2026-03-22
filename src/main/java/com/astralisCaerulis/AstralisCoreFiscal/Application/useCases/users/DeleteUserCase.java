package com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users;

import org.springframework.stereotype.Service;

import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteUserCase {
  private final UserRepository userRepository;

  public void execute(String id) {
    userRepository.deleteById(java.util.UUID.fromString(id));
  }
}
