package com.casestudy.AmazeCare.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.TestAppointment;
import com.casestudy.AmazeCare.Service.TestAppointmentService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/test/appointment")
@CrossOrigin(origins = "http://localhost:5173")
public class TestAppointmentController {

	@Autowired
	private TestAppointmentService testAppointmentService;

	/*
	 * AIM: add appointment 
	 * PATH:/api/test/appointment/add Method: POST
	 * PARAMS:patient_name,doctor_name,labname, testname,schedule <- request body
	 * Expected: Test Appointment
	 * METHOD: POST
	 */
	
	@PostMapping("/add/{patientId}/{doctorId}/{labId}/{testId}/{scheduleId}")
	public ResponseEntity<?> addTestAppointment(@PathVariable int patientId,
												@PathVariable int doctorId,
												@PathVariable int labId, 
												@PathVariable int testId,
												@PathVariable int scheduleId,
												@RequestBody TestAppointment testAppointment) {
		return ResponseEntity.status(HttpStatus.CREATED).body(testAppointmentService.addTestAppointment(patientId,
				doctorId, labId, testId, scheduleId, testAppointment));
	}
	
	/*
	 * AIM: Get appointment by id 
	 * PATH:/api/test/appointment/add Method: POST
	 * PARAMS:appointment id <- Path variable
	 * Expected: Test Appointment
	 * METHOD: POST
	 */
	
	@GetMapping("/get/{appointmentId}")
	public ResponseEntity<?> get(@PathVariable int appointmentId){
		return ResponseEntity.status(HttpStatus.OK).body(testAppointmentService.get(appointmentId));
	}
	
	/*
	 * AIM: Get all appointments 
	 * PATH:/api/test/appointment/add Method: POST
	 * PARAMS:
	 * Expected: Test Appointment
	 * METHOD: POST
	 */
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(testAppointmentService.getAll());
	}
	
	/*
	 * AIM: Get appointments by patient id
	 * PATH:/api/test/appointment/add Method: POST
	 * PARAMS:patient_Id <- path variable
	 * Expected: Test Appointment
	 * METHOD: POST
	 */
	@GetMapping("/get-by-patien_id")
	public ResponseEntity<?> getByPatientId(Principal principal){
		String username=principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(testAppointmentService.getByPatientId(username));
	}
	
	@GetMapping("/get-dates/{lab_id}/{test_id}")
	public ResponseEntity<?> getAvailableDatesForLabTest(@PathVariable int lab_id,@PathVariable int test_id){
		return ResponseEntity.status(HttpStatus.OK).body(testAppointmentService.getAvailableDatesForLabTest(lab_id,test_id));
	}
	
	@PutMapping("/edit/{appId}")
	public ResponseEntity<?> editStatus(@RequestParam String status,@PathVariable int appId){
		return ResponseEntity.status(HttpStatus.OK).body(testAppointmentService.editStatus(status,appId));
	}
}