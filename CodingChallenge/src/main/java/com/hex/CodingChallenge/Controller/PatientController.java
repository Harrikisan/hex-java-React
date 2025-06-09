package com.hex.CodingChallenge.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hex.CodingChallenge.Service.PatientService;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	/*
	 * AIM: get all patients by doctor_id
	 * METHOD:GET
	 * PATH:/api/patient/getall
	 * Params: doctor_id <-request param
	 * EXPECTED: List<Patient>
	 * */
	@GetMapping("/getByDoctor")
	public ResponseEntity<?> getBydoctor(Principal principal){
		String username=principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getByDoctor(username));
	}
}
