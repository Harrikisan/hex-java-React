package com.casestudy.AmazeCare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Service.DoctorService;

@RestController
@RequestMapping("api/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	/*
	 * AIM: add doctor
	 * PATH: api/doctor/add
	 * METHOD: POST 
	 * PARAM: doctor <- request body
	 * EXPECTED: doctor
	 * */
	@PostMapping("/add")
	public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor){
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.addDoctor(doctor));
	}
	
	
}
