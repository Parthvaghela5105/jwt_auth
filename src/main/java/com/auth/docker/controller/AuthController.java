package com.auth.docker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.docker.entities.User;
import com.auth.docker.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService service;
	
	@PostMapping("/register")
	public User register(@RequestBody User user){
		this.service.registerUser(user);
		return user;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
	    String token = service.loginUser(user);
	    Map<String, String> response = new HashMap<>();
	    response.put("token", token);
	    return ResponseEntity.ok(response);
	}
}
