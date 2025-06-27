package com.casestudy.AmazeCare.Repoitory;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Enum.BedAvailability;

public interface BedRepository extends JpaRepository<Bed, Integer> {
    List<Bed> findByBedAvailability(BedAvailability availability);

    @Query("select b from Bed b where b.ward.status=?1")
	List<Bed> getByWard(int wardId);
}
