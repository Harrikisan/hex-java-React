package com.casestudy.AmazeCare.Service;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Model.TestSchedule;
import com.casestudy.AmazeCare.Repoitory.TestScheduleRepository;

@Service
public class TestScheduleService {

	private TestScheduleRepository testScheduleRepository;

	public TestScheduleService(TestScheduleRepository testScheduleRepository) {
		super();
		this.testScheduleRepository = testScheduleRepository;
	}

	public TestSchedule add(TestSchedule testSchedule) {
		return testScheduleRepository.save(testSchedule);
	}
	
	
}
