package com.casestudy.AmazeCare.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.BedAvailability;
import com.casestudy.AmazeCare.Exception.BedNotFoundException;
import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Repoitory.BedRepository;

@Service
public class BedService {

    private final BedRepository bedRepository;

    public BedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    public Bed addBed(Bed bed) {
    	bed.setBedAvailability(BedAvailability.NOTAVAILABLE);
        return bedRepository.save(bed);
    }

    public Bed updateBed(int id, Bed bedDetails) {
        Bed bed = bedRepository.findById(id)
                .orElseThrow(() -> new BedNotFoundException("Bed not found with id: " + id));

        if (bedDetails.getNumber() != null) bed.setNumber(bedDetails.getNumber());
        if (bedDetails.getRoomNumber() != null) bed.setRoomNumber(bedDetails.getRoomNumber());
        if (bedDetails.getWardNumber() != null) bed.setWardNumber(bedDetails.getWardNumber());
        if (bedDetails.getBedAvailability() != null) bed.setBedAvailability(bedDetails.getBedAvailability());

        return bedRepository.save(bed);
    }

    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }

    public List<Bed> getBedsByAvailability(BedAvailability availability) {
        return bedRepository.findByBedAvailability(availability);
    }

    public Bed changeAvailability(int id, BedAvailability availability) {
        Bed bed = bedRepository.findById(id)
                .orElseThrow(() -> new BedNotFoundException("Bed not found with id: " + id));
        bed.setBedAvailability(availability);
        return bedRepository.save(bed);
    }
}
