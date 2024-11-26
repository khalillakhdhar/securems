
package com.elitech.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.elitech.dto.UserDto;
import com.elitech.dto.UserReadDto;
import com.elitech.model.Role;
import com.elitech.model.User;
import com.elitech.model.mapper.UserMapper;
import com.elitech.repository.RoleRepository;
import com.elitech.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Récupérer tous les utilisateurs
    public List<UserReadDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toReadDto)
                .collect(Collectors.toList());
    }

    // Récupérer un utilisateur par email
    public UserDto getUserByEmail(String email) {
        return UserMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'email : " + email)));
    }

    // Récupérer un utilisateur par ID
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'ID : " + id));
        return UserMapper.toDto(user);
    }

    // Créer un nouvel utilisateur
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            User savedUser = userRepository.save(user);
            return UserMapper.toDto(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur : " + e.getMessage());
        }
    }

    // Charger un utilisateur par email (pour Spring Security)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepository.findByEmail(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'email : " + username));
    }

    // Assigner un rôle à un utilisateur
    public void assignRoleToUser(int userId, int roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'ID : " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rôle introuvable avec l'ID : " + roleId));

        try {
            user.getRoles().add(role);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'assignation du rôle à l'utilisateur : " + e.getMessage());
        }
    }
    public void deleteUserById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UsernameNotFoundException("User not found with ID: " + id);
        }
    }
}

