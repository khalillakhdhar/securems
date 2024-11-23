package com.elitech.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoleDto {
	private String roleName;
	private List<UserReadDto> users; 
}
