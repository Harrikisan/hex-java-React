package com.casestudy.AmazeCare.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Service.UserService;
import com.casestudy.AmazeCare.Util.JwtUtil;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	/*
	 * AIM: Add a user
	 * PATH: api/user/add
	 * METHOD: POST
	 * PARAMS: user <- request body
	 * EXPECTED: User
	 * */
	@PostMapping("/signup")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@GetMapping("/token")
	public ResponseEntity<?> getToken(Principal principal) {
		try {
		String token =jwtUtil.createToken(principal.getName()); 
		return ResponseEntity.status(HttpStatus.OK).body(token);
		}
		catch(Exception e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
}
