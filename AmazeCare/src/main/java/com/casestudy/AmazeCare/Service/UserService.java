package com.casestudy.AmazeCare.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;


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
	
	
}
