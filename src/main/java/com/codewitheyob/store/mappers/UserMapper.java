package com.codewitheyob.store.mappers;

import com.codewitheyob.store.dtos.RegisterUserRequest;
import com.codewitheyob.store.dtos.UserDto;
import com.codewitheyob.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel= "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest user);
}
