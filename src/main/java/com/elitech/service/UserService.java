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
import com.elitech.model.User;
import com.elitech.model.mapper.UserMapper;
import com.elitech.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service

public class UserService implements UserDetailsService {
		@Autowired
	 UserRepository userRepository;
		@Autowired
	 PasswordEncoder passwordEncoder;
	public List<UserReadDto> getAllUsers()
	{
		List<User> users=userRepository.findAll();
		return users.stream()
				.map(UserMapper::toReadDto)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userInfo=userRepository.findByEmail(username);
		return userInfo.map(UserInfoDetails::new)
				.orElseThrow(()-> new UsernameNotFoundException("User not found with email: "+username));
	}
	public UserDto getUserByEmail(String email) {
	    return UserMapper.toDto(userRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email)));
	}
	public UserReadDto getUserById(int id) {
	    User user= userRepository.findById(id)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
	    return UserMapper.toReadDto(user);
	}
	public UserDto createUser(UserDto userDto)
	{
		User user=UserMapper.toEntity(userDto);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser=userRepository.save(user);
		return UserMapper.toDto(savedUser);
	}

}
