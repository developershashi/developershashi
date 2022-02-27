package com.shashi.wirelesscardemo.mapper;

import com.shashi.wirelesscardemo.models.User;
import com.shashi.wirelesscardemo.pojo.UserDto;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserDto> map(List<User> users);
    User dtoToEntityMapper(UserDto userDto);
    UserDto entityToDtoMapper(User user);

}
