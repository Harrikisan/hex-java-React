package com.casestudy.AmazeCare.Service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Exception.WardNotFoundException;
import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Model.Ward;
import com.casestudy.AmazeCare.Repoitory.BedRepository;
import com.casestudy.AmazeCare.Repoitory.WardRepository;

@Service 
public class WardService {

	private WardRepository wardRepository;
	private BedRepository bedRepository;

	public WardService(WardRepository wardRepository) {
		super();
		this.wardRepository = wardRepository;
	}

	public Ward add(String wardNumber) {
		Ward ward=new Ward();
		ward.setWard_number(wardNumber);
		ward.setStatus(LabStatus.AVAILABLE);
		return wardRepository.save(ward);
	}

	public List<Ward> getAll() {
		return wardRepository.getAll();
	}

	public Ward setAvailability(int wardId,LabStatus status) {
		List<Bed> bed=bedRepository.getByWard(wardId);
		Ward ward=wardRepository.findById(wardId)
				.orElseThrow(()->new WardNotFoundException("Ward number not available") );
		ward.setStatus(status);
		return wardRepository.save(ward);
	}

	
	
}
