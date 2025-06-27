package com.casestudy.AmazeCare.Repoitory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Model.TestAppointment;
import com.casestudy.AmazeCare.Model.TestSchedule;

public interface TestAppointmentRepository extends JpaRepository<TestAppointment, Integer>{

	@Query("select t from TestAppointment t where t.patient.id=?1")
	Optional<List<TestAppointment>> getByPatientId(int patientId);

	@Query("select t from TestAppointment t where t.lab.id=?1 and t.test.id=?2 and date=?3")
	List<TestAppointment> getAvailableDatesForLabTest(int lab_id, int test_id, LocalDate date);

	

	

}