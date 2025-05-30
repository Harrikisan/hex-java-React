package com.casestudy.AmazeCare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Service.LabService;

@RestController
@RequestMapping("api/lab")
public class LabController {

	@Autowired
	private LabService labService;
	
	/*
	 * AIM: add lab
	 * METHOD:POST
	 * PATH: api/lab/add
	 * PARAMS: lab <-request bdy
	 * EXPECTED: lab
	 * */
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Lab lab){
		return ResponseEntity.status(HttpStatus.CREATED).body(labService.add(lab));
	}
}
