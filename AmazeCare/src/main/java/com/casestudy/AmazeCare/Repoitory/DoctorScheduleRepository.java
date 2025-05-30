package com.casestudy.AmazeCare.Repoitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {

	@Query("select ds from DoctorSchedule ds where ds.doctor.id=?1")
	Optional<List<DoctorSchedule>> getByDoctor(int doctor_id);

	@Query("""
		    SELECT ds FROM DoctorSchedule ds 
		    WHERE ds.doctor.id = ?1 
		    AND ds.id NOT IN (
		        SELECT da.doctorSchedule.id FROM DoctorAppointment da 
		        WHERE da.doctor.id = ?1 
		        GROUP BY da.doctorSchedule.id 
		        HAVING COUNT(da.id) >= 2
		    )
		""")
		List<DoctorSchedule> getAvailableSlots(int doctorId);


}
