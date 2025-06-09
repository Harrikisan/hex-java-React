package com.hex.CodingChallenge.Dto;

import org.springframework.stereotype.Component;

import com.hex.CodingChallenge.Model.MedicalHistory;
import com.hex.CodingChallenge.Model.Patient;

@Component
public class PatientMedicalRecordDto {

	private Patient patient;
	
	private MedicalHistory medicalHistory;

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public MedicalHistory getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(MedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	
}
