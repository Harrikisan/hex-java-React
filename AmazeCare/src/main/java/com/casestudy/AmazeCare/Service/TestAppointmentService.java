package com.casestudy.AmazeCare.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Exception.AppointmentNotFoundException;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Exception.LabNotFoundException;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Exception.TestNotFoundException;
import com.casestudy.AmazeCare.Exception.TestScheduleNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Model.Test;
import com.casestudy.AmazeCare.Model.TestAppointment;
import com.casestudy.AmazeCare.Model.TestSchedule;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.LabRepository;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;
import com.casestudy.AmazeCare.Repoitory.TestAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.TestRepository;
import com.casestudy.AmazeCare.Repoitory.TestScheduleRepository;

@Service
public class TestAppointmentService {

	private TestAppointmentRepository testAppointmentRepository;
	private PatientRepository patientRepository;
	private DoctorRepository doctorRepository;
	private LabRepository labRepository;
	private TestRepository testRepository;
	private TestScheduleRepository testScheduleRepository;

	public TestAppointmentService(TestAppointmentRepository testAppointmentRepository,
			PatientRepository patientRepository, DoctorRepository doctorRepository, LabRepository labRepository,
			TestRepository testRepository, TestScheduleRepository testScheduleRepository) {
		super();
		this.testAppointmentRepository = testAppointmentRepository;
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
		this.labRepository = labRepository;
		this.testRepository = testRepository;
		this.testScheduleRepository = testScheduleRepository;
	}

	
	public Object addTestAppointment(int patientId, int doctorId, int labId, int testId,
			int scheduleId, TestAppointment testAppointment) {
		//Fetch patient
		Patient patient=patientRepository.findById(patientId)
				.orElseThrow(()->new PatientNotFountException("Patient Id not found"));
		//Fetch doctor
		Doctor doctor=null;
		if(doctorId!=0) {  
		doctor=doctorRepository.findById(doctorId)
				.orElseThrow(()->new DoctorNotFoundException("Doctor Id not found"));
		}
		//Fetch lab
		Lab lab=labRepository.findById(labId).orElseThrow(()->new LabNotFoundException("Lab ID not found"));
		//Fetch test
		Test test= testRepository.findById(testId).orElseThrow(()->new TestNotFoundException("Test not found"));
		//Fetch schedule
		TestSchedule testSchedule=testScheduleRepository.findById(scheduleId)
				.orElseThrow(()->new TestScheduleNotFoundException("Test schedule not found"));
		//Attach to testAppointment
		
		testAppointment.setPatient(patient);
		testAppointment.setDoctor(doctor);
		testAppointment.setLab(lab);
		testAppointment.setTest(test);
		testAppointment.setSchedule(testSchedule);
		testAppointment.setStatus(AppointmentStatus.APPOROVED);
		//Add to db
		return testAppointmentRepository.save(testAppointment);
	}


	public TestAppointment get(int appointmentId) {
		return testAppointmentRepository.findById(appointmentId)
				.orElseThrow(()->new AppointmentNotFoundException("Appointment ID not found"));
	}


	public Object getAll() {
		return testAppointmentRepository.findAll();
	}


	public List<TestAppointment> getByPatientId(String username) {
		Patient patient=patientRepository.getbyUsername(username);
		return testAppointmentRepository.getByPatientId(patient.getId()).
				orElseThrow(()->new PatientNotFountException("No appointments by Patient ID"));
	}


	public List<LocalDate> getAvailableDatesForLabTest(int lab_id,int test_id) {
		List<LocalDate> dates=new ArrayList<>();
		for(int i=0;i<21;i++) {
			LocalDate date=LocalDate.now().plusDays(i);
			if(testAppointmentRepository.getAvailableDatesForLabTest(lab_id,test_id,date).size()<8) dates.add(date);
		}
		
		return dates;
	}


	public TestAppointment editStatus(String status, int appId) {
		TestAppointment appointment=testAppointmentRepository.findById(appId)
				.orElseThrow(()->new AppointmentNotFoundException("Appointment ID not found"));
		appointment.setStatus(AppointmentStatus.valueOf(status));
		return testAppointmentRepository.save(appointment);
	}	
}