package com.casestudy.AmazeCare.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Exception.LabNotFoundException;
import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Model.Test;
import com.casestudy.AmazeCare.Repoitory.LabRepository;
import com.casestudy.AmazeCare.Repoitory.TestRepository;

@Service
public class TestService {

	private TestRepository testRepository;
	private LabRepository labRepository;

	public TestService(TestRepository testRepository, LabRepository labRepository) {
		super();
		this.testRepository = testRepository;
		this.labRepository = labRepository;
	}

	public Object add(int lab_id, Test test) {
		//Fetch lab
		Lab lab=labRepository.findById(lab_id)
				.orElseThrow(()->new LabNotFoundException("Lab not available"));
		//Attach lab
		test.setLab(lab);
		// save to db
		return testRepository.save(test);
	}

	public List<Test> getAll() {
		return testRepository.findAll();
	}

	public List<Test> getByLab(int labId) {
		return testRepository.getByLabId(labId);
	}
	
	
}
