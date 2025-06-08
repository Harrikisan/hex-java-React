package com.casestudy.AmazeCare.Model;

import java.time.LocalDate;

import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Enum.PatientType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bed_booking")
public class BedBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDate adminssionDate;
	
	private LocalDate dischargeDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "patient_type")
	private PatientType patientType;
	
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;
	
	@ManyToOne
	private Patient patirnt;
	
	@ManyToOne
	private Bed bed;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getAdminssionDate() {
		return adminssionDate;
	}

	public void setAdminssionDate(LocalDate adminssionDate) {
		this.adminssionDate = adminssionDate;
	}

	public LocalDate getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(LocalDate dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public PatientType getPatientType() {
		return patientType;
	}

	public void setPatientType(PatientType patientType) {
		this.patientType = patientType;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public Patient getPatirnt() {
		return patirnt;
	}

	public void setPatirnt(Patient patirnt) {
		this.patirnt = patirnt;
	}

	public Bed getBed() {
		return bed;
	}

	public void setBed(Bed bed) {
		this.bed = bed;
	}
	
	
}
