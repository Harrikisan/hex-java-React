package com.hex.CodingChallenge.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hex.CodingChallenge.Service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	/*
	 * AIM: to book appointment 
	 * PATH:/api/appointment/add
	 * METHOD: POST
	 * PARAMS: patient_id,doctor_id <-path variable
	 * EXPECTED: patient
	 * */
	@PostMapping("/add/{patient_id}/{doctor_id}")
	public ResponseEntity<?> add(@PathVariable int patient_id,@PathVariable int doctor_id){
		return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.add(patient_id,doctor_id));
	}
}
