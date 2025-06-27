package com.casestudy.AmazeCare.Controller;

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

import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Service.LabService;

@RestController
@RequestMapping("api/lab")
@CrossOrigin(origins = "http://localhost:5173")
public class LabController {

	@Autowired
	private LabService labService;
	
	/*
	 * AIM: add lab
	 * METHOD:POST
	 * PATH: api/lab/add
	 * PARAMS: lab <-request bdy
	 * EXPECTED: lab
	 * */
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Lab lab){
		return ResponseEntity.status(HttpStatus.CREATED).body(labService.add(lab));
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(labService.getAll());
	}
	@PutMapping("/edit-availability/{lab_id}")
	public ResponseEntity<?> editAvailability(@PathVariable int lab_id, @RequestParam LabStatus status){
		return ResponseEntity.status(HttpStatus.OK).body(labService.editAvailability(lab_id, status));
	}
}
