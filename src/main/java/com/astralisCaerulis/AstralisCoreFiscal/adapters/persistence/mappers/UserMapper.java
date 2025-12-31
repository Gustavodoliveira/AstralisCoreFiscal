package com.astralisCaerulis.AstralisCoreFiscal.adapters.persistence.mappers;

import org.springframework.stereotype.Component;

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
}
