package com.casestudy.AmazeCare.Dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.AmazeCare.Model.Doctor;

@Component
public class DoctorSearchDto {

	private int id;
	private String name;
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
	
	public List<DoctorSearchDto> convertToDto(List<Doctor> doctor){
		List<DoctorSearchDto> doctorSearchDtos=new ArrayList<>();
		doctor.stream().forEach(d->{
			DoctorSearchDto dto=new DoctorSearchDto();
			dto.setId(d.getId());
			dto.setName(d.getName());
			doctorSearchDtos.add(dto);
		});
		return doctorSearchDtos;
	}
}
