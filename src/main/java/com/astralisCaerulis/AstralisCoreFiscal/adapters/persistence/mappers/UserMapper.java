package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers;

import org.springframework.stereotype.Component;

import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.CreateUserRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.UpdateUserRequest;
import com.astralisCaerulis.AstralisCoreFiscal.Application.dtos.user.UserResponse;
import com.astralisCaerulis.AstralisCoreFiscal.Core.domain.models.User;
import com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.entities.UserEntity;

@Component
public class UserMapper {

  public UserEntity toEntity(User user) {
    if (user == null) {
      return null;
    }

    UserEntity entity = new UserEntity();
    entity.setId(user.getId());
    entity.setName(user.getName());
    entity.setEmail(user.getEmail());
    entity.setPhone(user.getPhone());
    entity.setPassword(user.getPasswordHash());

    return entity;
  }

  public User toDomain(UserEntity entity) {
    if (entity == null) {
      return null;
    }

    return new User(
        entity.getId(),
        entity.getName(),
        entity.getEmail(),
        entity.getPhone(),
        entity.getPassword());
  }

  // DTO -> Domain
  public User toDomain(CreateUserRequest request) {
    if (request == null) {
      return null;
    }

    return new User(
        null,
        request.getName(),
        request.getEmail(),
        request.getPhone(),
        request.getPassword());
  }

  public User toDomain(UpdateUserRequest request) {
    if (request == null) {
      return null;
    }

    return new User(
        null,
        request.getName(),
        request.getEmail(),
        request.getPhone(),
        request.getPassword());
  }

  // Domain -> DTO Response
  public UserResponse toResponse(User user) {
    if (user == null) {
      return null;
    }

    return new UserResponse(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPhone());
  }
}
