package com.casestudy.AmazeCare.Controller;

import com.casestudy.AmazeCare.Model.BedBooking;
import com.casestudy.AmazeCare.Service.BedBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bed/appointment")
public class BedBookingController {

    @Autowired
    private BedBookingService bedBookingService;

    @PostMapping("/book/{patient_id}/{bed_id}")
    public ResponseEntity<BedBooking> bookBed(@RequestBody BedBooking bedBooking,
                                              @PathVariable int patient_id,
                                              @PathVariable int bed_id) {
        return ResponseEntity.ok(bedBookingService.bookBed(bedBooking, patient_id, bed_id));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<BedBooking> getById(@PathVariable int id) {
        return ResponseEntity.ok(bedBookingService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BedBooking>> getAll() {
        return ResponseEntity.ok(bedBookingService.getAll());
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<BedBooking>> getByPatientId(@PathVariable int patientId) {
        return ResponseEntity.ok(bedBookingService.getByPatientId(patientId));
    }

    @GetMapping("/by-username")
    public ResponseEntity<List<BedBooking>> getByPrincipal(Principal principal) {
        return ResponseEntity.ok(bedBookingService.getByUsername(principal.getName()));
    }

    @GetMapping("/by-bed/{bedId}")
    public ResponseEntity<List<BedBooking>> getByBedId(@PathVariable int bedId) {
        return ResponseEntity.ok(bedBookingService.getByBedId(bedId));
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<BedBooking> cancelBooking(@PathVariable int bookingId) {
        return ResponseEntity.ok(bedBookingService.cancelBooking(bookingId));
    }
}
