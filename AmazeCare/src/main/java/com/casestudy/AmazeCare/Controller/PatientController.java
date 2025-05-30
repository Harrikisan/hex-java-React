package com.casestudy.AmazeCare.Controller;



import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Service.PatientService;



@RestController
@RequestMapping("/api/patient")
public class PatientController {

	@Autowired
	public PatientService patientService;
	
	/*
	 * AIM: add patient
	 * Path:/api/patient/insert
	 * input: Patient
	 * output: Patient 
	 */
	
	
	@PostMapping("/insert")
	public Patient insertPatient(@RequestBody Patient patient) {
		return patientService.insertPatient(patient);
	}
	
	/*
	 * AIM: edit personal info
	 * PATH:/api/patient/edit
	 * PARAMS:patient<- request body,id <-path variable
	 * EXPECTED: patient
	 * */

	@PutMapping("/edit")
	public Patient editPersonalInfo(Principal principal, @RequestBody Patient patient) {
		String username=principal.getName();
		return patientService.editPatientInfo(username,patient);
	}
	
	
	/*
	 * AIM: get personal info
	 * PATH:/api/patient/get
	 * PARAMS:principle
	 * EXPECTED: patient
	 * */
	
	@GetMapping("/get")
	public ResponseEntity<?> get(Principal principal) {
		String username=principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getName(username));
	}
}
