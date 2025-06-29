package com.casestudy.AmazeCare.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.casestudy.AmazeCare.Enum.BedAvailability;
import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Service.BedService;

@RestController
@RequestMapping("/api/bed")
@CrossOrigin(origins = "http://localhost:5173")
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @PostMapping("/add/{wardId}")
    public ResponseEntity<Bed> addBed(@PathVariable int wardId, @RequestBody Bed bed) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bedService.addBed(wardId,bed));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Bed> updateBed(@PathVariable int id, @RequestBody Bed bed) {
        return ResponseEntity.status(HttpStatus.OK).body(bedService.updateBed(id, bed));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bed>> getAllBeds() {
        return ResponseEntity.status(HttpStatus.OK).body(bedService.getAllBeds());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Bed>> getAvailableBeds() {
        List<Bed> availableBeds = bedService.getBedsByAvailability(BedAvailability.AVAILABLE);
        return ResponseEntity.status(HttpStatus.OK).body(availableBeds);
    }

    @PutMapping("/changeAvailability/{id}")
    public ResponseEntity<Bed> changeAvailability(@PathVariable int id, @RequestParam BedAvailability availability) {
        Bed updatedBed = bedService.changeAvailability(id, availability);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBed);
    }
}
