package com.auth.docker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.docker.dao.UserDao;
import com.auth.docker.entities.User;
import com.auth.docker.jwt.JwtUtil;

@Service
public class AuthService {
	@Autowired
	private UserDao dao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public User registerUser(User user){
		user.setPassword(encoder.encode(user.getPassword()));
		this.dao.save(user);
		return user;
	}
	
	public User getUserByUsername(User user){
		User existingUser = this.dao.findByUsername(user.getUsername());
		
		if(existingUser == null)
		{
			throw new RuntimeException("User not found with username: "+ user.getUsername());
		}
		
		boolean matches = encoder.matches(user.getPassword(), existingUser.getPassword());
		
		if(!matches){
			throw new RuntimeException("Invalid Password");
		}
		
		return existingUser;
	}
	
	public String loginUser(User user){
		User existingUser = dao.findByUsername(user.getUsername());
		if (existingUser == null || !encoder.matches(user.getPassword(), existingUser.getPassword())) {
	        throw new RuntimeException("Invalid username or password");
	    }
		return jwtUtil.generateToken(existingUser.getUsername());
	}
}
