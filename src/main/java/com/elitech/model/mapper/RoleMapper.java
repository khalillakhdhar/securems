package com.elitech.model.mapper;


import org.modelmapper.ModelMapper;

import com.elitech.dto.RoleDto;
import com.elitech.model.Role;

public class RoleMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private RoleMapper() {
        // Private constructor to prevent instantiation
    }

    public static RoleDto toDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public static Role toEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }
}
