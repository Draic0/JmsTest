package com.example.jms.mapper;

import jms.dto.UserDto;
import jms.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "secondName", source = "secondName")
    @Mapping(target = "birthDate", source = "birthDate")
    UserDto toDto(User model);

    @InheritInverseConfiguration
    User toModel(UserDto dto, @MappingTarget User model);

}
