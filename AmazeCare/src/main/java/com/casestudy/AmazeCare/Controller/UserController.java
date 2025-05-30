package com.casestudy.AmazeCare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
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
}
