package com.casestudy.AmazeCare.Repoitory;

import com.casestudy.AmazeCare.Model.BedBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedBookingRepository extends JpaRepository<BedBooking, Integer> {

    List<BedBooking> findByPatirntId(int patientId);

    List<BedBooking> findByBedId(int bedId);
}
