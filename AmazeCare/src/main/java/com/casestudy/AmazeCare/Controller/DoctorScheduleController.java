package com.casestudy.AmazeCare.Controller;

import java.security.Principal;

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

import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.DoctorSchedule;
import com.casestudy.AmazeCare.Service.DoctorScheduleService;

@RestController
@RequestMapping("api/doctor/schedule")
public class DoctorScheduleController {

	@Autowired
	private DoctorScheduleService doctorScheduleService;

	/*
	 * AIM: Add Schedule PATH: api/doctor/schedule/add METHOD: POST PARAMS:
	 * doctor_id <- path variable, schedule<- request body EXPECTED: schedule
	 */
	@PostMapping("/add/")
	public ResponseEntity<?> addSchedule(Principal principal, @RequestBody DoctorSchedule doctorSchedule) {
		String username = principal.getName();
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(doctorScheduleService.addSchedule(username, doctorSchedule));
	}

	/*
	 * AIM: Fetch all slots available by doctor PATH:
	 * api/doctor/schedule/getbydoctor METHOD:GET PARAM:doctor_id <-Request Param
	 * EXPECTED:List<DoctorSchedule>
	 */
	@GetMapping("/get-by-doctor")
	public ResponseEntity<?> getByDoctor(@RequestParam int doctor_id) {
		return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleService.getByDoctor(doctor_id));
	}

	/*
	 * AIM: Fetch available slots By doctor.(Max available patients for a slot =2)
	 * PATH:api/doctor/schedule/get-available-slots-by-doctor_id METHOD:GET PARAMS:
	 * doctor_id <-Request Param, appointment<- Path variable Expected: Schedule
	 */

	@GetMapping("/get-available-slots-by-doctor_id")
	public ResponseEntity<?> getAvailableSoltsByDoctorId(@RequestParam int doctor_id) {
		return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleService.getAvailableSoltsByDoctorId(doctor_id));
	}
}
