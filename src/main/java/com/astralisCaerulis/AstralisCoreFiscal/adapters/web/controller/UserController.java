package com.astralisCaerulis.AstralisCoreFiscal.adapters.web.controller;

import java.util.UUID;

import org.hibernate.sql.Delete;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.CreateUserRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.LoginRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.LoginResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.UserResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.CreateUserCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.DeleteUserCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.FindUserByEmailUseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.FindUserByIdUseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.LoginUserCase;
import com.astralisCaerulis.AstralisCoreFiscal.Application.useCases.users.UpdateUserUseCase;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers.UserMapper;
import com.astralisCaerulis.AstralisCoreFiscal.infrastructure.config.UserDetailsServiceImpl;
import com.astralisCaerulis.AstralisCoreFiscal.infrastructure.security.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final CreateUserCase createUserCase;
  private final UpdateUserUseCase updateUserUseCase;
  private final FindUserByEmailUseCase findUserByEmailUseCase;
  private final FindUserByIdUseCase findUserByIdUseCase;
  private final LoginUserCase loginUserCase;
  private final DeleteUserCase deleteUserCase;
  private final UserMapper userMapper;
  private final JwtService jwtService;
  private final UserDetailsServiceImpl userDetailsService;

  @PostMapping("/create")
  public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
    User user = userMapper.toDomain(request);
    User createdUser = createUserCase.execute(user);

    // Gerar token JWT
    var userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());
    String token = jwtService.generateToken(userDetails);

    UserResponse response = userMapper.toResponse(createdUser);
    response.setToken(token);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    LoginResponse response = loginUserCase.execute(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/by-id/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
    var newId = UUID.fromString(id);
    var user = findUserByIdUseCase.execute(newId);
    UserResponse response = userMapper.toResponse(user.orElse(null));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/by-email/{email}")
  public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
    var user = findUserByEmailUseCase.execute(email);
    UserResponse response = userMapper.toResponse(user.orElse(null));
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable String id) {
    deleteUserCase.execute(id);
    return ResponseEntity.noContent().build();
  }
}