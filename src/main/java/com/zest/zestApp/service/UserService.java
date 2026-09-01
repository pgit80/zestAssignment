package com.zest.zestApp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zest.zestApp.dao.UserDao;
import com.zest.zestApp.dto.UserDto;
import com.zest.zestApp.entity.UserEntity;

@Service
public class UserService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	public UserDto registerUser(UserDto dto) {
		if (userDao.existsByUsername(dto.getUsername())) {
			throw new RuntimeException("Username is already taken");
		}

		UserEntity entity = new UserEntity();
		entity.setUsername(dto.getUsername());
		// Hash password before saving to DB
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		// Default role to ROLE_USER if none specified
		entity.setRole(dto.getRole() != null ? dto.getRole() : "ROLE_USER");

		UserEntity saved = userDao.save(entity);

		UserDto response = new UserDto();
		response.setId(saved.getId());
		response.setUsername(saved.getUsername());
		response.setRole(saved.getRole());
		return response;
	}
}