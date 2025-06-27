package com.casestudy.AmazeCare.Controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.casestudy.AmazeCare.Model.MedicalRecord;
import com.casestudy.AmazeCare.Service.MedicalRecordService;

@RestController
@RequestMapping("/api/medicalrecord")
@CrossOrigin(origins = "http://localhost:5173")
public class MedicalRecordController {

	private final MedicalRecordService medicalRecordService;

	public MedicalRecordController(MedicalRecordService medicalRecordService) {
		this.medicalRecordService = medicalRecordService;
	}

	@PostMapping("/add/{patientId}")
	public ResponseEntity<MedicalRecord> addMedicalRecord(Principal principal, @PathVariable int patientId,
			@RequestBody MedicalRecord medicalRecord) {
		String username = principal.getName();
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(medicalRecordService.addMedicalRecord(medicalRecord, patientId, username));
	}

	@GetMapping("/all")
	public ResponseEntity<List<MedicalRecord>> getAllRecords() {
		return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
	}

	@GetMapping("/get/{recordId}")
	public ResponseEntity<MedicalRecord> getRecordById(@PathVariable int recordId) {
		return ResponseEntity.ok(medicalRecordService.getMedicalRecordById(recordId));
	}

	@GetMapping("/get/patient")
	public ResponseEntity<List<MedicalRecord>> getRecordsByPatient(Principal principal) {
		String username = principal.getName();
		return ResponseEntity.ok(medicalRecordService.getMedicalRecordsByPatientUsername(username));
	}

	@GetMapping("/get/patient/{id}")
	public ResponseEntity<List<MedicalRecord>> getRecordsByPatientId(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "100") int size, @PathVariable("id") int id) {
		return ResponseEntity.ok(medicalRecordService.getMedicalRecordsByPatientId(id,page,size));
	}

	@GetMapping("/get/doctor")
	public ResponseEntity<List<MedicalRecord>> getRecordsByDoctor(Principal principal) {
		String username = principal.getName();
		return ResponseEntity.ok(medicalRecordService.getMedicalRecordsByDoctorUsername(username));
	}

	@GetMapping("/get/recorddate")
	public ResponseEntity<List<MedicalRecord>> getRecordsByRecordDate(@RequestParam("date") LocalDate recordDate) {
		return ResponseEntity.ok(medicalRecordService.getMedicalRecordsByRecordDate(recordDate));
	}
}
