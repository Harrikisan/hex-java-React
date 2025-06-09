package com.hex.CodingChallenge.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hex.CodingChallenge.Model.Doctor;
import com.hex.CodingChallenge.Service.DoctorService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	/*
	 * AIM: add doctor
	 * PATH:/api/doctor/add
	 * METHOR: POST
	 * PARAMS: doctor <-Request body
	 * EXPECTED: doctor
	 * 
	 * */
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Doctor doctor){
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.add(doctor));
	}
}
