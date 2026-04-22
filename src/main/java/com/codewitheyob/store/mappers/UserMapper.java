package com.codewitheyob.store.mappers;

import com.codewitheyob.store.dtos.RegisterUserRequest;
import com.codewitheyob.store.dtos.UpdateUserRequest;
import com.codewitheyob.store.dtos.UserDto;
import com.codewitheyob.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel= "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest user);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
