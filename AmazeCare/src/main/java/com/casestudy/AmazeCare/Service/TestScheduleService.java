package com.casestudy.AmazeCare.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.Day;
import com.casestudy.AmazeCare.Exception.LabNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Model.TestAppointment;
import com.casestudy.AmazeCare.Model.TestSchedule;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.LabRepository;
import com.casestudy.AmazeCare.Repoitory.TestAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.TestScheduleRepository;

@Service
public class TestScheduleService {

	private TestScheduleRepository testScheduleRepository;
	private LabRepository labRepository;
	private TestAppointmentRepository testAppointmentRepository;

	

	public TestScheduleService(TestScheduleRepository testScheduleRepository, LabRepository labRepository,
			TestAppointmentRepository testAppointmentRepository) {
		super();
		this.testScheduleRepository = testScheduleRepository;
		this.labRepository = labRepository;
		this.testAppointmentRepository = testAppointmentRepository;
	}

	public TestSchedule add(TestSchedule testSchedule) {
		return testScheduleRepository.save(testSchedule);
	}

	public List<TestSchedule> getAvailableSlots(int labId, LocalDate date) {
	    Day dayEnum = Day.valueOf(date.getDayOfWeek().toString()); 
	    return testScheduleRepository.getAvailableDatesForLabTest(labId, dayEnum, date);
	}



	
	
	
}
