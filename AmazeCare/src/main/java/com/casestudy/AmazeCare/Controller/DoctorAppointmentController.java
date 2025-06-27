package com.casestudy.AmazeCare.Controller;

import java.security.Principal;
import java.time.LocalDate;

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

import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Model.DoctorAppointment;
import com.casestudy.AmazeCare.Service.DoctorAppointmentService;

@RestController
@RequestMapping("/api/doctor/appointment")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorAppointmentController {

	@Autowired
	private DoctorAppointmentService doctorAppointmentService;

	/*
	 * AIM: add appointment PATH: api/doctor/appointment/add METHOD: POST
	 * PARAMS:patient_id,doctor_id,schedule_id <- path variables, doctorAppointment
	 * request body EXPECTED: DoctorAppointment
	 */
	@PostMapping("/add/{patient_id}/{doctor_id}/{schedule_id}")
	public ResponseEntity<?> addDoctorAppointment(@PathVariable int patient_id, @PathVariable int doctor_id,
			@PathVariable int schedule_id, @RequestBody DoctorAppointment doctorAppointment) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
				doctorAppointmentService.addDoctorAppointment(patient_id, doctor_id, schedule_id, doctorAppointment));
	}

	@GetMapping("/get-dates/{doctor_id}")
	public ResponseEntity<?> getAvailableDatesForDoctor(@PathVariable int doctor_id) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(doctorAppointmentService.getAvailableDatesForDoctor(doctor_id));
	}

	@GetMapping("/get-by-doctor")
	public ResponseEntity<?> getAppointmentByDoctor(Principal principal) {
		String username = principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(doctorAppointmentService.getByDoctor(username));
	}

	@GetMapping("/get-by-patient")
	public ResponseEntity<?> getAppointmentByPatient(Principal principal, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "1000") int size) {
		String username = principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(doctorAppointmentService.getAppointmentByPatient(username,page,size));
	}
	
	@PutMapping("/editStatus/{id}")
	public ResponseEntity<?> editAppointmentStatus(@RequestParam String status,
												@PathVariable int id){
		return ResponseEntity.status(HttpStatus.OK).body(doctorAppointmentService.editAppointmentStatus(status,id));
	}
	
	@GetMapping("/get-by-id/{id}")
	public ResponseEntity<?> getAppointmentById(@PathVariable int id) {
	    DoctorAppointment appointment = doctorAppointmentService.getAppointmentById(id);
	    return ResponseEntity.status(HttpStatus.OK).body(appointment);
	}


}
