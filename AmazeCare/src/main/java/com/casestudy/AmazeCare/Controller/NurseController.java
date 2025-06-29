package com.casestudy.AmazeCare.Controller;

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

import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Model.Nurse;
import com.casestudy.AmazeCare.Service.NurseService;

@RestController
@RequestMapping("/api/nurse")
@CrossOrigin(origins = "http://localhost:5173")
public class NurseController {

	@Autowired
	private NurseService nurseService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addNurse(@RequestBody Nurse nurse){
		return ResponseEntity.status(HttpStatus.OK).body(nurseService.AddNurse(nurse));
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateNurse(@RequestBody Nurse nurse,Principal principal){
		String username=principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(nurseService.UpdateNurse(username,nurse));
	}
	@PutMapping("/editStatus/{nurse_id}")
	public ResponseEntity<?> editStatus(@PathVariable int nurse_id,@RequestParam UserStatus status){
		return ResponseEntity.status(HttpStatus.OK).body(nurseService.editStatus(nurse_id,status));
	}
	@GetMapping("/get")
	public ResponseEntity<?> getNurseProfile(Principal principal) {
	    String username = principal.getName();
	    Nurse nurse = nurseService.getNurseByUsername(username);
	    return ResponseEntity.status(HttpStatus.CREATED).body(nurse);
	}

}
