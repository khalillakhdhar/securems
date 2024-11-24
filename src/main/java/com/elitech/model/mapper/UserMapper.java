package com.elitech.model.mapper;

import org.modelmapper.ModelMapper;

import com.elitech.dto.UserDto;
import com.elitech.dto.UserReadDto;
import com.elitech.model.User;

public class UserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private UserMapper() {
        // Private constructor to prevent instantiation
    }

    public static UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public static UserReadDto toReadDto(User user) {
        return modelMapper.map(user, UserReadDto.class);
    }

    public static User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
    public static User toReadEntity(UserReadDto userReadDto)
    {
    	return modelMapper.map(userReadDto, User.class);
    	
    }
}