package com.elitech.dto;

import java.util.List;

import lombok.Data;

@Data

public class UserReadDto {
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String adresse;
	List<RoleDto> roles;

}
