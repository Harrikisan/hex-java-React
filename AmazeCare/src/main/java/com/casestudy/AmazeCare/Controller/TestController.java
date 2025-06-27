package com.casestudy.AmazeCare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.Test;
import com.casestudy.AmazeCare.Service.TestService;

@RestController
@RequestMapping("api/lab/test")
@CrossOrigin(origins = "http://localhost:5173")
public class TestController {

	@Autowired
	private TestService testService;
	
	/*
	 * AIM: add test
	 * METHOD : POST
	 * PATH: /api/lab/test/add
	 * PARAS: Lab <-path value, test <-request body
	 * EXPECTED: Lab
	 * */
	
	@PostMapping("/add/{lab_id}")
	public ResponseEntity<?> add(@PathVariable int lab_id,@RequestBody Test test){
		return ResponseEntity.status(HttpStatus.CREATED).body(testService.add(lab_id,test));
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(testService.getAll());
	}
	
	@GetMapping("/getByLab/{labId}")
	public ResponseEntity<?> getByLabID(@PathVariable int labId){
		return ResponseEntity.status(HttpStatus.OK).body(testService.getByLab(labId));
	}
}
