package com.casestudy.AmazeCare.Service;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Repoitory.LabRepository;

@Service
public class LabService {

	private LabRepository labRepository;

	public LabService(LabRepository labRepository) {
		super();
		this.labRepository = labRepository;
	}

	public Lab add(Lab lab) {
		lab.setLabStatus(LabStatus.AVAILABLE);
		return labRepository.save(lab);
	}
	
	
}
