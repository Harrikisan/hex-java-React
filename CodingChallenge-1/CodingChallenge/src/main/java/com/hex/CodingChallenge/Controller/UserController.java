package com.hex.CodingChallenge.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hex.CodingChallenge.Model.User;
import com.hex.CodingChallenge.Service.UserService;
import com.hex.CodingChallenge.Util.JwtUtil;

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
		return ResponseEntity.status(HttpStatus.OK).body(userService.getToken(principal));
	}
	
	/*
	 * AIM: Get User
	 * PATH: api/user/details
	 * PARAMS: principal
	 * METHOD: GET
	 * EXPECTED: User
	 * */
	@GetMapping("/details")
	public ResponseEntity<?> getLoggedInUserDetails(Principal principal){
		String username=principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo(username));
	}
}
