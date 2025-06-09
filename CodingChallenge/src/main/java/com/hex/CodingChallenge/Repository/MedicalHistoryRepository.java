package com.hex.CodingChallenge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hex.CodingChallenge.Model.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer>{

}
