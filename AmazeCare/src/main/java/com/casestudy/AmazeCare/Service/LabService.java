package com.casestudy.AmazeCare.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Exception.LabNotFoundException;
import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Repoitory.LabRepository;

@Service
public class LabService {

	private LabRepository labRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentService.class);

	public LabService(LabRepository labRepository) {
		super();
		this.labRepository = labRepository;
	}

	public Lab add(Lab lab) {
		lab.setLabStatus(LabStatus.AVAILABLE);
		return labRepository.save(lab);
	}

	public List<Lab> getAll() {
		return labRepository.getAll();
	}

	public Lab editAvailability(int lab_id,LabStatus status) {
		Lab lab=labRepository.findById(lab_id).orElseThrow(()->new LabNotFoundException("Lab not found"));
		lab.setLabStatus(status);
		logger.info("status edited: ",lab);
		return labRepository.save(lab);
	}
	
	
}
