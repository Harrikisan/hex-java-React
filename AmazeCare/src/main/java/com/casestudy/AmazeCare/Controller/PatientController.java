package com.casestudy.AmazeCare.Controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Service.PatientService;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

	@Autowired
	public PatientService patientService;

	/*
	 * AIM: add patient Path:/api/patient/insert input: Patient output: Patient
	 */

	@PostMapping("/insert")
	public Patient insertPatient(@RequestBody Patient patient) {
		return patientService.insertPatient(patient);
	}

	/*
	 * AIM: edit personal info PATH:/api/patient/edit PARAMS:patient<- request
	 * body,id <-path variable EXPECTED: patient
	 */

	@PutMapping("/edit")
	public Patient editPersonalInfo(Principal principal, @RequestBody Patient patient) {
		String username = principal.getName();
		return patientService.editPatientInfo(username, patient);
	}

	/*
	 * AIM: get personal info PATH:/api/patient/get PARAMS:principle EXPECTED:
	 * patient
	 */

	@GetMapping("/get")
	public ResponseEntity<?> get(Principal principal) {
		String username = principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getName(username));
	}

	@GetMapping("/get-by-id/{patient_id}")
	public ResponseEntity<?> getById(@PathVariable int patient_id) {
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getById(patient_id));
	}

	@GetMapping("/get-by-name")
	public ResponseEntity<?> getByName(@RequestParam String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getByName(name, page, size));
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getAll(page, size));
	}
	
	@PutMapping("/edit-status/{patient_id}")
	public ResponseEntity<?> editStatus(@PathVariable("patient_id") int patientId,
	                                    @RequestParam UserStatus status) {
	    return ResponseEntity.status(HttpStatus.OK)
	                         .body(patientService.editStatus(status, patientId));
	}
	
	@PostMapping("/upload/profile-pic")
	public ResponseEntity<?> uploadProfilePic(@RequestParam MultipartFile file, Principal principal) throws IOException {
	    String username = principal.getName();
	    return ResponseEntity.status(HttpStatus.OK).body(patientService.uploadProfilePic(file, username));
	}


}
