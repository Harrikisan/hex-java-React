package com.hex.CodingChallenge.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hex.CodingChallenge.Dto.PatientMedicalRecordDto;

import com.hex.CodingChallenge.Service.MedicalHistoryService;

@RestController
@RequestMapping("/api/history")
public class MedicalHistoryController {

	@Autowired
	private MedicalHistoryService medicalHistoryService;
	
	/*
	 * AIM:Add patient with medical history and user details
	 * PATH:/api/patient/add
	 * METHOD:POST
	 * PARAMS:patient, medical details <-request body
	 * EXPECTED: patient
	 * */
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody PatientMedicalRecordDto patDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalHistoryService.add(patDto));
	}
	
	/*
	 * AIM:Get redord by id
	 * PATH:/api/patient/get
	 * METHOD:GET
	 * PARAMS:record_id <-path variable
	 * EXPECTED: record
	 * */
	@GetMapping("/get")
	public ResponseEntity<?> get(@RequestParam int record_id){
		return ResponseEntity.status(HttpStatus.OK).body(medicalHistoryService.getById(record_id));
	}
}
