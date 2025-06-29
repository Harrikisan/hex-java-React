package com.casestudy.AmazeCare.Controller;

import com.casestudy.AmazeCare.Model.BedBooking;
import com.casestudy.AmazeCare.Service.BedBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bed/appointment")
@CrossOrigin(origins = "http://localhost:5173")
public class BedBookingController {

    @Autowired
    private BedBookingService bedBookingService;

    @PostMapping("/book/{patient_id}/{bed_id}")
    public ResponseEntity<BedBooking> bookBed(@RequestBody BedBooking bedBooking,
                                              @PathVariable int patient_id,
                                              @PathVariable int bed_id) {
        return ResponseEntity.status(HttpStatus.OK).body(bedBookingService.bookBed(bedBooking, patient_id, bed_id));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<BedBooking> getById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(bedBookingService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BedBooking>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bedBookingService.getAll());
    }

    @GetMapping("/by-patient")
    public ResponseEntity<List<BedBooking>> getMyBedBookings(Principal principal) {
        String username = principal.getName(); // Assumes username is unique
        return ResponseEntity.status(HttpStatus.OK).body(bedBookingService.getByUsername(username));
    }

    @GetMapping("/by-username")
    public ResponseEntity<List<BedBooking>> getByPrincipal(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(bedBookingService.getByUsername(principal.getName()));
    }

    @GetMapping("/by-bed/{bedId}")
    public ResponseEntity<List<BedBooking>> getByBedId(@PathVariable int bedId) {
        return ResponseEntity.status(HttpStatus.OK).body(bedBookingService.getByBedId(bedId));
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<BedBooking> cancelBooking(@PathVariable int bookingId) {
        return ResponseEntity.status(HttpStatus.OK).body(bedBookingService.cancelBooking(bookingId));
    }
}
