package com.hex.CodingChallenge.Dto;

import org.springframework.stereotype.Component;

import com.hex.CodingChallenge.Model.MedicalHistory;
import com.hex.CodingChallenge.Model.Patient;
@Component
public class RecordDto {

	private String patientName;
	
	private int age;
	
	private String illness;
	
	private int numberOfYears;
	
	private String medication;

	public String getIllness() {
		return illness;
	}

	public void setIllness(String illness) {
		this.illness = illness;
	}

	public int getNumberOfYears() {
		return numberOfYears;
	}

	public void setNumberOfYears(int numberOfYears) {
		this.numberOfYears = numberOfYears;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	
	
	
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public RecordDto convertToDto(MedicalHistory medicalhistory) {
		RecordDto recordDto=new RecordDto();
		recordDto.setIllness(medicalhistory.getIllness());
		recordDto.setMedication(medicalhistory.getCurrentMedication());
		recordDto.setNumberOfYears(medicalhistory.getNumberOfYears());
		recordDto.setAge(medicalhistory.getPatient().getAge());
		recordDto.setPatientName(medicalhistory.getPatient().getName());
		
		return recordDto;
	}
	
}
