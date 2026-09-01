package com.zest.zestApp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zest.zestApp.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByUsername(String username);

	boolean existsByUsername(String username);
}