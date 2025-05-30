package com.casestudy.AmazeCare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Model.TestSchedule;
import com.casestudy.AmazeCare.Service.TestScheduleService;

@RestController
@RequestMapping("api/test/schedule")
public class TestScheduleController {

	@Autowired
	private TestScheduleService testScheduleService;
	/*
	 * AIM: add schedule
	 * METHOD: POST
	 * PATH: api/test/schedule/add
	 * PARAMS: TestSchedule <-request body
	 * OUTPUT: TestSchedule
	 * */
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody TestSchedule testSchedule){
		return ResponseEntity.status(HttpStatus.CREATED).body(testScheduleService.add(testSchedule));
	}
}
