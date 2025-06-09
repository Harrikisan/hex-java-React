package com.hex.CodingChallenge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hex.CodingChallenge.Model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

}
