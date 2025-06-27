package com.casestudy.AmazeCare.Repoitory;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Enum.Day;
import com.casestudy.AmazeCare.Model.TestSchedule;

public interface TestScheduleRepository extends JpaRepository<TestSchedule, Integer>{

	@Query("""
		    SELECT ts FROM TestSchedule ts 
		    WHERE ts.day = ?2 AND ts.id NOT IN (
		        SELECT ta.schedule.id FROM TestAppointment ta 
		        WHERE ta.lab.id = ?1 AND ta.date = ?3
		    )
		""")
		List<TestSchedule> getAvailableDatesForLabTest(int labId, Day day, LocalDate date);



	


}
