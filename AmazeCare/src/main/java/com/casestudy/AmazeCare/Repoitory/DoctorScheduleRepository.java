package com.casestudy.AmazeCare.Repoitory;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Enum.Day;
import com.casestudy.AmazeCare.Enum.Slot;
import com.casestudy.AmazeCare.Model.DoctorSchedule;

import jakarta.transaction.Transactional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {

	@Query("select ds from DoctorSchedule ds where ds.doctor.id=?1")
	List<DoctorSchedule> getByDoctor(int doctor_id);

	@Query("""
		    SELECT ds FROM DoctorSchedule ds 
		    WHERE ds.doctor.id = ?1 
		    AND ds.id NOT IN (
		        SELECT da.doctorSchedule.id FROM DoctorAppointment da 
		        WHERE da.doctor.id = ?1 
		        GROUP BY da.date , da.doctorSchedule.id 
		        HAVING COUNT(da.id) >= 2
		    )
		""")
		List<DoctorSchedule> getAvailableSlots(int doctorId);

	@Query("""
		    SELECT ds FROM DoctorSchedule ds 
		    WHERE ds.doctor.id = ?1 and ds.day=?3 and ds.status=AVAILABLE
		    AND ds.id NOT IN (
		        SELECT da.doctorSchedule.id FROM DoctorAppointment da 
		        WHERE da.doctor.id = ?1 AND da.date = ?2 
		        GROUP BY da.doctorSchedule.id 
		        HAVING COUNT(da.id) >= 2
		    )
		""")
		List<DoctorSchedule> getAvailableSlotsForDate(int doctor_id, LocalDate date, Day day);


    @Modifying
    @Transactional
	@Query("delete from DoctorSchedule d where d.id=?1")
	void deleteSchedule(int schedule_id);


}
