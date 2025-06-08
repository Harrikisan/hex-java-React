package com.casestudy.AmazeCare.Dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.AmazeCare.Model.Doctor;

@Component
public class DoctorListingDto {

	private int id;
	private String name;
	private String phone;
	private String specilization;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSpecilization() {
		return specilization;
	}
	public void setSpecilization(String specilization) {
		this.specilization = specilization;
	}
	
	public List<DoctorListingDto> convertToDto(List<Doctor> doctor){
		
		List<DoctorListingDto> l_doctDoctorListingDto = new ArrayList<>();
		doctor.stream().forEach(d -> {
			DoctorListingDto dto = new DoctorListingDto();
			dto.setId(d.getId());
			dto.setName(d.getName());
			dto.setSpecilization(d.getSpecilization());
			dto.setPhone(d.getPhone());
			l_doctDoctorListingDto.add(dto);
		});
		
		return l_doctDoctorListingDto;
	}
}
