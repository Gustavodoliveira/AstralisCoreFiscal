package com.astralisCaerulis.AstralisCoreFiscal.adapters.web.controller;

import org.hibernate.sql.Delete;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.CreateUserRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.UserResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.CreateUserCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.FindUserByEmailUseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.FindUserByIdUseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.UpdateUserUseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final CreateUserCase createUserCase;
  private final UpdateUserUseCase updateUserUseCase;
  private final FindUserByEmailUseCase findUserByEmailUseCase;
  private final FindUserByIdUseCase findUserByIdUseCase;
  private final UserMapper userMapper;

  @PostMapping("/create")
  public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
    User user = userMapper.toDomain(request);
    var createdUser = createUserCase.execute(user);
    UserResponse response = userMapper.toResponse(createdUser);
    return ResponseEntity.ok(response);
  }
}
