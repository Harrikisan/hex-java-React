package com.hex.CodingChallenge.Service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.hex.CodingChallenge.Model.User;
import com.hex.CodingChallenge.Repository.UserRepository;
import com.hex.CodingChallenge.Util.JwtUtil;



@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}



	public User addUser(User user) {
		//encrypt password
		String plaintext=user.getPassword();
		String ciphertext=passwordEncoder.encode(plaintext);
		
		//Set encrypted password
		user.setPassword(ciphertext);
		
		//Save to DB
		return userRepository.save(user);
	}



	public Object getToken(Principal principal) {
		
		try {
			String token =jwtUtil.createToken(principal.getName()); 
			return ResponseEntity.status(HttpStatus.OK).body(token);
			}
			catch(Exception e){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
			}
	}


	public Object getUserInfo(String username) {
		User user=userRepository.getByUsername(username);
		switch(user.getRole()) {
		
		}
		
		return null;
	}

}
