package com.casestudy.AmazeCare.Service;

import java.util.List;
import com.casestudy.AmazeCare.Enum.LabStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.BedAvailability;
import com.casestudy.AmazeCare.Exception.BedNotFoundException;
import com.casestudy.AmazeCare.Exception.WardNotFoundException;
import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Model.Ward;
import com.casestudy.AmazeCare.Repoitory.BedRepository;
import com.casestudy.AmazeCare.Repoitory.WardRepository;

@Service
public class BedService {

    private final BedRepository bedRepository;
    private WardRepository wardRepository;
    private static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentService.class);

    public BedService(BedRepository bedRepository, WardRepository wardRepository) {
		super();
		this.bedRepository = bedRepository;
		this.wardRepository = wardRepository;
	}

	public Bed addBed(int wardId,Bed bed) {
		Ward ward=wardRepository.findById(wardId)
				.orElseThrow(()->new WardNotFoundException("Ward ID not found"));
    	bed.setWard(ward);
    	bed.setBedAvailability(BedAvailability.AVAILABLE);
    	logger.info("",bed);
        return bedRepository.save(bed);
    }

    public Bed updateBed(int id, Bed bedDetails) {
        Bed bed = bedRepository.findById(id)
                .orElseThrow(() -> new BedNotFoundException("Bed not found with id: " + id));

        if (bedDetails.getNumber() != null) bed.setNumber(bedDetails.getNumber());
        if (bedDetails.getWard() != null) bed.setWard(bedDetails.getWard());
        if (bedDetails.getBedAvailability() != null) bed.setBedAvailability(bedDetails.getBedAvailability());
        logger.info("",bedDetails);
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
        if(bed.getWard().getStatus()==LabStatus.AVAILABLE) 
        	bed.setBedAvailability(availability);
        else throw new WardNotFoundException("Ward not available");
        return bedRepository.save(bed);
    }
}
