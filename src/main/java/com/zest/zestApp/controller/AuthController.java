package com.zest.zestApp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zest.zestApp.dao.UserDao;
import com.zest.zestApp.dto.UserDto;
import com.zest.zestApp.entity.UserEntity;
import com.zest.zestApp.security.JwtUtil;
import com.zest.zestApp.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	private final UserService userService;
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthController(UserService userService, UserDao userDao, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userService = userService;
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
		UserDto createdUser = userService.registerUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
		String username = body.get("username");
		String password = body.get("password");

		Optional<UserEntity> userOpt = userDao.findByUsername(username);

		if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
			UserEntity user = userOpt.get();
			String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());
			String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

			Map<String, String> response = new HashMap<>();
			response.put("accessToken", accessToken);
			response.put("refreshToken", refreshToken);

			return ResponseEntity.ok(response);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> body) {
		String refreshToken = body.get("refreshToken");

		if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
			String username = jwtUtil.extractUsername(refreshToken);
			Optional<UserEntity> userOpt = userDao.findByUsername(username);

			if (userOpt.isPresent()) {
				UserEntity user = userOpt.get();
				// Token Rotation: Generate a new access and refresh token
				String newAccessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());
				String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());

				Map<String, String> response = new HashMap<>();
				response.put("accessToken", newAccessToken);
				response.put("refreshToken", newRefreshToken);

				return ResponseEntity.ok(response);
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}