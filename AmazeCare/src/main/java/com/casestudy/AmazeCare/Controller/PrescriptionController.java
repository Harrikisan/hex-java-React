package com.casestudy.AmazeCare.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.casestudy.AmazeCare.Model.Prescription;
import com.casestudy.AmazeCare.Service.PrescriptionService;

@RestController
@RequestMapping("/api/prescription")
@CrossOrigin(origins = "http://localhost:5173")
public class PrescriptionController {

	@Autowired
	private PrescriptionService prescriptionService;

	@PostMapping("/add/batch/{recordId}")
	public ResponseEntity<List<Prescription>> addPrescriptionsBatch(@PathVariable int recordId,
			@RequestBody List<Prescription> prescriptions) {
		List<Prescription> savedList = prescriptionService.addPrescriptionsBatch(recordId, prescriptions);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedList);
	}

	@GetMapping("/getByRecord")
	public ResponseEntity<?> getPrescriptionsByRecord(
			@RequestParam int recordId) {
		return ResponseEntity.status(HttpStatus.OK).body(prescriptionService.getPrescriptionsByRecordId(recordId));
	}

}
