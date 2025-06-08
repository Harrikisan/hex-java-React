package com.casestudy.AmazeCare.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Enum.UserStatus;
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
	
	/*
	 * AIM: Get All doctors
	 * PATH: api/doctor/get-all
	 * METHOD: GET 
	 * PARAM: 
	 * EXPECTED: List<doctorListingDto>
	 * */
	
	@GetMapping("/get-all-listing")
	public ResponseEntity<?> getAllActive(@RequestParam(defaultValue = "0") int page,
										  @RequestParam(defaultValue = "10") int size){
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.getAllActive(page,size));
	}
	
	/*
	 * AIM: Get doctor By Id
	 * PATH: api/doctor/get-by-id
	 * METHOD: GET 
	 * PARAM: doctor-id <- Request param
	 * EXPECTED: doctor
	 * */
	@GetMapping("/get-by-id/{doctor_id}")
	public ResponseEntity<?> getById(@PathVariable int doctor_id){
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.getById(doctor_id));
	}
	
	/*
	 * AIM: Get doctor By Name
	 * PATH: api/doctor/get-by-name
	 * METHOD: GET 
	 * PARAM: doctor-name <- Path variable
	 * EXPECTED: doctor
	 * */
	@GetMapping("/get-by-name-listing")
	public ResponseEntity<?> getByName(@RequestParam String name,
									   @RequestParam(defaultValue = "0") int page,
									   @RequestParam(defaultValue = "10") int size){
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.getByName(name,page,size));
	}
	
	/*
	 * AIM: Get doctor By specilization
	 * PATH: api/doctor/get-by-name
	 * METHOD: GET 
	 * PARAM: speclization,page,size <-Request param
	 * EXPECTED: doctor
	 * */
	@GetMapping("/get-by-speclization-listing")
	public ResponseEntity<?> getBySpecilization(@RequestParam String speclization,
									   			@RequestParam(defaultValue = "0") int page,
									   			@RequestParam(defaultValue = "10") int size){
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.getBySpecilization(speclization,page,size));
	}
	
	/*
	 * AIM: Get doctor By name for search
	 * PATH: api/doctor/get-by-name
	 * METHOD: GET 
	 * PARAM: name <-request param
	 * EXPECTED: doctor
	 * */
	@GetMapping("/get-by-name-search")
	public ResponseEntity<?> getByNameSearch(@RequestParam String name){
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.getByNameSearch(name));
	}
	
	/*
	 * AIM: Update doctor detaile
	 * PATH: api/doctor/get-by-name
	 * METHOD: PUT 
	 * PARAM: principal |doctor <-Request body
	 * EXPECTED: doctor
	 * */
	@PutMapping("/update-info")
	public ResponseEntity<?> UpdateInfo(Principal principal,@RequestBody Doctor doctor){
		String username=principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.updateInfo(username,doctor));
	}
	
	/*
	 * AIM: Update doctor status 
	 * PATH: api/doctor/get-by-name
	 * METHOD: PUT 
	 * PARAM: doctor_id <-Request body
	 * EXPECTED: doctor
	 * */
	@PutMapping("/edit-status/{doctor_id}")
	public ResponseEntity<?> InactiveDoctor(@PathVariable int doctor_id,@RequestParam UserStatus status){
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.EditStatus(doctor_id,status));
	}
	
}
