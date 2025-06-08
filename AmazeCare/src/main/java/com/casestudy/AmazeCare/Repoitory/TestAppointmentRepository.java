package com.casestudy.AmazeCare.Repoitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.TestAppointment;

public interface TestAppointmentRepository extends JpaRepository<TestAppointment, Integer>{

	@Query("select t from TestAppointment t where t.patient.id=?1")
	Optional<List<TestAppointment>> getByPatientId(int patientId);

	@Query("select t from TestAppointment t where t.lab.id=?1 and t.test.id=?2")
	List<TestAppointment> getAvailableDatesForLabTest(int lab_id, int test_id);

}