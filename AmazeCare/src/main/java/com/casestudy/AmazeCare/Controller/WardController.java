package com.casestudy.AmazeCare.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Service.WardService;

@RestController
@RequestMapping("/api/ward")
@CrossOrigin(origins = "http://localhost:5173")
public class WardController {

	@Autowired
	private WardService wardService;
	
	@PostMapping("/add")
	private ResponseEntity<?> addWard(@RequestParam String wardNumber){
		return ResponseEntity.status(HttpStatus.CREATED).body(wardService.add(wardNumber));
	}
	
	@GetMapping("/all")
	private ResponseEntity<?> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(wardService.getAll());
	}
	
	@PutMapping("/setAvailability/{wardId}")
	private ResponseEntity<?> setAvailability(
			@PathVariable int wardId,
			@RequestParam LabStatus status){
		return ResponseEntity.status(HttpStatus.OK).body(wardService.setAvailability(wardId,status));
	}
}
