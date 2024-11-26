package com.elitech.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String roleName;
@ManyToMany(mappedBy = "roles")
private Set<User> users;

public Role(String roleName) {
	super();
	this.roleName = roleName;
}


}
