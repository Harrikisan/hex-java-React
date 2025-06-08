package com.casestudy.AmazeCare.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.casestudy.AmazeCare.Model.Prescription;
import com.casestudy.AmazeCare.Service.PrescriptionService;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/add/{recordId}")
    public ResponseEntity<Prescription> addPrescription(@PathVariable int recordId, @RequestBody Prescription prescription) {
        return ResponseEntity.ok(prescriptionService.addPrescription(recordId, prescription));
    }

    @GetMapping("/getByRecord")
    public ResponseEntity<List<Prescription>> getPrescriptionsByRecord(@RequestParam int recordId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByRecordId(recordId));
    }
}
