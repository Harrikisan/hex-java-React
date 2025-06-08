package com.casestudy.AmazeCare.Dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.AmazeCare.Model.Patient;

@Component
public class PatientListingDto {

	private int id;
	private String name;
	private String contact;
	private String emergencycontact;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmergencycontact() {
		return emergencycontact;
	}
	public void setEmergencycontact(String emergencycontact) {
		this.emergencycontact = emergencycontact;
	}
	
	public List<PatientListingDto> convertToDto(List<Patient> patient){
		List<PatientListingDto> patientListingDto=new ArrayList<>();
		patient.stream().forEach(p->{
			PatientListingDto dto=new PatientListingDto();
			dto.setId(p.getId());
			dto.setName(p.getName());
			dto.setContact(p.getPhone());
			dto.setEmergencycontact(p.getEmergencyContact());
			patientListingDto.add(dto);
		});
		return patientListingDto;
	}
	
}
