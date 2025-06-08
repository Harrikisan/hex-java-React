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
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @PostMapping("/add")
    public ResponseEntity<Bed> addBed(@RequestBody Bed bed) {
        Bed savedBed = bedService.addBed(bed);
        return new ResponseEntity<>(savedBed, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Bed> updateBed(@PathVariable int id, @RequestBody Bed bed) {
        Bed updatedBed = bedService.updateBed(id, bed);
        return new ResponseEntity<>(updatedBed, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bed>> getAllBeds() {
        List<Bed> beds = bedService.getAllBeds();
        return new ResponseEntity<>(beds, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Bed>> getAvailableBeds() {
        List<Bed> availableBeds = bedService.getBedsByAvailability(BedAvailability.AVAILABLE);
        return new ResponseEntity<>(availableBeds, HttpStatus.OK);
    }

    @PutMapping("/changeAvailability/{id}")
    public ResponseEntity<Bed> changeAvailability(@PathVariable int id, @RequestParam BedAvailability availability) {
        Bed updatedBed = bedService.changeAvailability(id, availability);
        return new ResponseEntity<>(updatedBed, HttpStatus.OK);
    }
}
