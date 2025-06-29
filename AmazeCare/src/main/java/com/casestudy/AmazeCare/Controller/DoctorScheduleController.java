package com.casestudy.AmazeCare.Controller;


import java.security.Principal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.DoctorSchedule;
import com.casestudy.AmazeCare.Service.DoctorScheduleService;

@RestController
@RequestMapping("api/doctor/schedule")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorScheduleController {

	@Autowired
	private DoctorScheduleService doctorScheduleService;

	/*
	 * AIM: Add Schedule PATH: api/doctor/schedule/add METHOD: POST PARAMS:
	 * doctor_id <- path variable, schedule<- request body EXPECTED: schedule
	 */
	@PostMapping("/add/{doctorId}")
    public ResponseEntity<DoctorSchedule> addDoctorSchedule(
            @PathVariable int doctorId,
            @RequestBody DoctorSchedule schedule) {
        DoctorSchedule savedSchedule = doctorScheduleService.addSchedule(doctorId, schedule);
        return ResponseEntity.status(HttpStatus.OK).body(savedSchedule);
    }

	/*
	 * AIM: Fetch all slots available by doctor PATH:
	 * api/doctor/schedule/getbydoctor METHOD:GET PARAM:doctor_id <-Request Param
	 * EXPECTED:List<DoctorSchedule>
	 */
	@GetMapping("/get-by-doctor/{doctor_id}")
	public ResponseEntity<?> getByDoctor(@PathVariable int doctor_id) {
		return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleService.getByDoctor(doctor_id));
	}
	
	@GetMapping("/get-by-doctor")
	public ResponseEntity<?> getByDoctor(Principal principal) {
		String username=principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleService.getByDoctor(username));
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
	
	/*
	 * AIM: Fetch available slots By date,and doctor.(Max available patients for a slot =2)
	 * PATH:api/doctor/schedule/get-available-slots-by-doctor_id METHOD:GET PARAMS:
	 * doctor_id <-Request Param, appointment<- Path variable Expected: Schedule
	 */
	
	@GetMapping("/get-slots/{doctor_id}")
	public ResponseEntity<?> getAvailableSlotsForDate(@PathVariable int doctor_id, @RequestParam LocalDate date){
		return ResponseEntity.status(HttpStatus.OK)
				.body(doctorScheduleService.getAvailableSlotsForDate(doctor_id,date));
	}
	
	@DeleteMapping("/delete-slot/{schedule_id}")
	public ResponseEntity<?> deleteSchedule(@PathVariable int schedule_id){
		doctorScheduleService.deleteSchedule(schedule_id);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfull !!!");
	}
	@PutMapping("/editStatus/{recordId}")
	public ResponseEntity<?> editStatus(@PathVariable int recordId,@RequestParam String status){
		return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleService.editStatus(recordId,status));
	}
}
