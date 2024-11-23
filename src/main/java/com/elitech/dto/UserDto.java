package com.elitech.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private String adresse;
	List<RoleDto> roles;
	
}
