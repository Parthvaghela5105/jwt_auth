package com.auth.docker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.docker.entities.User;


public interface UserDao extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
